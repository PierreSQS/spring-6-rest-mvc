package guru.springframework.spring6restmvc.bootstrap;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class BootstrapData {

    @Bean
    CommandLineRunner loadData() {
        return args -> {
        };
    }

}
