package imdl.eclesia.service;

import imdl.eclesia.controller.filter.LevitaFilter;
import imdl.eclesia.domain.LevitaResumed;
import imdl.eclesia.domain.exception.EntityNotFoundException;
import imdl.eclesia.domain.Instrumento;
import imdl.eclesia.domain.Levita;
import imdl.eclesia.domain.exception.RogueException;
import imdl.eclesia.domain.input.LevitaInput;
import imdl.eclesia.persistence.EscalaRepository;
import imdl.eclesia.persistence.LevitaRepository;
import imdl.eclesia.service.mapper.LevitaMapper;

import java.time.LocalDate;
import java.util.*;

public class LevitaService {

    private final LevitaRepository levitaRepository;

    private final InstrumentoService instrumentoService;

    private final EscalaRepository escalaService;

    public LevitaService(LevitaRepository levitaRepository, InstrumentoService instrumentoService, EscalaRepository escalaService) {
        this.levitaRepository = levitaRepository;
        this.instrumentoService = instrumentoService;
        this.escalaService = escalaService;
    }

    public List<Levita> findAll(LevitaFilter filter){
        return levitaRepository.findAll(filter.getNome(), filter.getInstrumento()).stream()
                .map(LevitaMapper::entityToDomain).sorted(Comparator.comparing(Levita::getNome)).toList();
    }
    public List<LevitaResumed> findAllResumed(){
        return levitaRepository.findAllResumed().stream().map(LevitaMapper::entityToDomainResumed)
                .sorted(Comparator.comparing(LevitaResumed::getNome)).toList();
    }

    public List<Levita> findAllById(List<UUID> ids){
        return levitaRepository.findAllById(ids).stream().map(LevitaMapper::entityToDomain)
                .sorted(Comparator.comparing(Levita::getNome)).toList();
    }

    public List<LevitaResumed> findAllDisponivel(LocalDate date){
        return levitaRepository.findAllResumed().stream().map(LevitaMapper::entityToDomainResumed)
                .filter(levita -> {
                    if(levita.getAgenda() == null)
                        return true;
                    return !levita.getAgenda().contains(date);
                }).sorted(Comparator.comparing(LevitaResumed::getNome)).toList();
    }
    public List<Levita> findAllByInstrument(Long instrumento){
        return levitaRepository.findAllByInstrumento(instrumento).stream()
                .map(LevitaMapper::entityToDomain).sorted(Comparator.comparing(Levita::getNome)).toList();
    }

    public Levita findById(UUID id){
        return LevitaMapper.entityToDomain(levitaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Levita não encontrada.")));
    }
    public Levita create(LevitaInput input){
        validateInput(input);
        Levita levita = inputToDomain(input);
        levita.setAgenda(new ArrayList<>());
        return LevitaMapper.entityToDomain(levitaRepository.save(LevitaMapper.domainToEntity(levita)));
    }
    public Levita update(LevitaInput input){
        Levita levita = LevitaMapper.entityToDomain(levitaRepository.findById(input.getId())
                .orElseThrow(() -> new EntityNotFoundException("Levita não encontrada.")));
        if(input.getNome() != null)
            if (input.getNome().isBlank())
                throw new RogueException("O nome está vazio.");
            else levita.setNome(input.getNome());
        if(input.getInstrumentos() != null)
            levita.setInstrumentos(input.getInstrumentos().stream().map(instrumentoService::findById).toList());
        if(input.getContato() != null)
            levita.setContato(input.getContato());
        if(input.getEmail() != null)
            levita.setEmail(input.getEmail());
        if(input.getDescricao() != null)
            levita.setDescricao(input.getDescricao());
        return LevitaMapper.entityToDomain(levitaRepository.save(LevitaMapper.domainToEntity(levita)));
    }

    public void deleteLevita(UUID id){
        if(escalaService.existsByLevita(id))
            throw new RogueException("Levita está em uma escala.");
        levitaRepository.delete(levitaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Levita não encontrada.")));
    }

    public Levita addInstrumento(UUID id, Long codInstrumento){ // TODO FIX
        Levita levita = LevitaMapper.entityToDomain(levitaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Levita não encontrada.")));
        List<Instrumento> instrumentos = levita.getInstrumentos();
        if(instrumentos.stream().map(Instrumento::getId).toList().contains(codInstrumento))
            throw new RogueException("Levita já possui este instrumento.");
        Instrumento instrumento = instrumentoService.findById(codInstrumento);
        List<Instrumento> newInstrumentos = new ArrayList<>();
        for (Instrumento value : instrumentos) {
            if (instrumento.getId() < value.getId())
                newInstrumentos.add(instrumento);
            else
                newInstrumentos.add(value);
        }
        levita.setInstrumentos(newInstrumentos);
        return LevitaMapper.entityToDomain(levitaRepository.save(LevitaMapper.domainToEntity(levita)));
    }

    public Levita removeInstrumento(UUID id, Long instrumento){
        Levita levita = LevitaMapper.entityToDomain(levitaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Levita não encontrada.")));
        List<Instrumento> instrumentos = levita.getInstrumentos();
        if(!instrumentos.stream().map(Instrumento::getId).toList().contains(instrumento))
            throw new EntityNotFoundException("Levita já não possui este instrumento.");

        List<Instrumento> newInstrumentos = new ArrayList<>();
        instrumentos.forEach(i -> {
            if(!Objects.equals(i.getId(), instrumento))
                newInstrumentos.add(i);
        });

        levita.setInstrumentos(newInstrumentos);
        return LevitaMapper.entityToDomain(levitaRepository.save(LevitaMapper.domainToEntity(levita)));
    }

    public List<LocalDate> getLevitaAgenda(UUID id){
        return levitaRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Levita não encontrado.")).getAgenda();
    }

    public Levita updateAgentaFromALevita(UUID id){
        Levita levita = LevitaMapper.entityToDomain(levitaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Levita não encontrada.")));

        List<LocalDate> agenda = levita.getAgenda();
        agenda.removeIf(date -> date.isBefore(LocalDate.now().minusDays(30)));
        levita.setAgenda(agenda);

        levitaRepository.save(LevitaMapper.domainToEntity(levita));
        return levita;
    }

    public Levita setLevitaAgenda(UUID id, List<LocalDate> dates){
        Levita levita = LevitaMapper.entityToDomain(levitaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Levita não encontrado.")));
        if(dates.stream().anyMatch(date -> date.isBefore(LocalDate.now())))
            throw new RogueException("Data já passou.");
        levita.setAgenda(dates);
        return LevitaMapper.entityToDomain(levitaRepository.save(LevitaMapper.domainToEntity(levita)));
    }

    private void validateInput(LevitaInput input){
        if(input.getNome() == null || input.getNome().isBlank())
            throw new RogueException("O nome está vazio.");
        if(input.getInstrumentos() == null || input.getInstrumentos().isEmpty())
            throw new RogueException("O instrumento está vazio.");
        if((input.getContato() == null||input.getContato().isBlank())
                && (input.getEmail() == null ||input.getEmail().isBlank()))
            throw new RogueException("Não há nenhum contato cadastrado.");
    }

    private Levita inputToDomain(LevitaInput input){
        Levita levita = new Levita();
        if (input.getId() != null)
            levita.setId(input.getId());
        levita.setNome(input.getNome());
        levita.setInstrumentos(input.getInstrumentos().stream().map(instrumentoService::findById).toList());
        levita.setContato(input.getContato());
        levita.setEmail(input.getEmail());
        levita.setDescricao(input.getDescricao());
        return levita;
    }
}
