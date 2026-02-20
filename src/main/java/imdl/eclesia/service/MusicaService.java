package imdl.eclesia.service;

import imdl.eclesia.domain.Musica;
import imdl.eclesia.domain.event.MusicaEvent;
import imdl.eclesia.domain.exception.EntityNotFoundException;
import imdl.eclesia.domain.exception.RogueException;
import imdl.eclesia.domain.input.MusicaInput;
import imdl.eclesia.persistence.MusicaRepository;
import imdl.eclesia.service.mapper.MusicaMapper;
import imdl.eclesia.service.utils.events.LogAction;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Comparator;
import java.util.List;
import java.util.UUID;

public class MusicaService {

    private final MusicaRepository musicaRepository;
    private final ApplicationEventPublisher eventPublisher;

    public MusicaService(MusicaRepository musicaRepository, ApplicationEventPublisher eventPublisher) {
        this.musicaRepository = musicaRepository;
        this.eventPublisher = eventPublisher;
    }

    public List<Musica> listAll(){
        return musicaRepository.findAll().stream().map(MusicaMapper::entityToDomain).sorted(Comparator.comparing(Musica::getNome)).toList();
    }

    public Musica findById(UUID id){
        return musicaRepository.findById(id).map(MusicaMapper::entityToDomain)
                .orElseThrow(() -> new EntityNotFoundException("Música não encontrada."));
    }

    public Musica addMusica(MusicaInput input){
        validate(input);
        Musica musica = inputToDomain(input);
        Musica created = MusicaMapper.entityToDomain(musicaRepository.save(MusicaMapper.domainToEntity(musica)));
        publishMusicaEvent(LogAction.CRIAR_MUSICA, created);
        return created;
    }

    public Musica updateMusica(UUID id, MusicaInput input){
        Musica musica = MusicaMapper.entityToDomain(musicaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Música não encontrada.")));
        if(input.getNome() != null)
            musica.setNome(input.getNome());
        if(input.getLink() != null)
            musica.setLink(input.getLink());
        if(input.getCifra() != null)
            musica.setCifra(input.getCifra());
        Musica updated = MusicaMapper.entityToDomain(musicaRepository.save(MusicaMapper.domainToEntity(musica)));
        publishMusicaEvent(LogAction.ATUALIZAR_MUSICA, updated);
        return updated;
    }

    public void deleteMusica(UUID id){
        Musica musica = MusicaMapper.entityToDomain(musicaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Música não encontrada.")));
        publishMusicaEvent(LogAction.EXCLUIR_MUSICA, musica);
        musicaRepository.delete(musicaRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Música não encontrada.")));
    }

    private void validate(MusicaInput input){
        if(input.getNome() == null || input.getNome().isBlank())
            throw new RogueException("A música deve ter um nome.");
        if(input.getLink() == null || input.getLink().isBlank())
            throw new RogueException("A música deve ter um nome.");
//        if(input.getCifra() == null || input.getCifra().isBlank())
//            throw new RogueException("A música deve ter um nome.");
    }

    private Musica inputToDomain(MusicaInput input){
        Musica musica = new Musica();
        musica.setNome(input.getNome());
        musica.setLink(input.getLink());
        musica.setCifra(input.getCifra());
        return musica;
    }

    private void publishMusicaEvent(LogAction action, Musica musica) {
        String user = SecurityContextHolder.getContext().getAuthentication().getName();
        eventPublisher.publishEvent(new MusicaEvent(musica, action, user));
    }
}
