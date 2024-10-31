package guru.springframework.spring6restmvc.config;

import org.springframework.boot.autoconfigure.h2.H2ConsoleProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class H2ConsoleConfig {
    @Bean
    @Primary
    public H2ConsoleProperties h2ConsoleProperties() {
        return new H2ConsoleProperties();
    }
}
