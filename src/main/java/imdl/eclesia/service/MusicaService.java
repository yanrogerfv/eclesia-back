package imdl.eclesia.service;

import imdl.eclesia.domain.Musica;
import imdl.eclesia.domain.exception.EntityNotFoundException;
import imdl.eclesia.domain.exception.RogueException;
import imdl.eclesia.domain.input.MusicaInput;
import imdl.eclesia.persistence.MusicaRepository;
import imdl.eclesia.service.mapper.MusicaMapper;

import java.util.Comparator;
import java.util.List;
import java.util.UUID;

public class MusicaService {

    private final MusicaRepository musicaRepository;

    public MusicaService(MusicaRepository musicaRepository) {
        this.musicaRepository = musicaRepository;
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
        return MusicaMapper.entityToDomain(musicaRepository.save(MusicaMapper.domainToEntity(musica)));
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
        return MusicaMapper.entityToDomain(musicaRepository.save(MusicaMapper.domainToEntity(musica)));
    }

    public void deleteMusica(UUID id){
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
}
