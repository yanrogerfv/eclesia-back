package imdl.scalator.service;

import imdl.scalator.domain.EntityNotFoundException;
import imdl.scalator.domain.Escala;
import imdl.scalator.domain.Musica;
import imdl.scalator.entity.EscalaEntity;
import imdl.scalator.persistence.EscalaRepository;
import imdl.scalator.persistence.LevitaRepository;
import imdl.scalator.persistence.MusicasRepository;
import imdl.scalator.service.mapper.LevitaMapper;

import java.util.List;
import java.util.UUID;

public class EscalaService {

    private final EscalaRepository escalaRepository;
    private final LevitaRepository levitaRepository;
    private final MusicasRepository musicasRepository;

    public EscalaService(EscalaRepository escalaRepository, LevitaRepository levitaRepository, MusicasRepository musicasRepository) {
        this.escalaRepository = escalaRepository;
        this.levitaRepository = levitaRepository;
        this.musicasRepository = musicasRepository;
    }

    public List<Escala> findAllEscalas(){
        return null;
    }

    public List<Escala> findMonthEscalas(){
        return null;
    }

    public Escala findById(UUID id){
        EscalaEntity entity = escalaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Escala não encontrada."));
        Escala escala = new Escala();
        escala.setMinistro(LevitaMapper.entityToDomain(levitaRepository.findById(entity
                .getMinistro()).orElseThrow(() -> new EntityNotFoundException("Escala não encontrada."))));
        escala.setBaixo(LevitaMapper.entityToDomain(levitaRepository.findById(entity
                .getBaixo()).orElseThrow(() -> new EntityNotFoundException("Escala não encontrada."))));
        escala.setBateria(LevitaMapper.entityToDomain(levitaRepository.findById(entity
                .getBateria()).orElseThrow(() -> new EntityNotFoundException("Escala não encontrada."))));
        escala.setTeclado(LevitaMapper.entityToDomain(levitaRepository.findById(entity
                .getTeclado()).orElseThrow(() -> new EntityNotFoundException("Escala não encontrada."))));
        escala.setViolao(LevitaMapper.entityToDomain(levitaRepository.findById(entity
                .getViolao()).orElseThrow(() -> new EntityNotFoundException("Escala não encontrada."))));
        escala.setDate(entity.getDate());
        escala.setId(entity.getEscalaId());
//        escala.setMusicas(entity.getMusicas().forEach(musicaId -> {
//            return musicasRepository.findById(musicaId).orElseThrow(() -> new EntityNotFoundException("Música não encontrada."));
//        }));
        return escala;
    }

    public Escala create(Escala escala){
        return escala;
    }

    public Escala update(Escala escala){
        EscalaEntity entity = escalaRepository.findById(escala.getId()).orElseThrow(() -> new EntityNotFoundException("Escala não encontrada."));
        entity.setEscalaId(escala.getId());
        entity.setMinistro(escala.getMinistro().getId());
        entity.setBaixo(escala.getBaixo().getId());
        entity.setBateria(escala.getBateria().getId());
        entity.setTeclado(escala.getTeclado().getId());
        entity.setViolao(escala.getViolao().getId());
        entity.setMusicas(escala.getMusicas().stream().map(Musica::getId).toList());
        entity.setDate(escala.getDate());
        return escala;
    }

}
