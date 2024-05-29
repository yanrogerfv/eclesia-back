package imdl.scalator.service.mapper;

import imdl.scalator.domain.Musica;
import imdl.scalator.entity.MusicaEntity;

public class MusicaMapper {

    public static Musica entityToDomain(MusicaEntity entity){
        Musica domain = new Musica();
        domain.setId(entity.getId());
        domain.setNome(entity.getNome());
        domain.setLink(entity.getLink());
        domain.setEscala(entity.getEscala());
        return domain;
    }
}
