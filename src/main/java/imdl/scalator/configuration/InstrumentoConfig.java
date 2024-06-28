package imdl.scalator.configuration;

import imdl.scalator.persistence.InstrumentoRepository;
import imdl.scalator.service.InstrumentoService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class InstrumentoConfig {

    @Bean
    public InstrumentoService instrumentoService(InstrumentoRepository instrumentoRepository){
        return new InstrumentoService(instrumentoRepository);
    }
}
