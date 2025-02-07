package imdl.eclesia.service.mapper;

import imdl.eclesia.domain.Musica;
import imdl.eclesia.entity.MusicaEntity;

public class MusicaMapper {

    public static Musica entityToDomain(MusicaEntity entity){
        Musica domain = new Musica();
        domain.setId(entity.getId());
        domain.setNome(entity.getNome());
        domain.setLink(entity.getLink());
        domain.setCifra(entity.getCifra());
        return domain;
    }

    public static MusicaEntity domainToEntity(Musica domain){
        MusicaEntity entity = new MusicaEntity();
        entity.setId(domain.getId());
        entity.setNome(domain.getNome());
        entity.setLink(domain.getLink());
        entity.setCifra(domain.getCifra());
        return entity;
    }
}
