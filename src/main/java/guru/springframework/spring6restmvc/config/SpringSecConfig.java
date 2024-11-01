package guru.springframework.spring6restmvc.config;

import org.springframework.boot.actuate.autoconfigure.security.servlet.EndpointRequest;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Modified by Pierrot, on 2024-10-12.
 */
@Profile("!test")
@Configuration
public class SpringSecConfig {

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
        // ALLOW H2 CONSOLE ACCESS
        http.authorizeHttpRequests(authorize -> authorize.
                requestMatchers(PathRequest.toH2Console()).permitAll());

        // DISABLE CSRF PROTECTION ON H2-CONSOLE PATH
        http.csrf(csrfConfigurer -> csrfConfigurer.ignoringRequestMatchers(PathRequest.toH2Console()));

        // SET THE HEADER X-FRAME-OPTIONS TO SAMEORIGIN ON RESPONSES FROM THE CONSOLE
        // e.g. ALLOW X-FRAMES ON SAME ORIGIN
        http.headers(headers -> headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin));

        http.authorizeHttpRequests(authorize -> authorize.
                        requestMatchers(
                                "/v3/api-docs**",
                                        "/swagger-ui/**",
                                        "/swagger-ui.html")
                                .permitAll()
          .anyRequest().authenticated())
               .oauth2ResourceServer(httpSecurityOAuth2ResourceServerConfigurer ->
                       httpSecurityOAuth2ResourceServerConfigurer.jwt(Customizer.withDefaults()));

        return http.build();
    }

}
