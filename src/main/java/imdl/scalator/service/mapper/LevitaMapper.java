package imdl.scalator.service.mapper;

import imdl.scalator.domain.Levita;
import imdl.scalator.entity.LevitaEntity;

public class LevitaMapper {

    public static Levita entityToDomain(LevitaEntity entity){
        Levita domain = new Levita();
        domain.setId(entity.getId());
        domain.setNome(entity.getNome());
        domain.setInstrumentos(entity.getInstrumentos().stream().map(InstrumentoMapper::entityToDomain).toList());
        domain.setContato(entity.getContato());
        domain.setEmail(entity.getEmail());
        domain.setDisponivel(entity.isDisponivel());
        return domain;
    }

    public static LevitaEntity domainToEntity(Levita domain){
        LevitaEntity entity = new LevitaEntity();
        entity.setId(domain.getId());
        entity.setNome(domain.getNome());
        entity.setInstrumentos(domain.getInstrumentos().stream().map(InstrumentoMapper::domainToEntity).toList());
        entity.setContato(domain.getContato());
        entity.setEmail(domain.getEmail());
        entity.setDisponivel(domain.isDisponivel());
        return entity;
    }

}
