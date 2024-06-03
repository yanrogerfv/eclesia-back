package imdl.scalator.service.mapper;

import imdl.scalator.domain.Escala;
import imdl.scalator.entity.EscalaEntity;

public class EscalaMapper {

    public static Escala entityToDomain(EscalaEntity entity){
        Escala domain = new Escala();
        domain.setMinistro(LevitaMapper.entityToDomain(entity.getMinistro()));
        domain.setBaixo(LevitaMapper.entityToDomain(entity.getBaixo()));
        domain.setBateria(LevitaMapper.entityToDomain(entity.getBateria()));
        domain.setTeclado(LevitaMapper.entityToDomain(entity.getTeclado()));
        domain.setViolao(LevitaMapper.entityToDomain(entity.getViolao()));
        domain.setBack(entity.getBack().stream().map(LevitaMapper::entityToDomain).toList());
        domain.setData(entity.getData());
        return domain;
    }

    public static EscalaEntity domainToEntity(Escala escala){
        EscalaEntity entity = new EscalaEntity();
        entity.setEscalaId(escala.getId());
        entity.setTitulo(escala.getTitulo());
        entity.setMinistro(LevitaMapper.domainToEntity(escala.getMinistro()));
        entity.setBaixo(LevitaMapper.domainToEntity(escala.getBaixo()));
        entity.setBateria(LevitaMapper.domainToEntity(escala.getBateria()));
        entity.setViolao(LevitaMapper.domainToEntity(escala.getViolao()));

        return entity;
    }
}
