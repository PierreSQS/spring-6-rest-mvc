package guru.springframework.spring6restmvc.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Created by Pierrot on 10.02.2023.
 */
@Configuration
public class SpringSecConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .csrf().ignoringRequestMatchers("/api/**")
                .and()
                .authorizeHttpRequests().anyRequest().authenticated()
                .and()
                .httpBasic(Customizer.withDefaults());
        return httpSecurity.build();
    }
}
