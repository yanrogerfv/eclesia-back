package imdl.eclesia.service;

import imdl.eclesia.domain.*;
import imdl.eclesia.domain.event.EscalaEvent;
import imdl.eclesia.domain.exception.EntityNotFoundException;
import imdl.eclesia.domain.exception.RogueException;
import imdl.eclesia.domain.input.EscalaInput;
import imdl.eclesia.persistence.EscalaRepository;
import imdl.eclesia.service.mapper.EscalaMapper;
import imdl.eclesia.service.mapper.MusicaMapper;
import imdl.eclesia.service.utils.events.LogAction;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.core.context.SecurityContextHolder;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

@Slf4j
public class EscalaService {

    private final EscalaRepository escalaRepository;
    private final LevitaService levitaService;
    private final MusicaService musicaService;
    private final ApplicationEventPublisher eventPublisher;
    private final ZoneId zoneId = ZoneId.of("America/Sao_Paulo");

    public EscalaService(EscalaRepository escalaRepository, LevitaService levitaService, MusicaService musicaService, ApplicationEventPublisher eventPublisher) {
        this.escalaRepository = escalaRepository;
        this.levitaService = levitaService;
        this.musicaService = musicaService;
        this.eventPublisher = eventPublisher;
    }

    public List<Escala> findAllEscalas(UUID levita){
        return escalaRepository.findAll(levita).stream().map(EscalaMapper::entityToDomain).sorted(Comparator.comparing(Escala::getData)).toList();
    }

    public List<Escala> findMonthEscalas(int month){
        return escalaRepository.findAllInMonth(month).stream().map(EscalaMapper::entityToDomain).sorted(Comparator.comparing(Escala::getData)).toList();
    }

    public List<EscalaResumed> findNextEscalasResumidas() {
        return escalaRepository.findNextResumidas(LocalDate.now(), LocalDate.now().plusDays(31)).stream().map(EscalaMapper::entityToDomainResumida).sorted(Comparator.comparing(EscalaResumed::getData)).toList();
    }

    public List<EscalaResumed> findAllResumidas(){
        return escalaRepository.findAllResumida().stream().map(EscalaMapper::entityToDomainResumida).sorted(Comparator.comparing(EscalaResumed::getData)).toList();
    }

    public Escala findById(UUID id){
        return escalaRepository.findById(id).map(EscalaMapper::entityToDomain)
                .orElseThrow(() -> new EntityNotFoundException("Escala não encontrada."));
    }

    public Escala create(EscalaInput input){
        validateInput(input);
        Escala escala = inputToDomain(input);
        escala.setCreatedAt(LocalDateTime.now(zoneId));
        escala.setCreatedBy(SecurityContextHolder.getContext().getAuthentication().getName());
        Escala created = EscalaMapper.entityToDomain(escalaRepository.save(EscalaMapper.domainToEntity(escala)));
        publishEscalaEvent(LogAction.CRIAR_ESCALA, created);
        return created;
    }

    public Escala update(EscalaInput input){
        validateInput(input);
        Escala old = findById(input.getId());
        Escala escala = inputToDomain(input);

        old.update(escala);
        old.setUpdatedAt(LocalDateTime.now(zoneId));
        old.setUpdatedBy(SecurityContextHolder.getContext().getAuthentication().getName());
        publishEscalaEvent(LogAction.ATUALIZAR_ESCALA, old);
        return EscalaMapper.entityToDomain(escalaRepository.save(EscalaMapper.domainToEntity(old)));
    }

