package imdl.scalator.service;

import imdl.scalator.controller.filter.LevitaFilter;
import imdl.scalator.domain.exception.EntityNotFoundException;
import imdl.scalator.domain.Instrumento;
import imdl.scalator.domain.Levita;
import imdl.scalator.domain.exception.RogueException;
import imdl.scalator.domain.input.LevitaInput;
import imdl.scalator.persistence.LevitaRepository;
import imdl.scalator.service.mapper.InstrumentoMapper;
import imdl.scalator.service.mapper.LevitaMapper;

import java.time.LocalDate;
import java.util.*;

public class LevitaService {

    private final LevitaRepository levitaRepository;

    private final InstrumentoService instrumentoService;

    public LevitaService(LevitaRepository levitaRepository, InstrumentoService instrumentoService) {
        this.levitaRepository = levitaRepository;
        this.instrumentoService = instrumentoService;
    }

    public List<Levita> findAll(LevitaFilter filter){
        return levitaRepository.findAll(filter.nome(), filter.instrumento(), filter.disponivel()).stream()
                .map(LevitaMapper::entityToDomain).sorted(Comparator.comparing(Levita::getNome)).toList();
    }
    public List<Levita> findAllById(List<UUID> ids){
        return levitaRepository.findAllById(ids).stream().map(LevitaMapper::entityToDomain).toList();
    }

    public List<Levita> findAllDisponivel(LocalDate date){
        return levitaRepository.findAll().stream().map(LevitaMapper::entityToDomain)
                .filter(levita -> !levita.getAgenda().contains(date)).toList();
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
    public Levita update(UUID id, LevitaInput input){
        Levita levita = LevitaMapper.entityToDomain(levitaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Levita não encontrada.")));
        if(input.getNome() != null) {
            if (input.getNome().isBlank())
                throw new RogueException("O nome está vazio.");
            else
                levita.setNome(input.getNome());
        }
        if(input.getInstrumentos() != null)
            levita.setInstrumentos(input.getInstrumentos().stream().map(instrumentoService::findById).toList());
        if(input.getContato() != null)
            levita.setContato(input.getContato());
        if(input.getEmail() != null)
            levita.setEmail(input.getEmail());
        if(input.getDisponivel() != null)
            levita.setDisponivel(input.getDisponivel());
        levitaRepository.save(LevitaMapper.domainToEntity(levita));
        return levita;
    }

    public void deleteLevita(UUID id){
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

    public Levita changeDisponivel(UUID id){
        Levita levita = LevitaMapper.entityToDomain(levitaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Levita não encontrada.")));
        levita.setDisponivel(!levita.isDisponivel());
        levitaRepository.save(LevitaMapper.domainToEntity(levita));
        return levita;
    }

    public Levita addDataInAgenda(UUID id, LocalDate date){
        Levita levita = LevitaMapper.entityToDomain(levitaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Levita não encontrado.")));
        List<LocalDate> agenda = levita.getAgenda();
        if(agenda.contains(date))
            throw new RogueException("Data já inserida.");
        if(date.isBefore(LocalDate.now()))
            throw new RogueException("Data já passou.");
        agenda.add(date);
        levita.setAgenda(agenda);
        return LevitaMapper.entityToDomain(levitaRepository.save(LevitaMapper.domainToEntity(levita)));
    }

    private void validateInput(LevitaInput input){
        if(input.getNome() == null || input.getNome().isBlank())
            throw new RogueException("O nome está vazio.");
        if(input.getInstrumentos() == null)
            throw new RogueException("O instrumento está vazio.");
        if((input.getContato() == null||input.getContato().isBlank())
                && (input.getEmail() == null ||input.getEmail().isBlank()))
            throw new RogueException("Não há nenhum contato cadastrado.");
    }

    private Levita inputToDomain(LevitaInput input){
        Levita levita = new Levita();
        levita.setNome(input.getNome());
        levita.setInstrumentos(input.getInstrumentos().stream().map(instrumentoService::findById).toList());
        levita.setContato(input.getContato());
        levita.setEmail(input.getEmail());
        levita.setDisponivel(input.getDisponivel());
        return levita;
    }
}
