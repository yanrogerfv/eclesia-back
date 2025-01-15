package imdl.scalator.auth.configuration.bean;

import imdl.scalator.auth.configuration.AppUserDetailsService;
import imdl.scalator.auth.configuration.AuthManager;
import imdl.scalator.auth.configuration.SecurityConfig;
import imdl.scalator.auth.filter.JwtAuthenticationFilter;
import imdl.scalator.auth.repository.UserRepository;
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
