package imdl.eclesia.service;

import imdl.eclesia.domain.Instrumento;
import imdl.eclesia.domain.exception.EntityNotFoundException;
import imdl.eclesia.domain.exception.RogueException;
import imdl.eclesia.domain.input.InstrumentoInput;
import imdl.eclesia.persistence.InstrumentoRepository;
import imdl.eclesia.service.mapper.InstrumentoMapper;

import java.util.List;

public class InstrumentoService {

    private final InstrumentoRepository instrumentoRepository;

    public InstrumentoService(InstrumentoRepository instrumentoRepository) {
        this.instrumentoRepository = instrumentoRepository;
    }

    public List<Instrumento> findAll(){
        return instrumentoRepository.findAll().stream().map(InstrumentoMapper::entityToDomain).toList();
    }

    public Instrumento findById(Long numero){
        return InstrumentoMapper.entityToDomain(instrumentoRepository.findById(numero)
                .orElseThrow(() -> new EntityNotFoundException("Instrumento não encontrado.")));
    }

    public Instrumento createInstrumento(InstrumentoInput input){
        if(instrumentoRepository.existsByNome(input.getNome().toUpperCase()))
            throw new RogueException("Já existe um instrumento com este nome.");
        Instrumento instrumento = new Instrumento();
        instrumento.setNome(input.getNome().toUpperCase());
        instrumento.setId(instrumentoRepository.findNextSequential());
        return InstrumentoMapper.entityToDomain(instrumentoRepository.save(InstrumentoMapper.domainToEntity(instrumento)));
    }

    public void deleteInstrumento(Long id){
        if(!instrumentoRepository.existsById(id))
            throw new RogueException("Não existe um instrumento com este ID.");
        if(instrumentoRepository.existsByLevita(id))
            throw new RogueException("Não é possível excluir um instrumento que está vinculado a um levita.");
        instrumentoRepository.deleteById(id);
    }
}
