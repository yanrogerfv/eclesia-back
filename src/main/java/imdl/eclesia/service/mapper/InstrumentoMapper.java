package imdl.eclesia.service.mapper;

import imdl.eclesia.domain.Instrumento;
import imdl.eclesia.entity.InstrumentoEntity;

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
