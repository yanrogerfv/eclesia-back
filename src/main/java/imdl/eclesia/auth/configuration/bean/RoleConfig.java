package imdl.eclesia.auth.configuration.bean;

import imdl.eclesia.auth.repository.RoleRepository;
import imdl.eclesia.auth.service.RoleService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RoleConfig {

    @Bean
    public RoleService roleService(RoleRepository roleRepository){
        return new RoleService(roleRepository);
    }
}
