package imdl.eclesia.configuration;

import imdl.eclesia.persistence.EscalaRepository;
import imdl.eclesia.persistence.LevitaRepository;
import imdl.eclesia.service.InstrumentoService;
import imdl.eclesia.service.LevitaService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LevitaConfig {
    @Bean
    public LevitaService levitaService(LevitaRepository levitaRepository, InstrumentoService instrumentoService, EscalaRepository escalaService) {
        return new LevitaService(levitaRepository, instrumentoService, escalaService);
    }
}
