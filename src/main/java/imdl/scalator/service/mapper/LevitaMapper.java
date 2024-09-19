package imdl.scalator.service.mapper;

import imdl.scalator.domain.Instrumento;
import imdl.scalator.domain.Levita;
import imdl.scalator.entity.LevitaEntity;

import java.util.Comparator;

public class LevitaMapper {

    public static Levita entityToDomain(LevitaEntity entity){
        Levita domain = new Levita();
        domain.setId(entity.getId());
        domain.setNome(entity.getNome());
        domain.setInstrumentos(entity.getInstrumentos().stream().map(InstrumentoMapper::entityToDomain).sorted(Comparator.comparingLong(Instrumento::getId)).toList());
        domain.setContato(entity.getContato());
        domain.setEmail(entity.getEmail());
        domain.setDisponivel(entity.isDisponivel());
        domain.setAgenda(entity.getAgenda());
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
        entity.setAgenda(domain.getAgenda());
        return entity;
    }

}
