package imdl.scalator.service.mapper;

import imdl.scalator.domain.Escala;
import imdl.scalator.entity.EscalaEntity;

public class EscalaMapper {

    public static Escala entityToDomain(EscalaEntity entity){
        Escala domain = new Escala();
        domain.setId(entity.getId());
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
        if(entity.getBack() != null)
            domain.setBack(entity.getBack().stream().map(LevitaMapper::entityToDomain).toList());
        if(entity.getObservacoes() != null)
            domain.setObservacoes(entity.getObservacoes());
        return domain;
    }

    public static EscalaEntity domainToEntity(Escala domain){
        EscalaEntity entity = new EscalaEntity();
        if(domain.getId() != null)
            entity.setId(domain.getId());
        entity.setData(domain.getData());
        entity.setTitulo(domain.getTitulo());
        entity.setMinistro(LevitaMapper.domainToEntity(domain.getMinistro()));
        if(domain.getBaixo() != null)
            entity.setBaixo(LevitaMapper.domainToEntity(domain.getBaixo()));
        if(domain.getBateria() != null)
            entity.setBateria(LevitaMapper.domainToEntity(domain.getBateria()));
        if(domain.getTeclado() != null)
            entity.setTeclado(LevitaMapper.domainToEntity(domain.getTeclado()));
        if(domain.getViolao() != null)
            entity.setViolao(LevitaMapper.domainToEntity(domain.getViolao()));
        if(domain.getBack() != null)
            entity.setBack(domain.getBack().stream().map(LevitaMapper::domainToEntity).toList());
        if (domain.getObservacoes() != null)
            entity.setObservacoes(domain.getObservacoes());
        return entity;
    }
}
