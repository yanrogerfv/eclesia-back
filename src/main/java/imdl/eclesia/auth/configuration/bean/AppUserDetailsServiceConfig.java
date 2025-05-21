package imdl.eclesia.auth.configuration.bean;

import imdl.eclesia.auth.configuration.AppUserDetailsService;
import imdl.eclesia.auth.configuration.AuthManager;
import imdl.eclesia.auth.configuration.SecurityConfig;
import imdl.eclesia.auth.filter.JwtAuthenticationFilter;
import imdl.eclesia.auth.repository.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppUserDetailsServiceConfig {
    @Bean
    public AppUserDetailsService appUserDetailsService(UserRepository userRepository) {
        return new AppUserDetailsService(userRepository);
    }

    @Bean
    public SecurityConfig securityConfig(AppUserDetailsService appUserDetailsService, JwtAuthenticationFilter jwtAuthenticationFilter) {
        return new SecurityConfig(appUserDetailsService, jwtAuthenticationFilter);
    }

    @Bean
    public AuthManager authManager() {
        return new AuthManager();
    }
}
