package guru.springframework.spring6restmvc.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import guru.springframework.spring6restmvc.model.Customer;
import guru.springframework.spring6restmvc.services.CustomerService;
import guru.springframework.spring6restmvc.services.CustomerServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static guru.springframework.spring6restmvc.controller.CustomerController.CUSTOMER_PATH;
import static guru.springframework.spring6restmvc.controller.CustomerController.CUSTOMER_PATH_ID;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
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

    @Captor
    ArgumentCaptor<UUID> uuidArgumentCaptor;

    @Captor ArgumentCaptor<Customer> customerArgumentCaptor;

    CustomerServiceImpl customerServiceImpl;

    @BeforeEach
    void setUp() {
        customerServiceImpl = new CustomerServiceImpl();
    }

    @Test
    void testPatchCustomer() throws Exception {
        // Given
        Customer custToPatch = customerServiceImpl.getAllCustomers().getLast();
        Map<String , String> custMap = new HashMap<>();
        custMap.put("name","Cust To Patch");

        // When, Then
        mockMvc.perform(patch(CUSTOMER_PATH_ID,custToPatch.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(custMap)))
                .andExpect(status().isNoContent())
                .andDo(print());

        verify(customerServMock, times(1))
                .patchCustomerById(uuidArgumentCaptor.capture(), customerArgumentCaptor.capture());

        assertThat(custToPatch.getId()).isEqualTo(uuidArgumentCaptor.getValue());

        assertThat(custMap).containsEntry("name", custMap.get("name"));
    }

    @Test
    void deleteCustomer() throws Exception{
        // Given
        Customer customerToDelete = customerServiceImpl.getAllCustomers().getFirst();

        // When, Then
        mockMvc.perform(delete(CUSTOMER_PATH_ID, customerToDelete.getId()))
                .andExpect(status().isNoContent())
                .andDo(print());


        verify(customerServMock, times(1))
                .deleteCustomerById(uuidArgumentCaptor.capture());

        assertThat(customerToDelete.getId()).isEqualTo(uuidArgumentCaptor.getValue());

    }
    
    @Test
    void updateCustomer() throws Exception {
        // Given
        Customer updatedCustomer = customerServiceImpl.getAllCustomers().getFirst();

        // When, Then
        mockMvc.perform(put(CUSTOMER_PATH_ID, updatedCustomer.getId())
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
        mockMvc.perform(post(CUSTOMER_PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(custToSave)))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", "/api/v1/customer/" + firstCustomer.getId()))
                .andDo(print());
    }

    @Test
    void listAllCustomers() throws Exception {
        given(customerServMock.getAllCustomers()).willReturn(customerServiceImpl.getAllCustomers());

        mockMvc.perform(get(CUSTOMER_PATH)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()", is(3)));
    }

    @Test
    void getCustomerById() throws Exception {
        Customer customer = customerServiceImpl.getAllCustomers().getFirst();

        given(customerServMock.getCustomerById(customer.getId())).willReturn(customer);

        mockMvc.perform(get(CUSTOMER_PATH_ID, customer.getId())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name", is(customer.getName())));

    }
}










