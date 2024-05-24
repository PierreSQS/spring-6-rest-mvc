package guru.springframework.spring6restmvc.bootstrap;

import guru.springframework.spring6restmvc.repositories.BeerRepository;
import guru.springframework.spring6restmvc.repositories.CustomerRepository;
import guru.springframework.spring6restmvc.services.BeerCsvService;
import guru.springframework.spring6restmvc.services.BeerCsvServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.core.io.ResourceLoader;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class BootstrapDataTest {

    @Autowired
    BeerRepository beerRepository;

    @Autowired
    CustomerRepository customerRepository;

    BeerCsvService beerCsvSrv;

    @Autowired
    ResourceLoader resourceLoader;

    BootstrapData bootstrapData;

    @BeforeEach
    void setUp() {
        beerCsvSrv = new BeerCsvServiceImpl();
        resourceLoader.getResource("classpath:csv/beers.csv");
        bootstrapData = new BootstrapData(beerRepository, customerRepository, beerCsvSrv, resourceLoader);
    }

    @Test
    void TestRun() throws IOException {
        bootstrapData.run();

        assertThat(beerRepository.count()).isEqualTo(2413);
        assertThat(customerRepository.count()).isEqualTo(3);
    }
}





