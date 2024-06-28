package imdl.scalator.service.mapper;

import imdl.scalator.domain.Instrumento;
import imdl.scalator.entity.InstrumentoEntity;

public class InstrumentoMapper {

    public static Instrumento entityToDomain(InstrumentoEntity entity){
        Instrumento domain = new Instrumento();
        domain.setId(entity.getId());
        domain.setNome(entity.getNome());
        return domain;
    }

    public static InstrumentoEntity domainToEntity(Instrumento domain){
        InstrumentoEntity entity = new InstrumentoEntity();
        entity.setId(domain.getId());
        entity.setNome(domain.getNome());
        return entity;
    }
}
