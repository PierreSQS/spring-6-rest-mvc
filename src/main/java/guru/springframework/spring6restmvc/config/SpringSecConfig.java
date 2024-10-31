package guru.springframework.spring6restmvc.config;

import org.springframework.boot.actuate.autoconfigure.security.servlet.EndpointRequest;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.web.SecurityFilterChain;

/**
 *  * Modified by Pierrot on 2024-10-31.
 */
@Profile("!test")
@Configuration(proxyBeanMethods = false) // ACCORDING TO SPRING BOOT DOCUMENTATION
public class SpringSecConfig {

    // ACCORDING TO SPRING BOOT DOCUMENTATION
    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE)
    SecurityFilterChain h2ConsoleSecurityFilterChain(HttpSecurity http) throws Exception {
        // ALLOW ALL REQUESTS TO THE H2 CONSOLE
        http.securityMatcher(PathRequest.toH2Console());

        // DISABLE CSRF FOR H2 CONSOLE
        http.csrf(csrfConfigurer -> csrfConfigurer.ignoringRequestMatchers("/h2-console/**"));

        // ALLOW FRAMES FROM SAME ORIGIN
        http.headers(headers -> headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin));
        return http.build();
    }


    @Bean
    @Order(1)
    public SecurityFilterChain actuatorSecurityFilterChain(HttpSecurity http) throws Exception {
        http.securityMatcher(EndpointRequest.toAnyEndpoint())
                .authorizeHttpRequests(authorize -> authorize.anyRequest().permitAll());

        return http.build();
    }

    @Bean
    @Order(2)
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/v3/api-docs**",
                                                  "/swagger-ui/**",
                                                  "/swagger-ui.html").permitAll()
                        .anyRequest().authenticated())
                .oauth2ResourceServer(httpSecurityOAuth2ResourceServerConfigurer ->
                       httpSecurityOAuth2ResourceServerConfigurer.jwt(Customizer.withDefaults()));



        return http.build();
    }

}
