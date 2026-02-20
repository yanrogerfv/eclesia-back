package imdl.eclesia.configuration;

import imdl.eclesia.persistence.MusicaRepository;
import imdl.eclesia.service.MusicaService;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MusicaConfig {
    @Bean
    public MusicaService musicaService(MusicaRepository musicaRepository, ApplicationEventPublisher eventPublisher){
        return new MusicaService(musicaRepository, eventPublisher);
    }
}
