package imdl.scalator.service;

import imdl.scalator.domain.exception.EntityNotFoundException;
import imdl.scalator.domain.Instrumento;
import imdl.scalator.domain.Levita;
import imdl.scalator.domain.exception.RogueException;
import imdl.scalator.domain.input.LevitaInput;
import imdl.scalator.persistence.LevitaRepository;
import imdl.scalator.service.mapper.LevitaMapper;

import java.util.List;
import java.util.UUID;

public class LevitaService {

    private final LevitaRepository levitaRepository;

    public LevitaService(LevitaRepository levitaRepository) {
        this.levitaRepository = levitaRepository;
    }

    public List<Levita> findAll(){
        return levitaRepository.findAll().stream().map(LevitaMapper::entityToDomain).toList();
    }

    public Levita findById(UUID id){
        Levita levita = LevitaMapper.entityToDomain(levitaRepository.findById(id).orElseThrow(RuntimeException::new));
        return levita;
    }
    public Levita create(LevitaInput input){
        validateInput(input);
        Levita levita = inputToDomain(input);
        return LevitaMapper.entityToDomain(levitaRepository.save(LevitaMapper.domainToEntity(levita)));
    }
    public Levita update(UUID id, LevitaInput input){
        validateInput(input);
        Levita levita = LevitaMapper.entityToDomain(levitaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Levita não encontrado.")));
        levita.setNome(input.getNome());
        levita.setInstrumento(Instrumento.values()[input.getInstrumento()]);
        levita.setContato(input.getContato());
        levita.setEmail(input.getEmail());
        levita.setDisponivel(input.isDisponivel());
        levitaRepository.save(LevitaMapper.domainToEntity(levita));
        return levita;
    }

    public Levita changeDisponivel(UUID id){
        Levita levita = LevitaMapper.entityToDomain(levitaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Levita não encontrado.")));
        levita.setDisponivel(!levita.isDisponivel());
        levitaRepository.save(LevitaMapper.domainToEntity(levita));
        return levita;
    }

    private void validateInput(LevitaInput input){
        if(input.getNome().isBlank())
            throw new RogueException("O nome está vazio.");
        if(input.getInstrumento() == null)
            throw new RogueException("O instrumento está vazio.");
        if(input.getContato().isBlank() && input.getEmail().isBlank())
            throw new RogueException("Não há nenhum contato cadastrado.");
    }

    private Levita inputToDomain(LevitaInput input){
        Levita levita = new Levita();
        levita.setNome(input.getNome());
        levita.setInstrumento(Instrumento.values()[input.getInstrumento()]);
        levita.setContato(input.getContato());
        levita.setEmail(input.getEmail());
        levita.setDisponivel(input.isDisponivel());
        return levita;
    }
}
