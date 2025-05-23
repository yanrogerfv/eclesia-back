package imdl.eclesia.configuration;

import imdl.eclesia.persistence.InstrumentoRepository;
import imdl.eclesia.service.InstrumentoService;
import imdl.eclesia.service.LevitaService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class InstrumentoConfig {

    @Bean
    public InstrumentoService instrumentoService(InstrumentoRepository instrumentoRepository, LevitaService levitaService){
        return new InstrumentoService(instrumentoRepository, levitaService);
    }
}
