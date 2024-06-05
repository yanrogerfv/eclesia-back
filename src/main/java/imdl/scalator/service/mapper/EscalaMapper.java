package imdl.scalator.service.mapper;

import imdl.scalator.domain.Escala;
import imdl.scalator.entity.EscalaEntity;

public class EscalaMapper {

    public static Escala entityToDomain(EscalaEntity entity){
        Escala domain = new Escala();
        domain.setData(entity.getData());
        domain.setTitulo(entity.getTitulo());
        domain.setMinistro(LevitaMapper.entityToDomain(entity.getMinistro()));
        if(entity.getBaixo() != null)
            domain.setBaixo(LevitaMapper.entityToDomain(entity.getBaixo()));
        if(entity.getBateria() != null)
            domain.setBateria(LevitaMapper.entityToDomain(entity.getBateria()));
        if(entity.getTeclado() != null)
            domain.setTeclado(LevitaMapper.entityToDomain(entity.getTeclado()));
        if(entity.getViolao() != null)
            domain.setViolao(LevitaMapper.entityToDomain(entity.getViolao()));
//        domain.setBack(entity.getBack().stream().map(LevitaMapper::entityToDomain).toList());
        return domain;
    }

    public static EscalaEntity domainToEntity(Escala escala){
        EscalaEntity entity = new EscalaEntity();
        if(escala.getId() != null)
            entity.setEscalaId(escala.getId());
        entity.setData(escala.getData());
        entity.setTitulo(escala.getTitulo());
        entity.setMinistro(LevitaMapper.domainToEntity(escala.getMinistro()));
        if(escala.getBaixo() != null)
            entity.setBaixo(LevitaMapper.domainToEntity(escala.getBaixo()));
        if(escala.getBateria() != null)
            entity.setBateria(LevitaMapper.domainToEntity(escala.getBateria()));
        if(escala.getTeclado() != null)
            entity.setTeclado(LevitaMapper.domainToEntity(escala.getTeclado()));
        if(escala.getViolao() != null)
            entity.setViolao(LevitaMapper.domainToEntity(escala.getViolao()));
        if (escala.getObservacoes() != null)
            entity.setObservacoes(escala.getObservacoes());
        return entity;
    }
}
