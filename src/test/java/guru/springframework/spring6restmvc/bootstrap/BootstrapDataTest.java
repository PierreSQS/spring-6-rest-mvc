package guru.springframework.spring6restmvc.bootstrap;

import guru.springframework.spring6restmvc.repositories.BeerRepository;
import guru.springframework.spring6restmvc.repositories.CustomerRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Import(BootstrapData.class)
class BootstrapDataTest {

    @Autowired
    BeerRepository beerRepo;

    @Autowired
    CustomerRepository customerRepo;

    @Test
    void testLoadBeerData() {
        assertThat(beerRepo.count()).isEqualTo(3);
    }

    @Test
    void testLoadCustomerData() {
        assertThat(customerRepo.count()).isEqualTo(3);
    }
}