package imdl.scalator.service.mapper;

import imdl.scalator.domain.Instrumento;
import imdl.scalator.domain.Levita;
import imdl.scalator.entity.LevitaEntity;

public class LevitaMapper {

    public static Levita entityToDomain(LevitaEntity entity){
        Levita levita = new Levita();
        levita.setId(entity.getId());
        levita.setNome(entity.getNome());
        levita.setInstrumento(Instrumento.values()[entity.getInstrumento()]);
        levita.setContato(entity.getContato());
        levita.setEmail(entity.getEmail());
        return levita;
    }
}
