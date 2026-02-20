package imdl.eclesia.configuration;

import imdl.eclesia.persistence.EscalaRepository;
import imdl.eclesia.service.EscalaService;
import imdl.eclesia.service.LevitaService;
import imdl.eclesia.service.MusicaService;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EscalaConfig {
    @Bean
    public EscalaService escalaService(EscalaRepository escalaRepository, LevitaService levitaService, MusicaService musicaService, ApplicationEventPublisher eventPublisher){
        return new EscalaService(escalaRepository, levitaService, musicaService, eventPublisher);
    }
}
