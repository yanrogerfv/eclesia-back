package imdl.eclesia.auth.configuration.bean;

import imdl.eclesia.auth.repository.UserRepository;
import imdl.eclesia.auth.service.RoleService;
import imdl.eclesia.auth.service.UserService;
import imdl.eclesia.service.LevitaService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UserConfig {
    @Bean
    public UserService userService(LevitaService levitaService, UserRepository userRepository, RoleService roleService){
        return new UserService(levitaService, userRepository, roleService);
    }
}
