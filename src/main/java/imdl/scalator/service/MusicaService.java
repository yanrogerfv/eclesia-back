package imdl.scalator.service;

import imdl.scalator.domain.Musica;
import imdl.scalator.domain.exception.EntityNotFoundException;
import imdl.scalator.domain.exception.RogueException;
import imdl.scalator.domain.input.MusicaInput;
import imdl.scalator.persistence.MusicaRepository;
import imdl.scalator.service.mapper.MusicaMapper;

import java.util.List;
import java.util.UUID;

public class MusicaService {

    private final MusicaRepository musicaRepository;

    public MusicaService(MusicaRepository musicaRepository) {
        this.musicaRepository = musicaRepository;
    }

    public List<Musica> listAll(){
        return musicaRepository.findAll().stream().map(MusicaMapper::entityToDomain).toList();
    }

    public Musica addMusica(MusicaInput input){
        validate(input);
        Musica musica = inputToDomain(input);
        musicaRepository.save(MusicaMapper.domainToEntity(musica));
        return musica;
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
        musicaRepository.save(MusicaMapper.domainToEntity(musica));
        return musica;
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
}
