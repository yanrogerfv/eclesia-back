package imdl.scalator.service;

import imdl.scalator.domain.exception.EntityNotFoundException;
import imdl.scalator.domain.Escala;
import imdl.scalator.domain.Levita;
import imdl.scalator.domain.Musica;
import imdl.scalator.domain.exception.RogueException;
import imdl.scalator.domain.input.EscalaInput;
import imdl.scalator.entity.EscalaEntity;
import imdl.scalator.persistence.EscalaRepository;
import imdl.scalator.persistence.LevitaRepository;
import imdl.scalator.persistence.MusicasRepository;
import imdl.scalator.service.mapper.EscalaMapper;
import imdl.scalator.service.mapper.LevitaMapper;
import imdl.scalator.service.mapper.MusicaMapper;

import java.util.List;
import java.util.UUID;

public class EscalaService {

    private final EscalaRepository escalaRepository;
    private final LevitaRepository levitaRepository;
    private final MusicasRepository musicasRepository;

    public EscalaService(EscalaRepository escalaRepository, LevitaRepository levitaRepository, MusicasRepository musicasRepository) {
        this.escalaRepository = escalaRepository;
        this.levitaRepository = levitaRepository;
        this.musicasRepository = musicasRepository;
    }

    public List<Escala> findAllEscalas(){
        return null;
    }

    public List<Escala> findMonthEscalas(){
        return null;
    }

    public Escala findById(UUID id){
        EscalaEntity entity = escalaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Escala não encontrada."));
        Escala escala = new Escala();
        escala.setId(entity.getEscalaId());
        escala.setData(entity.getData());
        escala.setMinistro(LevitaMapper.entityToDomain(entity.getMinistro()));
        escala.setBaixo(LevitaMapper.entityToDomain(entity.getBaixo()));
        escala.setBateria(LevitaMapper.entityToDomain(entity.getBateria()));
        escala.setTeclado(LevitaMapper.entityToDomain(entity.getTeclado()));
        escala.setViolao(LevitaMapper.entityToDomain(entity.getViolao()));
        escala.setMusicas(musicasRepository.findAllInEscala(escala.getId()).stream().map(MusicaMapper::entityToDomain).toList());
        return escala;
    }

    public Escala create(EscalaInput input){
        validateInput(input);
        Escala escala = inputToDomain(input);


        return escala;
    }

    public Escala update(UUID id, EscalaInput input){
        EscalaEntity entity = escalaRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Escala não encontrada."));
        entity.setMinistro(levitaRepository.findById(input.getMinistro()).orElseThrow(() -> new EntityNotFoundException("")));
        entity.setBaixo(levitaRepository.findById(input.getBaixo()).orElseThrow(() -> new EntityNotFoundException("")));
        entity.setBateria(levitaRepository.findById(input.getBateria()).orElseThrow(() -> new EntityNotFoundException("")));
        entity.setTeclado(levitaRepository.findById(input.getTeclado()).orElseThrow(() -> new EntityNotFoundException("")));
        entity.setViolao(levitaRepository.findById(input.getViolao()).orElseThrow(() -> new EntityNotFoundException("")));
//        entity.setMusicas(musicasRepository.findAllById(input.getMusicas()));
        //setar o back
        entity.setData(input.getData());
        return EscalaMapper.entityToDomain(entity);
    }

    private List<Musica> findMusicas(List<UUID> musicasId){
        return musicasRepository.findAllById(musicasId).stream().map(MusicaMapper::entityToDomain).toList();
    }

    private void validateInput(EscalaInput input){
        if(input.getTitulo().isBlank())
            throw new RogueException("A escala está sem título.");
        if(input.getMinistro() == null)
            throw new RogueException("Favor inserir um ministro para a escala.");
        if(input.getMusicas() == null)
            throw new RogueException("A escala está sem músicas.");
    }
    private Escala inputToDomain(EscalaInput input){
        Escala escala = new Escala();
        escala.setData(input.getData());
        escala.setTitulo(input.getTitulo());
        escala.setMinistro(findLevita(input.getMinistro()));
        if(input.getBaixo() != null)
            escala.setBaixo(findLevita(input.getBaixo()));
        if(input.getBateria() != null)
            escala.setBateria(findLevita(input.getBateria()));
        if(input.getTeclado() != null)
            escala.setTeclado(findLevita(input.getTeclado()));
        if(input.getViolao() != null)
            escala.setViolao(findLevita(input.getTeclado()));
        if(input.getBacks() != null)
            escala.setBack(levitaRepository.findAllById(input.getBacks()).stream().map(LevitaMapper::entityToDomain).toList());
        if (input.getObservacoes() != null)
            escala.setObservacoes(input.getObservacoes());
        return escala;
    }

    private Levita findLevita(UUID levitaId){
        return LevitaMapper.entityToDomain(levitaRepository.findById(levitaId)
                .orElseThrow(() -> new EntityNotFoundException("Levita não encontrada.")));
    }

}
