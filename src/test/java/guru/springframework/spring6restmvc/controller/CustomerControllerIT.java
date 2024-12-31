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
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
class CustomerControllerIT {

    @Autowired
    CustomerController customerController;

    @Autowired
    CustomerRepository customerRepo;

    @Test
    void testGetCustomerByIDNotFound() {
        UUID uuid = UUID.randomUUID();

        assertThatThrownBy(() -> customerController.getCustomerById(uuid))
                .isInstanceOf(NotFoundException.class);
    }

    @Test
    void testGetCustomerByID() {
        // find an existing customer
        Customer foundCustomer = customerRepo.findAll().getLast();

        // find through the  controller
        CustomerDTO foundCustomerDTO = customerController.getCustomerById(foundCustomer.getId());

        // assert whether both the same
        assertThat(foundCustomer.getId()).isEqualTo(foundCustomerDTO.getId());
    }

    @Test
    void testListCustomers() {
        List<CustomerDTO> customerDTOList = customerController.listAllCustomers();
        assertThat(customerDTOList).hasSize(3);

    }

    @Rollback
    @Transactional
    @Test
    void testListCustomersEmpty() {
        customerRepo.deleteAll();
        List<CustomerDTO> customerDTOList = customerController.listAllCustomers();
        assertThat(customerDTOList).isEmpty();

    }


}