    public void deleteEscala(UUID id){
        Escala escala = findById(id);
        publishEscalaEvent(LogAction.EXCLUIR_ESCALA, escala);
        escalaRepository.delete(escalaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Escala não encontrada.")));
    }

    public List<Musica> findMusicasInEscala(UUID escalaId){
        if(escalaRepository.findById(escalaId).isEmpty())
            throw new EntityNotFoundException("Escala não encontrada.");
        return escalaRepository.findAllMusicasInEscala(escalaId).stream().map(MusicaMapper::entityToDomain).toList();
    }

    public Escala setMusicasInEscala(UUID escalaId, List<UUID> musicasIds){
        Escala escala = findById(escalaId);
        List<Musica> musicas = new ArrayList<>();
        if(!(musicasIds == null || musicasIds.isEmpty()))
            musicasIds.forEach(id -> musicas.add(musicaService.findById(id)));
        escala.setMusicas(musicas);
        publishEscalaEvent(LogAction.DEFINIR_MUSICAS, escala);
        return EscalaMapper.entityToDomain(escalaRepository.save(EscalaMapper.domainToEntity(escala)));
    }

    public Escala removeMusicaInEscala(UUID escalaId, UUID musicaId){
        Escala escala = findById(escalaId);
        List<Musica> musicas = escala.getMusicas();
        musicas.remove(musicaService.findById(musicaId));
        escala.setMusicas(musicas);
        log.info("{} removendo música da escala de {}", SecurityContextHolder.getContext().getAuthentication().getName(), escala.getData());
        return EscalaMapper.entityToDomain(escalaRepository.save(EscalaMapper.domainToEntity(escala)));
    }

    private void validateInput(EscalaInput input){
        if(input.getTitulo() == null || input.getTitulo().isBlank())
            throw new RogueException("A escala está sem título.");
        if(input.getData() == null)
            throw new RogueException("A escala está sem data.");
        if(input.getData().isBefore(LocalDate.now()))
            throw new RogueException("Essa data já passou.");
        if(input.getTitulo() == null || input.getTitulo().isBlank())
            throw new RogueException("A escala está sem título.");
        if(input.getMinistro() == null)
            log.warn("A escala de {} está sem ministro, criada/alterada por {}", input.getData(), SecurityContextHolder.getContext().getAuthentication().getName());
    }

    private Escala inputToDomain(EscalaInput input){
        Escala escala = new Escala();

        escala.setData(input.getData());
        escala.setTitulo(input.getTitulo());

        BiConsumer<UUID, Consumer<Levita>> setLevita = (levitaId, setter) -> {
             if (setter == null) return;
             if (levitaId == null) {
                 setter.accept(null);
             } else {
                 setter.accept(findLevita(levitaId, input.getData()));
             }
         };

         setLevita.accept(input.getMinistro(), escala::setMinistro);
         setLevita.accept(input.getBaixo(), escala::setBaixo);
         setLevita.accept(input.getBateria(), escala::setBateria);
         setLevita.accept(input.getGuitarra(), escala::setGuitarra);
         setLevita.accept(input.getTeclado(), escala::setTeclado);
         setLevita.accept(input.getViolao(), escala::setViolao);

        if(input.getBacks() != null) {
            List<Levita> backs = levitaService.findAllById(input.getBacks());
            var unavailable = backs.stream()
                    .filter(l -> l.getAgenda() != null && l.getAgenda().contains(input.getData()))
                    .findFirst();
            if (unavailable.isPresent())
                throw new RogueException(unavailable.get().getNome() + " não está disponível para essa data.");
            escala.setBack(backs);
        }

        if (input.getObservacoes() != null)
            escala.setObservacoes(input.getObservacoes());

        escala.setEspecial(false);
        escala.setDomingo(false);
        escala.setQuarta(false);

        if (input.isEspecial()){
            escala.setEspecial(true);
        } else {
            switch (input.getData().getDayOfWeek()) {
                case SUNDAY:
                    escala.setDomingo(true);
                    break;
                case WEDNESDAY:
                    escala.setQuarta(true);
                    break;
                default:
                    escala.setEspecial(true);
                    break;
            }
        }
        return escala;
    }

    private Levita findLevita(UUID levitaId, LocalDate data){
        Levita levita = levitaService.findById(levitaId);
        if(levita.getAgenda().contains(data))
            throw new RogueException(levita.getNome() + " está indisponível para essa data.");
        return levita;
    }

    @Transactional
    public void cleanEscalas(){
        log.info("Usuário {} iniciando limpeza de escalas antigas...", SecurityContextHolder.getContext().getAuthentication().getName());
        List<Escala> escalas = escalaRepository.findAll().stream().map(EscalaMapper::entityToDomain).toList();
        for (Escala escala : escalas) {
            if (escala.getData().isBefore(LocalDate.now(zoneId).minusDays(15)))
                escalaRepository.deleteById(escala.getId());
        }
    }

    private void publishEscalaEvent(LogAction action, Escala escala) {
        String user = SecurityContextHolder.getContext().getAuthentication().getName();
        log.info("{} {} escala de {}", user, action.getAcao(), escala.getData());
        eventPublisher.publishEvent(new EscalaEvent(escala, action, user));
    }

}
