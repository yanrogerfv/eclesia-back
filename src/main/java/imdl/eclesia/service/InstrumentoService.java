package imdl.eclesia.service;

import imdl.eclesia.domain.Instrumento;
import imdl.eclesia.domain.event.InstrumentoEvent;
import imdl.eclesia.domain.exception.EntityNotFoundException;
import imdl.eclesia.domain.exception.RogueException;
import imdl.eclesia.domain.input.InstrumentoInput;
import imdl.eclesia.persistence.InstrumentoRepository;
import imdl.eclesia.service.mapper.InstrumentoMapper;
import imdl.eclesia.service.utils.events.LogAction;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;

public class InstrumentoService {

    private final InstrumentoRepository instrumentoRepository;
    private final ApplicationEventPublisher eventPublisher;

    public InstrumentoService(InstrumentoRepository instrumentoRepository, ApplicationEventPublisher eventPublisher) {
        this.instrumentoRepository = instrumentoRepository;
        this.eventPublisher = eventPublisher;
    }

    public List<Instrumento> findAll() {
        return instrumentoRepository.findAll().stream().map(InstrumentoMapper::entityToDomain).toList();
    }

    public Instrumento findById(Long numero) {
        return InstrumentoMapper.entityToDomain(instrumentoRepository.findById(numero)
                .orElseThrow(() -> new EntityNotFoundException("Instrumento não encontrado.")));
    }

    public Instrumento createInstrumento(InstrumentoInput input) {
        if (instrumentoRepository.existsByNome(input.getNome().toUpperCase()))
            throw new RogueException("Já existe um instrumento com este nome.");
        Instrumento instrumento = new Instrumento();
        instrumento.setNome(input.getNome().toUpperCase());
        instrumento.setId(instrumentoRepository.findNextSequential());
        Instrumento created = InstrumentoMapper.entityToDomain(instrumentoRepository.save(InstrumentoMapper.domainToEntity(instrumento)));
        publishInstrumentoEvent(LogAction.CRIAR_INSTRUMENTO, created);
        return created;
    }

    public void deleteInstrumento(Long id) {
        if (!instrumentoRepository.existsById(id))
            throw new RogueException("Não existe um instrumento com este ID.");
        var instrumentoEntity = instrumentoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Instrumento não encontrado."));
        if (instrumentoRepository.isInstrumentInUse(instrumentoEntity))
            throw new RogueException("Não é possível excluir este instrumento, pois ele está associado a um levita.");
        Instrumento instrumento = InstrumentoMapper.entityToDomain(instrumentoEntity);
        publishInstrumentoEvent(LogAction.EXCLUIR_INSTRUMENTO, instrumento);
        instrumentoRepository.deleteById(id);
    }

    private void publishInstrumentoEvent(LogAction action, Instrumento instrumento) {
        String user = SecurityContextHolder.getContext().getAuthentication().getName();
        eventPublisher.publishEvent(new InstrumentoEvent(instrumento, action, user));
    }
}
