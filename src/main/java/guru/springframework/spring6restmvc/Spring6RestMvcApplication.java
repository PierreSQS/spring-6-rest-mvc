package guru.springframework.spring6restmvc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.web.config.EnableSpringDataWebSupport;

import static org.springframework.data.web.config.EnableSpringDataWebSupport.PageSerializationMode.VIA_DTO;

@SpringBootApplication
// Fixes the PageImpl Serialization issue
@EnableSpringDataWebSupport(pageSerializationMode = VIA_DTO)
public class Spring6RestMvcApplication {

    public static void main(String[] args) {
        SpringApplication.run(Spring6RestMvcApplication.class, args);
    }

}
