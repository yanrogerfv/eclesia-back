package imdl.eclesia.configuration;

import imdl.eclesia.persistence.InstrumentoRepository;
import imdl.eclesia.service.InstrumentoService;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class InstrumentoConfig {

    @Bean
    public InstrumentoService instrumentoService(InstrumentoRepository instrumentoRepository, ApplicationEventPublisher eventPublisher){
        return new InstrumentoService(instrumentoRepository, eventPublisher);
    }
}
