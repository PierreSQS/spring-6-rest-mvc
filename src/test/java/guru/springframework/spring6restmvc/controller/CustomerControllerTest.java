package guru.springframework.spring6restmvc.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import guru.springframework.spring6restmvc.model.Customer;
import guru.springframework.spring6restmvc.services.CustomerService;
import guru.springframework.spring6restmvc.services.CustomerServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CustomerController.class)
class CustomerControllerTest {

    @MockitoBean
    CustomerService customerServMock;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    CustomerServiceImpl customerServiceImpl;

    @BeforeEach
    void setUp() {
        customerServiceImpl = new CustomerServiceImpl();
    }

    @Test
    void deleteCustomer() throws Exception{
        // Given
        Customer customerToDelete = customerServiceImpl.getAllCustomers().getFirst();

        // When, Then
        mockMvc.perform(delete("/api/v1/customer/{customerID}", customerToDelete.getId()))
                .andExpect(status().isNoContent())
                .andDo(print());

        ArgumentCaptor<UUID> argumentCaptor = ArgumentCaptor.forClass(UUID.class);

        verify(customerServMock, times(1))
                .deleteCustomerById(argumentCaptor.capture());

        assertThat(argumentCaptor.getValue()).isEqualTo(customerToDelete.getId());

    }
    
    @Test
    void updateCustomer() throws Exception {
        // Given
        Customer updatedCustomer = customerServiceImpl.getAllCustomers().getFirst();

        // When, Then
        mockMvc.perform(put("/api/v1/customer/{customerID}", updatedCustomer.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedCustomer)))
                .andExpect(status().isNoContent())
                .andDo(print());

        verify(customerServMock, times(1))
                .updateCustomerById(updatedCustomer.getId(), updatedCustomer);
    }

    @Test
    void testCreateCustomer() throws Exception {
        // Given
        Customer firstCustomer = customerServiceImpl.getAllCustomers().getFirst();

        Customer custToSave = Customer.builder()
                .name(firstCustomer.getName())
                .createdDate(firstCustomer.getCreatedDate())
                .updateDate(firstCustomer.getUpdateDate())
                .build();

        given(customerServMock.saveNewCustomer(any(Customer.class)))
                .willReturn(firstCustomer);

        // When, Then
        mockMvc.perform(post("/api/v1/customer")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(custToSave)))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", "/api/v1/customer/" + firstCustomer.getId()))
                .andDo(print());
    }

    @Test
    void listAllCustomers() throws Exception {
        given(customerServMock.getAllCustomers()).willReturn(customerServiceImpl.getAllCustomers());

        mockMvc.perform(get("/api/v1/customer")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()", is(3)));
    }

    @Test
    void getCustomerById() throws Exception {
        Customer customer = customerServiceImpl.getAllCustomers().getFirst();

        given(customerServMock.getCustomerById(customer.getId())).willReturn(customer);

        mockMvc.perform(get("/api/v1/customer/" + customer.getId())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name", is(customer.getName())));

    }
}










