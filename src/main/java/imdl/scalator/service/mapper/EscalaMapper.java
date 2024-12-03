package imdl.scalator.service.mapper;

import imdl.scalator.domain.Escala;
import imdl.scalator.domain.EscalaResumed;
import imdl.scalator.entity.EscalaEntity;
import imdl.scalator.entity.EscalaResumedEntity;

public class EscalaMapper {

    public static Escala entityToDomain(EscalaEntity entity){
        Escala domain = new Escala();
        domain.setId(entity.getId());
        domain.setData(entity.getData());
        domain.setTitulo(entity.getTitulo());
        domain.setQuarta(entity.isQuarta());
        domain.setDomingo(entity.isDomingo());
        domain.setEspecial(entity.isEspecial());
        if(entity.getMinistroNome() != null)
            domain.setMinistroNome(entity.getMinistroNome());
        domain.setMinistro(LevitaMapper.entityToDomain(entity.getMinistro()));
        if(entity.getBaixo() != null) {
            domain.setBaixoNome(entity.getBaixoNome());
            domain.setBaixo(LevitaMapper.entityToDomain(entity.getBaixo()));
        } if(entity.getBateria() != null) {
            domain.setBateriaNome(entity.getBateriaNome());
            domain.setBateria(LevitaMapper.entityToDomain(entity.getBateria()));
        } if(entity.getGuitarra() != null) {
            domain.setGuitarraNome(entity.getGuitarraNome());
            domain.setGuitarra(LevitaMapper.entityToDomain(entity.getGuitarra()));
        } if(entity.getTeclado() != null) {
            domain.setTecladoNome(entity.getTecladoNome());
            domain.setTeclado(LevitaMapper.entityToDomain(entity.getTeclado()));
        } if(entity.getViolao() != null) {
            domain.setViolaoNome(entity.getViolaoNome());
            domain.setViolao(LevitaMapper.entityToDomain(entity.getViolao()));
        } if(entity.getBack() != null)
            domain.setBack(entity.getBack().stream().map(LevitaMapper::entityToDomain).toList());
        if(entity.getMusicas()!=null && !entity.getMusicas().isEmpty())
            domain.setMusicas(entity.getMusicas().stream().map(MusicaMapper::entityToDomain).toList());
        if(entity.getObservacoes() != null)
            domain.setObservacoes(entity.getObservacoes());
        return domain;
    }

    public static EscalaResumed entityToDomainResumida(EscalaResumedEntity entity){
        EscalaResumed domain = new EscalaResumed();
        domain.setId(entity.getId());
        domain.setData(entity.getData());
        domain.setTitulo(entity.getTitulo());
        domain.setQuarta(entity.isQuarta());
        domain.setDomingo(entity.isDomingo());
        domain.setEspecial(entity.isEspecial());
        domain.setMinistro(entity.getMinistroNome());
        if(entity.getBaixoNome() != null)
            domain.setBaixo(entity.getBaixoNome());
        if(entity.getBateriaNome() != null)
            domain.setBateria(entity.getBateriaNome());
        if(entity.getGuitarraNome() != null)
            domain.setGuitarra(entity.getGuitarraNome());
        if(entity.getTecladoNome() != null)
            domain.setTeclado(entity.getTecladoNome());
        if(entity.getViolaoNome() != null)
            domain.setViolao(entity.getViolaoNome());
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
        entity.setQuarta(domain.isQuarta());
        entity.setDomingo(domain.isDomingo());
        entity.setEspecial(domain.isEspecial());
        entity.setMinistroNome(domain.getMinistro().getNome());
        entity.setMinistro(LevitaMapper.domainToEntity(domain.getMinistro()));
        if(domain.getBaixo() != null) {
            entity.setBaixoNome(domain.getBaixo().getNome());
            entity.setBaixo(LevitaMapper.domainToEntity(domain.getBaixo()));
        } if(domain.getBateria() != null) {
            entity.setBateriaNome(domain.getBateria().getNome());
            entity.setBateria(LevitaMapper.domainToEntity(domain.getBateria()));
        } if(domain.getGuitarra() != null) {
            entity.setGuitarraNome(domain.getGuitarra().getNome());
            entity.setGuitarra(LevitaMapper.domainToEntity(domain.getGuitarra()));
        } if(domain.getTeclado() != null) {
            entity.setTecladoNome(domain.getTeclado().getNome());
            entity.setTeclado(LevitaMapper.domainToEntity(domain.getTeclado()));
        } if(domain.getViolao() != null) {
            entity.setViolaoNome(domain.getViolao().getNome());
            entity.setViolao(LevitaMapper.domainToEntity(domain.getViolao()));
        } if(domain.getBack() != null)
            entity.setBack(domain.getBack().stream().map(LevitaMapper::domainToEntity).toList());
        if(domain.getMusicas()!=null && !domain.getMusicas().isEmpty())
            entity.setMusicas(domain.getMusicas().stream().map(MusicaMapper::domainToEntity).toList());
        if (domain.getObservacoes() != null)
            entity.setObservacoes(domain.getObservacoes());
        return entity;
    }
}
