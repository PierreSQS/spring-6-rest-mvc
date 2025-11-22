package guru.springframework.spring7restmvc.services;

import guru.springframework.spring7restmvc.model.Customer;

import java.util.List;
import java.util.UUID;

/**
 * Created by jt, Spring Framework Guru.
 */
public interface CustomerService {

    Customer getCustomerById(UUID uuid);

    List<Customer> getAllCustomers();

}
