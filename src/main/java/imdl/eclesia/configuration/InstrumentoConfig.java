package imdl.eclesia.configuration;

import imdl.eclesia.persistence.InstrumentoRepository;
import imdl.eclesia.service.InstrumentoService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class InstrumentoConfig {

    @Bean
    public InstrumentoService instrumentoService(InstrumentoRepository instrumentoRepository){
        return new InstrumentoService(instrumentoRepository);
    }
}
