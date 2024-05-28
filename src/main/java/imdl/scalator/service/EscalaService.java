package imdl.scalator.service;

import imdl.scalator.domain.EntityNotFoundException;
import imdl.scalator.domain.Escala;
import imdl.scalator.domain.Musica;
import imdl.scalator.entity.EscalaEntity;
import imdl.scalator.persistence.EscalaRepository;
import imdl.scalator.service.mapper.EscalaMapper;

import java.util.ArrayList;
import java.util.List;

public class EscalaService {

    private final EscalaRepository escalaRepository;

    public EscalaService(EscalaRepository escalaRepository) {
        this.escalaRepository = escalaRepository;
    }

    public List<Escala> findAllEscalas(){
        return null;
    }

    public List<Escala> findMonthEscalas(){
        return null;
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
