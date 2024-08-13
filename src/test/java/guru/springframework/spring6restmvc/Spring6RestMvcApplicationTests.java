package guru.springframework.spring6restmvc;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
class Spring6RestMvcApplicationTests {

    @Test
    void contextLoads(ApplicationContext appCtx) {
        assertThat(appCtx).isNotNull();
    }

}
