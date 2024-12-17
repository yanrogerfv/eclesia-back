package imdl.scalator.auth.configuration.bean;

import imdl.scalator.auth.repository.RoleRepository;
import imdl.scalator.auth.service.RoleService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RoleConfig {

    @Bean
    public RoleService roleService(RoleRepository roleRepository){
        return new RoleService(roleRepository);
    }
}
