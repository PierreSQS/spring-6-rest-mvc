package guru.springframework.spring6restmvc.repositories;

import guru.springframework.spring6restmvc.entities.Customer;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@DataJpaTest
class CustomerRepositoryTest {
    @Autowired
    CustomerRepository customerRepo;

    @Test
    void testSaveCustomer() {
        Customer savedCustomer = customerRepo.save(Customer.builder().name("New Customer").build());

        assertThat(savedCustomer).isNotNull();

        UUID id = savedCustomer.getId();
        log.info("the generated UUID {}",id);
        assertThat(id).isNotNull();
    }


}