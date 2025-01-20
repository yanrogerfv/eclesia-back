package imdl.scalator.service;

import imdl.scalator.domain.EscalaResumed;
import imdl.scalator.domain.exception.EntityNotFoundException;
import imdl.scalator.domain.Escala;
import imdl.scalator.domain.Levita;
import imdl.scalator.domain.Musica;
import imdl.scalator.domain.exception.RogueException;
import imdl.scalator.domain.input.EscalaInput;
import imdl.scalator.persistence.EscalaRepository;
import imdl.scalator.service.mapper.EscalaMapper;
import imdl.scalator.service.mapper.MusicaMapper;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;

public class EscalaService {

    private final EscalaRepository escalaRepository;
    private final LevitaService levitaService;
    private final MusicaService musicaService;

    public EscalaService(EscalaRepository escalaRepository, LevitaService levitaService, MusicaService musicaService) {
        this.escalaRepository = escalaRepository;
        this.levitaService = levitaService;
        this.musicaService = musicaService;
    }

    public List<Escala> findAllEscalas(){
        return escalaRepository.findAll().stream().map(EscalaMapper::entityToDomain).sorted(Comparator.comparing(Escala::getData)).toList();
    }

    public List<Escala> findMonthEscalas(int month){
        return escalaRepository.findAllInMonth(month).stream().map(EscalaMapper::entityToDomain).sorted(Comparator.comparing(Escala::getData)).toList();
    }

    public List<Escala> findNextEscalas() {
        return escalaRepository.findNext(LocalDate.now(), LocalDate.now().plusDays(31)).stream().map(EscalaMapper::entityToDomain).sorted(Comparator.comparing(Escala::getData)).toList();
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
        return EscalaMapper.entityToDomain(escalaRepository.save(EscalaMapper.domainToEntity(escala)));
    }

    public Escala update(EscalaInput input){
        validateInput(input);
        Escala escala = inputToDomain(input);
        escalaRepository.save(EscalaMapper.domainToEntity(escala));
        return escala;
    }

    public void deleteEscala(UUID id){
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
        if(musicasIds == null || musicasIds.isEmpty())
            throw new RogueException("Nenhuma música foi selecionada.");
        musicasIds.forEach(id -> musicas.add(musicaService.findById(id)));
        escala.setMusicas(musicas);
        return EscalaMapper.entityToDomain(escalaRepository.save(EscalaMapper.domainToEntity(escala)));
    }

    public Escala removeMusicaInEscala(UUID escalaId, UUID musicaId){
        Escala escala = findById(escalaId);
        List<Musica> musicas = escala.getMusicas();
        musicas.remove(musicaService.findById(musicaId));
        escala.setMusicas(musicas);
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
            throw new RogueException("Favor inserir um ministro para a escala.");
    }

    private Escala inputToDomain(EscalaInput input){
        Escala escala = new Escala();
        if(input.getId() != null)
            escala = EscalaMapper.entityToDomain(escalaRepository.findById(input.getId())
                    .orElseThrow(() -> new EntityNotFoundException("Escala não encontrada.")));
        escala.setData(input.getData());
        escala.setTitulo(input.getTitulo());
        escala.setMinistro(findLevita(input.getMinistro(), input.getData()));
        if(input.getBaixo() != null)
            escala.setBaixo(findLevita(input.getBaixo(), input.getData()));
        else
            escala.setBaixo(null);
        if(input.getBateria() != null)
            escala.setBateria(findLevita(input.getBateria(), input.getData()));
        else
            escala.setBateria(null);
        if(input.getGuitarra() != null)
            escala.setGuitarra(findLevita(input.getGuitarra(), input.getData()));
        else
            escala.setGuitarra(null);
        if(input.getTeclado() != null)
            escala.setTeclado(findLevita(input.getTeclado(), input.getData()));
        else
            escala.setTeclado(null);
        if(input.getViolao() != null)
            escala.setViolao(findLevita(input.getViolao(), input.getData()));
        else
            escala.setViolao(null);
        if(input.getBacks() != null) {
            escala.setBack(levitaService.findAllById(input.getBacks()));
            escala.getBack().forEach(levita -> {
                if(levita.getAgenda().contains(input.getData()))
                    throw new RogueException(levita.getNome() + " não está disponível para essa data.");
            });
        }
        if (input.getObservacoes() != null)
            escala.setObservacoes(input.getObservacoes());
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
        return escala;
    }

    private Levita findLevita(UUID levitaId, LocalDate data){
        Levita levita = levitaService.findById(levitaId);
        if(levita.getAgenda().contains(data))
            throw new RogueException(levita.getNome()+" está indisponível para essa data.");
        return levita;
    }

    public void cleanEscalas(){
        List<Escala> escalas = escalaRepository.findAll().stream().map(EscalaMapper::entityToDomain).toList();
        for (int i = 0; i < escalas.size(); i++) {
            if(escalas.get(i).getData().isBefore(LocalDate.now()))
                escalaRepository.deleteById(escalas.get(i).getId());
        }
    }

}
