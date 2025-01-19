package guru.springframework.spring6restmvc.repositories;

import guru.springframework.spring6restmvc.bootstrap.BootstrapData;
import guru.springframework.spring6restmvc.entities.Beer;
import guru.springframework.spring6restmvc.entities.Customer;
import guru.springframework.spring6restmvc.services.BeerCsvServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

@DataJpaTest
@Import({BootstrapData.class, BeerCsvServiceImpl.class})
class BeerOrderRepositoryTest {

    @Autowired
    BeerOrderRepository beerOrderRepo;

    @Autowired
    BeerRepository beerRepo;

    @Autowired
    CustomerRepository customerRepo;

    Customer testCustomer;
    Beer testBeer;

    @BeforeEach
    void setUp() {
        testCustomer = customerRepo.findAll().getLast();
        testBeer = beerRepo.findAll().getLast();
    }

    @Test
    void testBeerOrders() {
        System.out.println(beerRepo.count());
        System.out.println(customerRepo.count());
        System.out.println(testCustomer.getName());
        System.out.println(testBeer.getBeerName());
    }
}