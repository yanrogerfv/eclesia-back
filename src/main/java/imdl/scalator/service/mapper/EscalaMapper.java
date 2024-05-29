package imdl.scalator.service.mapper;

import imdl.scalator.domain.Escala;
import imdl.scalator.entity.EscalaEntity;
import imdl.scalator.service.LevitaService;

public class EscalaMapper {

    private final LevitaService levitaService;

    public EscalaMapper(LevitaService levitaService) {
        this.levitaService = levitaService;
    }

    public Escala entityToDomain(EscalaEntity entity){
        Escala domain = new Escala();
        domain.setMinistro(levitaService.findById(entity.getMinistro()));
        domain.setBaixo(levitaService.findById(entity.getBaixo()));
        domain.setBateria(levitaService.findById(entity.getBateria()));
        domain.setTeclado(levitaService.findById(entity.getTeclado()));
        domain.setViolao(levitaService.findById(entity.getViolao()));
//        domain.setBack(entity.getBacks());
        domain.setDate(entity.getDate());
        return domain;
    }
}
