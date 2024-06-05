package imdl.scalator.configuration;

import imdl.scalator.persistence.MusicaRepository;
import imdl.scalator.service.MusicaService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MusicaConfig {
    @Bean
    public MusicaService musicaService(MusicaRepository musicaRepository){
        return new MusicaService(musicaRepository);
    }
}
