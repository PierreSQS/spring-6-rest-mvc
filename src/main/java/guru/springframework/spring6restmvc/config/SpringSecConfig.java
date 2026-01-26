package guru.springframework.spring6restmvc.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Modified by Pierrot on 2026-01-26.
 */
// to exclude the Profile test, which runs the RestAssured Tests
@Profile("!test")
@Configuration
public class SpringSecConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) {
        http.authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/v3/api-docs**","/swagger-ui/**","/swagger-ui.html").permitAll()
                        .anyRequest().authenticated())
               .oauth2ResourceServer(httpSecurityOAuth2ResourceServerConfigurer ->
                       httpSecurityOAuth2ResourceServerConfigurer.jwt(Customizer.withDefaults()));

        return http.build();
    }

}
