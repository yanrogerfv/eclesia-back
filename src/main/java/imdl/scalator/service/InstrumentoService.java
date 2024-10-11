package imdl.scalator.service;

import imdl.scalator.domain.Instrumento;
import imdl.scalator.domain.exception.EntityNotFoundException;
import imdl.scalator.domain.exception.RogueException;
import imdl.scalator.domain.input.InstrumentoInput;
import imdl.scalator.persistence.InstrumentoRepository;
import imdl.scalator.service.mapper.InstrumentoMapper;

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
        return InstrumentoMapper.entityToDomain(instrumentoRepository.save(InstrumentoMapper.domainToEntity(instrumento)));
    }

    public void deleteInstrumento(Long id){
        if(!instrumentoRepository.existsById(id))
            throw new RogueException("Não existe um instrumento com este ID.");
        instrumentoRepository.deleteById(id);
    }
}
