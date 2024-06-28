package imdl.scalator.configuration;

import imdl.scalator.persistence.LevitaRepository;
import imdl.scalator.service.InstrumentoService;
import imdl.scalator.service.LevitaService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LevitaConfig {
    @Bean
    public LevitaService levitaService(LevitaRepository levitaRepository, InstrumentoService instrumentoService){
        return new LevitaService(levitaRepository, instrumentoService);
    }
}
