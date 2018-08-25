package ru.dobrotrener.spring5mvcrest.services;

import ru.dobrotrener.spring5mvcrest.api.v1.model.CustomerDTO;

import java.util.List;

public interface CustomerService {

    List<CustomerDTO> getAllCustomers();
    CustomerDTO getCustomerByFirstName(String name);
    CustomerDTO getCustomerByLastName(String name);
    CustomerDTO createCustomer(CustomerDTO customerDTO);
    CustomerDTO saveCustomerByDTO(Long id, CustomerDTO customerDTO);

    CustomerDTO getCustomerById(Long id);

    CustomerDTO patchCustomer(Long id, CustomerDTO customerDTO);

    void deleteCustomerById(Long id);
}
