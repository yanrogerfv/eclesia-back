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
        escala.setMinistro(findLevita(entity.getMinistro()));
        escala.setBaixo(findLevita(entity.getBaixo()));
        escala.setBateria(findLevita(entity.getBateria()));
        escala.setTeclado(findLevita(entity.getTeclado()));
        escala.setViolao(findLevita(entity.getViolao()));
        escala.setData(entity.getData());
        escala.setId(entity.getEscalaId());
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
        entity.setMusicas(musicasRepository.findAllById(input.getMusicas()));
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
        escala.setTitulo(input.getTitulo());
        escala.setMinistro(findLevita(input.getMinistro()));
        escala.setBaixo(findLevita(input.getBaixo()));
        escala.setBateria(findLevita(input.getBateria()));
        escala.setTeclado(findLevita(input.getTeclado()));
        escala.setViolao(findLevita(input.getTeclado()));
        escala.setBack(levitaRepository.findAllById(input.getBacks()).stream().map(LevitaMapper::entityToDomain).toList());
        escala.setObservacoes(input.getObservacoes());
        escala.setData(input.getData());
        return escala;
    }

    private Levita findLevita(UUID levitaId){
        return LevitaMapper.entityToDomain(levitaRepository.findById(levitaId)
                .orElseThrow(() -> new EntityNotFoundException("Levita não encontrada.")));
    }

}
