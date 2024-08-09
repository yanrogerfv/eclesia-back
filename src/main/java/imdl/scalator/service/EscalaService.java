package imdl.scalator.service;

import imdl.scalator.domain.exception.EntityNotFoundException;
import imdl.scalator.domain.Escala;
import imdl.scalator.domain.Levita;
import imdl.scalator.domain.Musica;
import imdl.scalator.domain.exception.RogueException;
import imdl.scalator.domain.input.EscalaInput;
import imdl.scalator.persistence.EscalaRepository;
import imdl.scalator.service.mapper.EscalaMapper;
import imdl.scalator.service.mapper.MusicaMapper;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public class EscalaService {

    private final EscalaRepository escalaRepository;
    private final LevitaService levitaService;
    private final MusicaService musicaService;

    public EscalaService(EscalaRepository escalaRepository, LevitaService levitaService, MusicaService musicaService) {
        this.escalaRepository = escalaRepository;
        this.levitaService = levitaService;
        this.musicaService = musicaService;
    }

    public List<Escala> findAllEscalas(){
        return escalaRepository.findAll().stream().map(EscalaMapper::entityToDomain).toList();
    }

    public List<Escala> findMonthEscalas(int month){
        return escalaRepository.findAllInMonth(month).stream().map(EscalaMapper::entityToDomain).toList();
    }

    public Escala findById(UUID id){
        return escalaRepository.findById(id).map(EscalaMapper::entityToDomain)
                .orElseThrow(() -> new EntityNotFoundException("Escala não encontrada."));
    }

    public Escala create(EscalaInput input){
        validateInput(input);
        Escala escala = inputToDomain(input);
        return EscalaMapper.entityToDomain(escalaRepository.save(EscalaMapper.domainToEntity(escala)));
    }

    public Escala update(UUID id, EscalaInput input){
        validateInput(input);
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
            escala.setBack(levitaService.findAllById(input.getBacks()));
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
        Escala escala = findById(escalaId);
        List<Musica> musicas = escala.getMusicas();
        musicas.add(musicaService.findById(musicaId));
        escala.setMusicas(musicas);
        return EscalaMapper.entityToDomain(escalaRepository.save(EscalaMapper.domainToEntity(escala)));
    }

    public Escala removeMusicaInEscala(UUID escalaId, UUID musicaId){
        Escala escala = findById(escalaId);
        List<Musica> musicas = escala.getMusicas();
        musicas.remove(musicaService.findById(musicaId));
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
            escala.setBack(levitaService.findAllById(input.getBacks()));
        if (input.getObservacoes() != null)
            escala.setObservacoes(input.getObservacoes());
        switch (input.getData().getDayOfWeek()) {
            case SUNDAY:
                escala.setDomingo(true);
                break;
            case WEDNESDAY:
                escala.setQuarta(true);
                break;
            default:
                escala.setEspecial(true);
                break;
        }
        return escala;
    }

    private Levita findLevita(UUID levitaId, String instrumentista){
        return levitaService.findById(levitaId);
    }

}
