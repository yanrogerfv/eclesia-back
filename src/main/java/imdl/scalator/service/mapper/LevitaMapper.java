package imdl.scalator.service.mapper;

import imdl.scalator.domain.Instrumento;
import imdl.scalator.domain.Levita;
import imdl.scalator.entity.LevitaEntity;

public class LevitaMapper {

    public static Levita entityToDomain(LevitaEntity entity){
        Levita domain = new Levita();
        domain.setId(entity.getId());
        domain.setNome(entity.getNome());
        domain.setInstrumento(Instrumento.values()[entity.getInstrumento()]);
        domain.setContato(entity.getContato());
        domain.setEmail(entity.getEmail());
        domain.setDisponivel(entity.isDisponivel());
        return domain;
    }

    public static LevitaEntity domainToEntity(Levita domain){
        LevitaEntity entity = new LevitaEntity();
        entity.setId(domain.getId());
        entity.setNome(domain.getNome());
        entity.setInstrumento(domain.getInstrumento().ordinal());
        entity.setContato(domain.getContato());
        entity.setEmail(domain.getEmail());
        entity.setDisponivel(domain.isDisponivel());
        return entity;
    }

}
