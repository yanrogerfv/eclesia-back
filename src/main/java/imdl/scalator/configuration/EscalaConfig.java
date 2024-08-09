package imdl.scalator.configuration;

import imdl.scalator.persistence.EscalaRepository;
import imdl.scalator.service.EscalaService;
import imdl.scalator.service.LevitaService;
import imdl.scalator.service.MusicaService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EscalaConfig {
    @Bean
    public EscalaService escalaService(EscalaRepository escalaRepository, LevitaService levitaService, MusicaService musicaService){
        return new EscalaService(escalaRepository, levitaService, musicaService);
    }
}
