package guru.springframework.spring6restmvc.controller;

import guru.springframework.spring6restmvc.entities.Customer;
import guru.springframework.spring6restmvc.model.CustomerDTO;
import guru.springframework.spring6restmvc.repositories.CustomerRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CustomerControllerIT {

    @Autowired
    CustomerController customerController;

    @Autowired
    CustomerRepository customerRepo;

    @Test
    void testCustomerByID() {
        Customer firstCustInTheList = customerRepo.findAll().get(0);
        CustomerDTO foundCustByID = customerController.getCustomerById(firstCustInTheList.getId());
        assertThat(foundCustByID.getName()).isEqualTo(firstCustInTheList.getName());
    }

    @Test
    void testCustomerNotFound() {
        UUID id = UUID.randomUUID();
        NotFoundException notFoundException = assertThrows(NotFoundException.class,
                () -> customerController.getCustomerById(id));

        assertThat(notFoundException.getMessage()).isEqualTo("Customer not found!!");
    }

    @Test
    void testListCustomers() {
        List<CustomerDTO> customerDTOS = customerController.listAllCustomers();
        assertThat(customerDTOS).hasSize(3);
    }

    @Rollback
    @Transactional
    @Test
    void testEmptyListCustomers() {
        customerRepo.deleteAll();

        List<CustomerDTO> customerDTOS = customerController.listAllCustomers();
        assertThat(customerDTOS).isEmpty();
    }
}