package guru.springframework.spring6restmvc.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Modified by Pierrot on 19-06-2024.
 */
@Configuration
public class SpringSecConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(authorize ->
                        authorize.anyRequest().authenticated())
               .oauth2ResourceServer(httpSecurityOAuth2ResourceServerConfigurer ->
                       httpSecurityOAuth2ResourceServerConfigurer.jwt(Customizer.withDefaults()));

        return http.build();
    }

}
