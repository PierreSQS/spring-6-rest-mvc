package guru.springframework.spring6restmvc.services;

import guru.springframework.spring6restmvc.controller.NotFoundException;
import guru.springframework.spring6restmvc.entities.Customer;
import guru.springframework.spring6restmvc.mappers.CustomerMapper;
import guru.springframework.spring6restmvc.model.CustomerDTO;
import guru.springframework.spring6restmvc.repositories.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Primary
@RequiredArgsConstructor
public class CustomerServiceJPA implements CustomerService {

    private final CustomerRepository customerRepo;

    private final CustomerMapper customerMapper;

    @Override
    public Optional<CustomerDTO> getCustomerById(UUID uuid) {
        return customerRepo.findById(uuid).map(customerMapper::customerToCustomerDto);
    }

    @Override
    public List<CustomerDTO> getAllCustomers() {
        return customerRepo.findAll().stream()
                .map(customerMapper::customerToCustomerDto)
                .toList();
    }

    @Override
    public CustomerDTO saveNewCustomer(CustomerDTO customerDTO) {
        Customer customerToSave = customerMapper.customerDtoToCustomer(customerDTO);
        return customerMapper.customerToCustomerDto(customerRepo.save(customerToSave));
    }

    @Override
    public void updateCustomerById(UUID customerId, CustomerDTO customerDTO) {
        Optional<Customer> optFoundCustomerById = customerRepo.findById(customerId);

        if (optFoundCustomerById.isPresent()) {
            Customer customerToUpdate = optFoundCustomerById.get();
            customerToUpdate.setName(customerDTO.getName());
            customerToUpdate.setCreatedDate(customerDTO.getCreatedDate());
            customerToUpdate.setUpdateDate(customerDTO.getUpdateDate());
            customerRepo.save(customerToUpdate);

        } else {
            throw new NotFoundException("Customer Not Found");
        }
    }

    @Override
    public void deleteCustomerById(UUID customerId) {
        Customer customerToDelete = customerRepo.findById(customerId)
                .orElseThrow(() -> new NotFoundException("Customer Not Found!!"));
        customerRepo.deleteById(customerToDelete.getId());
    }

    @Override
    public void patchCustomerById(UUID customerId, CustomerDTO customerDTO) {

        Optional<Customer> optFoundCustByID = customerRepo.findById(customerId);
        if (optFoundCustByID.isPresent()) {
            if (StringUtils.hasText(optFoundCustByID.get().getName())) {
                optFoundCustByID.get().setName(customerDTO.getName());

                customerRepo.save(optFoundCustByID.get());
            }
        } else {
            throw new NotFoundException("Customer Not Found");
        }

    }
}
