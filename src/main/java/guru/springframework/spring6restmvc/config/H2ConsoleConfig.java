package guru.springframework.spring6restmvc.config;

import org.springframework.boot.autoconfigure.h2.H2ConsoleProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class H2ConsoleConfig {
    @Bean
    public H2ConsoleProperties h2ConsoleProperties() {
        return new H2ConsoleProperties();
    }
}
