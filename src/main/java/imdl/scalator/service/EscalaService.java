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
import imdl.scalator.persistence.MusicaRepository;
import imdl.scalator.service.mapper.EscalaMapper;
import imdl.scalator.service.mapper.LevitaMapper;
import imdl.scalator.service.mapper.MusicaMapper;

import java.util.List;
import java.util.UUID;

public class EscalaService {

    private final EscalaRepository escalaRepository;
    private final LevitaRepository levitaRepository;
    private final MusicaRepository musicaRepository;

    public EscalaService(EscalaRepository escalaRepository, LevitaRepository levitaRepository, MusicaRepository musicaRepository) {
        this.escalaRepository = escalaRepository;
        this.levitaRepository = levitaRepository;
        this.musicaRepository = musicaRepository;
    }

    public List<Escala> findAllEscalas(){
        return escalaRepository.findAll().stream().map(EscalaMapper::entityToDomain).toList();
    }

    public List<Escala> findMonthEscalas(int month){
        return escalaRepository.findAllInMonth(month).stream().map(EscalaMapper::entityToDomain).toList();
    }

    public Escala findById(UUID id){
        EscalaEntity entity = escalaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Escala não encontrada."));
        Escala escala = EscalaMapper.entityToDomain(entity);
//        escala.setMusicas(musicaRepository.findAllInEscala(escala.getId()).stream().map(MusicaMapper::entityToDomain).toList());
        return escala;
    }

    public Escala create(EscalaInput input){
        validateInput(input);
        Escala escala = inputToDomain(input);
        escalaRepository.save(EscalaMapper.domainToEntity(escala));
        return escala;
    }

    public Escala update(UUID id, EscalaInput input){
        Escala escala = EscalaMapper.entityToDomain(escalaRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Escala não encontrada.")));
        if(input.getData() != null)
            escala.setData(input.getData());
        if(input.getTitulo() != null && !input.getTitulo().isBlank())
            escala.setTitulo(input.getTitulo());
        else
            throw new RogueException("Título não pode estar vazio.");
        if(input.getMinistro() != null)
            escala.setMinistro(findLevita(input.getMinistro(), "Ministro"));
        if(input.getBaixo() != null)
            escala.setBaixo(findLevita(input.getBaixo(), "Baixista"));
        if(input.getBateria() != null)
            escala.setBateria(findLevita(input.getBateria(), "Baterista"));
        if(input.getGuitarra() != null)
            escala.setGuitarra(findLevita(input.getGuitarra(), "Guitarrista"));
        if(input.getTeclado() != null)
            escala.setTeclado(findLevita(input.getTeclado(), "Tecladista"));
        if(input.getViolao() != null)
            escala.setViolao(findLevita(input.getViolao(), "Violão"));
        if(input.getBacks() != null)
            escala.setBack(levitaRepository.findAllById(input.getBacks()).stream().map(LevitaMapper::entityToDomain).toList());
        if (input.getObservacoes() != null)
            escala.setObservacoes(input.getObservacoes());
        escalaRepository.save(EscalaMapper.domainToEntity(escala));
        return escala;
    }

    public void deleteEscala(UUID id){
        escalaRepository.delete(escalaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Escala não encontrada.")));
    }

    public List<Musica> findMusicasInEscala(UUID escalaId){
        if(escalaRepository.findById(escalaId).isEmpty())
            throw new EntityNotFoundException("Escala não encontrada.");
        return escalaRepository.findAllMusicasInEscala(escalaId).stream().map(MusicaMapper::entityToDomain).toList();
    }

    public Escala addMusicaInEscala(UUID escalaId, UUID musicaId){
        Escala escala = EscalaMapper.entityToDomain(escalaRepository.findById(escalaId)
                .orElseThrow(() -> new EntityNotFoundException("Escala não encontrada")));
        List<Musica> musicas = escala.getMusicas();
        musicas.add(MusicaMapper.entityToDomain(musicaRepository.findById(musicaId)
                .orElseThrow(() -> new EntityNotFoundException("Música não encontrada"))));
        escala.setMusicas(musicas);
        return EscalaMapper.entityToDomain(escalaRepository.save(EscalaMapper.domainToEntity(escala)));
    }

    public Escala removeMusicaInEscala(UUID escalaId, UUID musicaId){
        Escala escala = EscalaMapper.entityToDomain(escalaRepository.findById(escalaId)
                .orElseThrow(() -> new EntityNotFoundException("Escala não encontrada")));
        List<Musica> musicas = escala.getMusicas();
        musicas.remove(MusicaMapper.entityToDomain(musicaRepository.findById(musicaId)
                .orElseThrow(() -> new EntityNotFoundException("Música não encontrada"))));
        escala.setMusicas(musicas);
        return EscalaMapper.entityToDomain(escalaRepository.save(EscalaMapper.domainToEntity(escala)));
    }

    private void validateInput(EscalaInput input){
        if(input.getData() == null)
            throw new RogueException("A escala está sem data");
        if(input.getTitulo() == null || input.getTitulo().isBlank())
            throw new RogueException("A escala está sem título.");
        if(input.getMinistro() == null)
            throw new RogueException("Favor inserir um ministro para a escala.");
    }
    private Escala inputToDomain(EscalaInput input){
        Escala escala = new Escala();
        escala.setData(input.getData());
        escala.setTitulo(input.getTitulo());
        escala.setMinistro(findLevita(input.getMinistro(), "Ministra"));
        if(input.getBaixo() != null)
            escala.setBaixo(findLevita(input.getBaixo(), "Baixista"));
        if(input.getBateria() != null)
            escala.setBateria(findLevita(input.getBateria(), "Baterista"));
        if(input.getGuitarra() != null)
            escala.setGuitarra(findLevita(input.getGuitarra(), "Guitarrista"));
        if(input.getTeclado() != null)
            escala.setTeclado(findLevita(input.getTeclado(), "Tecladista"));
        if(input.getViolao() != null)
            escala.setViolao(findLevita(input.getViolao(), "Violão"));
        if(input.getBacks() != null)
            escala.setBack(levitaRepository.findAllById(input.getBacks()).stream().map(LevitaMapper::entityToDomain).toList());
        if (input.getObservacoes() != null)
            escala.setObservacoes(input.getObservacoes());
        return escala;
    }

    private Levita findLevita(UUID levitaId, String instrumentista){
        return LevitaMapper.entityToDomain(levitaRepository.findById(levitaId)
                .orElseThrow(() -> new EntityNotFoundException(instrumentista + " não encontrada.")));
    }

}
