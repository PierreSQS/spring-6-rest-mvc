package guru.springframework.spring6restmvc.bootstrap;

import guru.springframework.spring6restmvc.entities.Beer;
import guru.springframework.spring6restmvc.entities.Customer;
import guru.springframework.spring6restmvc.repositories.BeerRepository;
import guru.springframework.spring6restmvc.repositories.CustomerRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.util.List;

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
        List<Beer> allBeers = beerRepo.findAll();

        assertThat(allBeers.size()).isNotZero();
    }

    @Test
    void testLoadCustomerData() {
        List<Customer> allCustomers = customerRepo.findAll();

        assertThat(allCustomers.size()).isNotZero();
    }
}