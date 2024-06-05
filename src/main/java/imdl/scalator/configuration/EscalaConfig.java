package imdl.scalator.configuration;

import imdl.scalator.persistence.EscalaRepository;
import imdl.scalator.persistence.LevitaRepository;
import imdl.scalator.persistence.MusicaRepository;
import imdl.scalator.service.EscalaService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EscalaConfig {
    @Bean
    public EscalaService escalaService(EscalaRepository escalaRepository, LevitaRepository levitaRepository, MusicaRepository musicaRepository){
        return new EscalaService(escalaRepository, levitaRepository, musicaRepository);
    }
}
