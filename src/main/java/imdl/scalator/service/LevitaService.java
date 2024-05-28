package imdl.scalator.service;

import imdl.scalator.domain.Levita;
import imdl.scalator.persistence.LevitaRepository;
import imdl.scalator.service.mapper.LevitaMapper;

import java.util.UUID;

public class LevitaService {

    private final LevitaRepository levitaRepository;

    public LevitaService(LevitaRepository levitaRepository) {
        this.levitaRepository = levitaRepository;
    }

    public Levita findById(UUID id){
        Levita levita = LevitaMapper.entityToDomain(levitaRepository.findById(id).orElseThrow(RuntimeException::new));
        return levita;
    }
}
