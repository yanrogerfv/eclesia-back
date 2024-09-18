package imdl.scalator.service;

import imdl.scalator.controller.filter.LevitaFilter;
import imdl.scalator.domain.exception.EntityNotFoundException;
import imdl.scalator.domain.Instrumento;
import imdl.scalator.domain.Levita;
import imdl.scalator.domain.exception.RogueException;
import imdl.scalator.domain.input.LevitaInput;
import imdl.scalator.persistence.LevitaRepository;
import imdl.scalator.service.mapper.LevitaMapper;

import java.util.Comparator;
import java.util.List;
import java.util.UUID;

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
        return LevitaMapper.entityToDomain(levitaRepository.save(LevitaMapper.domainToEntity(levita)));
    }
    public Levita update(UUID id, LevitaInput input){
        Levita levita = LevitaMapper.entityToDomain(levitaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Levita não encontrada.")));
        if(input.getNome() != null)
            if(input.getNome().isBlank())
                throw new RogueException("O nome está vazio.");
            else
                levita.setNome(input.getNome());
        if(input.getInstrumentos() != null)
            levita.setInstrumentos(input.getInstrumentos().stream().map(instrumentoService::findById).toList());
        if(input.getContato() != null)
            levita.setContato(input.getContato());
        if(input.getEmail() != null)
            levita.setEmail(input.getEmail());
        levitaRepository.save(LevitaMapper.domainToEntity(levita));
        return levita;
    }

    public void deleteLevita(UUID id){
        levitaRepository.delete(levitaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Levita não encontrada.")));
    }

    public Levita addInstrumento(UUID id, Long instrumento){
        Levita levita = LevitaMapper.entityToDomain(levitaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Levita não encontrada.")));
        List<Instrumento> instrumentos = levita.getInstrumentos();
        instrumentos.add(instrumentoService.findById(instrumento));
        levita.setInstrumentos(instrumentos);
        return levita;
    }

    public Levita removeInstrumento(UUID id, Long instrumento){
        Levita levita = LevitaMapper.entityToDomain(levitaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Levita não encontrada.")));
        List<Instrumento> instrumentos = levita.getInstrumentos();
        if(!instrumentos.remove(instrumentoService.findById(instrumento)))
            throw new EntityNotFoundException("Levita já não tocava tal instrumento.");
        levita.setInstrumentos(instrumentos);
        return levita;
    }

    public Levita changeDisponivel(UUID id){
        Levita levita = LevitaMapper.entityToDomain(levitaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Levita não encontrada.")));
        levita.setDisponivel(!levita.isDisponivel());
        levitaRepository.save(LevitaMapper.domainToEntity(levita));
        return levita;
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
        levita.setDisponivel(input.isDisponivel());
        return levita;
    }
}
