package guru.springframework.spring6restmvc.controller;

import guru.springframework.spring6restmvc.entities.Customer;
import guru.springframework.spring6restmvc.mappers.CustomerMapper;
import guru.springframework.spring6restmvc.model.CustomerDTO;
import guru.springframework.spring6restmvc.repositories.CustomerRepository;
import lombok.val;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class CustomerControllerIT {

    @Autowired
    CustomerController customerController;

    @Autowired
    CustomerRepository customerRepo;
    @Autowired
    private CustomerMapper customerMapper;

    @Test
    void patchCustomerById() {
        List<CustomerDTO> customerDTOList = customerRepo.findAll()
                .stream()
                .map(customerMapper::customerToCustomerDto)
                .toList();

        CustomerDTO customerToPatch = CustomerDTO.builder().name("Customer to Patch").build();

        // Patch the first customer
        ResponseEntity<Void> respEntity = customerController.patchCustomerById(customerDTOList.get(0).getId(), customerToPatch);

        assertThat(respEntity.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
        Customer updatedCustomer = customerRepo.findAll().get(0);
        assertThat(updatedCustomer.getName()).isEqualTo("Customer to Patch");
    }

    @Test
    void deleteCustomerNotFound() {
        UUID customerID = UUID.randomUUID();

        // AssertJ assertion
        assertThatThrownBy(() -> customerController.deleteCustomerById(customerID))
                .isInstanceOf(NotFoundException.class)
                .hasMessage("Customer Not Found!!");

        // Junit5 assertion
        assertThrows(NotFoundException.class,() -> customerController.deleteCustomerById(customerID));
    }

    @Rollback
    @Transactional
    @Test
    void deleteCustomerExistingCustomerById() {
        List<CustomerDTO> customerDTOList = customerRepo.findAll()
                .stream()
                .map(customerMapper::customerToCustomerDto)
                .toList();

        // Delete first customer in the list
        ResponseEntity<Void> respEntity = customerController.deleteCustomerById(customerDTOList.get(0).getId());

        assertThat(respEntity.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
        assertThat(customerRepo.count()).isEqualTo(2);
    }

    @Test
    void updateCustomerNotFound() {
        UUID customerID = UUID.randomUUID();
        CustomerDTO updatedCustomerDTO = CustomerDTO.builder().name("Updated Customer").build();

        // AssertJ Assertion
        assertThatThrownBy(() -> customerController.updateCustomerByID(customerID, updatedCustomerDTO))
                .isInstanceOf(NotFoundException.class)
                .hasMessage("Customer Not Found");

        // Junit 5 Assertion
        assertThrows(NotFoundException.class,
                () -> customerController.updateCustomerByID(customerID, updatedCustomerDTO));

    }

    @Test
    void updateExistingCustomerByID() {
        UUID customerID = customerRepo.findAll().get(0).getId();

        ResponseEntity<Void> respEntity = customerController.updateCustomerByID(customerID,
                CustomerDTO.builder().name("Updated Customer").build());

        assertThat(respEntity.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);

        Customer updatedCustomer = customerRepo.findById(customerID).get();
        assertThat(updatedCustomer.getName()).isEqualTo("Updated Customer");

    }

    @Rollback
    @Transactional
    @Test
    void saveNewCustomer() {
        CustomerDTO newCustomerDTO = CustomerDTO.builder().name("New Customer").build();
        ResponseEntity<HttpHeaders> respEntity = customerController.saveNewCustomer(newCustomerDTO);

        String[] pathFragments= respEntity.getHeaders().getLocation().getPath().split("/");

        assertThat(pathFragments[4]).isNotNull();

        assertThat(respEntity.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(customerRepo.count()).isEqualTo(4);
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