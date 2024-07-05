package guru.springframework.spring6restmvc.config;

import org.springframework.boot.actuate.autoconfigure.security.servlet.EndpointRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Modified by Pierrot on 02-07-2024.
 */
@Profile("!test")
@Configuration
public class SpringSecConfig {

    @Bean
    @Order(1)
    public SecurityFilterChain actuatorFilterChain(HttpSecurity httpSecurity) throws Exception {

        // alternative to commented out code
        httpSecurity.securityMatcher(EndpointRequest.toAnyEndpoint())
                .authorizeHttpRequests(authorize -> authorize.anyRequest().permitAll());

//        httpSecurity.authorizeHttpRequests(authorize -> authorize
//                .requestMatchers(EndpointRequest.toAnyEndpoint()).permitAll());

        return httpSecurity.build();

    }

    @Bean
    @Order(2)
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(authorize ->
                        authorize.requestMatchers("/v3/api-docs**", "/actuator/**",  "/swagger-ui/**",  "/swagger-ui.html")
                                .permitAll()
          .anyRequest().authenticated())
               .oauth2ResourceServer(httpSecurityOAuth2ResourceServerConfigurer ->
                       httpSecurityOAuth2ResourceServerConfigurer.jwt(Customizer.withDefaults()));

        return http.build();
    }

}
