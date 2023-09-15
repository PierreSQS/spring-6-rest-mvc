package guru.springframework.spring6restmvc.controller;

import guru.springframework.spring6restmvc.repositories.CustomerRepository;
import lombok.val;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CustomerControllerIT {

    @Autowired
    CustomerController customerController;

    @Autowired
    CustomerRepository customerRepo;

    @Test
    void patchCustomerById() {
        fail("Not Implemented yet!!");
    }

    @Test
    void deleteCustomerById() {
        fail("Not Implemented yet!!");
    }

    @Test
    void updateCustomerByID() {
        fail("Not Implemented yet!!");
    }

    @Test
    void saveNewCustomer() {
        fail("Not Implemented yet!!");
    }

    @Rollback
    @Transactional
    @Test
    void testEmptyCustomerList() {
        customerRepo.deleteAll();
        val customerDTOS = customerController.listAllCustomers();
        assertThat(customerDTOS).isEmpty();
    }
    @Test
    void testListAllCustomers() {
        val customerDTOS = customerController.listAllCustomers();
        assertThat(customerDTOS).hasSize(3);
    }

    @Test
    void testCustomerByIdNotFound() {
        UUID uuid = UUID.randomUUID();

        assertThatThrownBy(() -> customerController.getCustomerById(uuid))
                .isInstanceOf(NotFoundException.class);
    }
    @Test
    void testGetCustomerById() {
        val customerById = customerController.getCustomerById(customerRepo.findAll().get(0).getId());
        assertThat(customerById.getId()).isNotNull();
    }
}