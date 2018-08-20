package ru.dobrotrener.spring5mvcrest.services;

import org.springframework.stereotype.Service;
import ru.dobrotrener.spring5mvcrest.api.v1.mapper.CustomerMapper;
import ru.dobrotrener.spring5mvcrest.api.v1.model.CustomerDTO;
import ru.dobrotrener.spring5mvcrest.domain.Customer;
import ru.dobrotrener.spring5mvcrest.repositories.CustomerRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomerServiceImpl implements CustomerService {

    private CustomerRepository customerRepository;

    private CustomerMapper customerMapper;

    public CustomerServiceImpl(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
        this.customerMapper = CustomerMapper.INSTANCE;
    }

    @Override
    public List<CustomerDTO> getAllCustomers() {
        List<Customer> customerList = customerRepository.findAll();
        List<CustomerDTO> dtoList = customerList
                .stream()
                .map(customer -> customerMapper.customerToCustomerDto(customer))
                .collect(Collectors.toList());
        return dtoList;
    }

    @Override
    public CustomerDTO getCustomerByFirstName(String name) {
        Customer customer = customerRepository.findByFirstname(name);
        if (customer == null) {
            customer = new Customer();
        }
        return customerMapper.customerToCustomerDto(customer);
    }

    @Override
    public CustomerDTO getCustomerByLastName(String name) {
        Customer customer = customerRepository.findByLastname(name);
        if (customer == null) {
            customer = new Customer();
        }
        return customerMapper.customerToCustomerDto(customer);
    }
}
