package guru.springframework.spring6restmvc.bootstrap;

import guru.springframework.spring6restmvc.entities.Beer;
import guru.springframework.spring6restmvc.entities.Customer;
import guru.springframework.spring6restmvc.repositories.BeerRepository;
import guru.springframework.spring6restmvc.repositories.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RequiredArgsConstructor
@DataJpaTest
class BootstrapDataTest {

    private final BeerRepository beerRepo;

    private final CustomerRepository customerRepo;

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