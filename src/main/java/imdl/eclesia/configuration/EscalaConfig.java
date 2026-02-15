package imdl.eclesia.configuration;

import imdl.eclesia.persistence.EscalaLogRepository;
import imdl.eclesia.persistence.EscalaRepository;
import imdl.eclesia.service.EscalaService;
import imdl.eclesia.service.LevitaService;
import imdl.eclesia.service.MusicaService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EscalaConfig {
    @Bean
    public EscalaService escalaService(EscalaLogRepository logRepository, EscalaRepository escalaRepository, LevitaService levitaService, MusicaService musicaService){
        return new EscalaService(logRepository, escalaRepository, levitaService, musicaService);
    }
}
