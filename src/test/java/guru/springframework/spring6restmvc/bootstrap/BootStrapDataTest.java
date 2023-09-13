package guru.springframework.spring6restmvc.bootstrap;

import guru.springframework.spring6restmvc.repositories.BeerRepository;
import guru.springframework.spring6restmvc.repositories.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class BootStrapDataTest {

    @Autowired
    CustomerRepository customerRepo;

    @Autowired
    BeerRepository beerRepo;

    BootStrapData bootStrapData;

    @BeforeEach
    void setUp() {
        bootStrapData = new BootStrapData(beerRepo,customerRepo);
    }

    @Test
    void testLoadData() {
        bootStrapData.run("");
        assertThat(beerRepo.count()).isEqualTo(3);
        assertThat(customerRepo.count()).isEqualTo(3);
    }
}