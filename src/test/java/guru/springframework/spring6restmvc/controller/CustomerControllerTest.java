package guru.springframework.spring6restmvc.controller;

import guru.springframework.spring6restmvc.model.Customer;
import guru.springframework.spring6restmvc.services.CustomerService;
import guru.springframework.spring6restmvc.services.CustomerServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(CustomerController.class)
class CustomerControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    CustomerService customerMockServ;

    CustomerService customerServImpl = new CustomerServiceImpl();

    @Test
    void listAllCustomers() throws Exception {
        given(customerMockServ.getAllCustomers()).willReturn(customerServImpl.getAllCustomers());

        mockMvc.perform(get("/api/v1/customer"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(3))
                .andDo(print());
    }

    @Test
    void getCustomerById() throws Exception {
        List<Customer> allCustomers = customerServImpl.getAllCustomers();
        Customer customer = allCustomers.get(0);

        System.out.println(allCustomers);

        given(customerMockServ.getCustomerById(any())).willReturn(customer);
        mockMvc.perform(get("/api/v1/customer/{customerId}",customer.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(customer.getName()))
                .andDo(print());
    }
}