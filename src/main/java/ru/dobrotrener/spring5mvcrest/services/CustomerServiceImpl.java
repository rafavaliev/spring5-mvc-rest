package ru.dobrotrener.spring5mvcrest.services;

import org.springframework.stereotype.Service;
import ru.dobrotrener.spring5mvcrest.api.v1.mapper.CustomerMapper;
import ru.dobrotrener.spring5mvcrest.api.v1.model.CustomerDTO;
import ru.dobrotrener.spring5mvcrest.domain.Customer;
import ru.dobrotrener.spring5mvcrest.repositories.CustomerRepository;

import java.util.List;
import java.util.Optional;
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
        Customer customer = customerRepository.findByFirstName(name);
        if (customer == null) {
            customer = new Customer();
        }
        return customerMapper.customerToCustomerDto(customer);
    }

    @Override
    public CustomerDTO getCustomerByLastName(String name) {
        Customer customer = customerRepository.findByLastName(name);
        if (customer == null) {
            customer = new Customer();
        }
        return customerMapper.customerToCustomerDto(customer);
    }

    @Override
    public CustomerDTO createCustomer(CustomerDTO customerDTO) {
        return saveAndReturnDTO(customerMapper.customerDtoToCustomer(customerDTO));
    }

    @Override
    public CustomerDTO saveCustomerByDTO(Long id, CustomerDTO customerDTO) {
        Customer customer = customerMapper.customerDtoToCustomer(customerDTO);
        customer.setId(id);

        return saveAndReturnDTO(customer);
    }

    private CustomerDTO saveAndReturnDTO(Customer customer) {
        Customer savedCustomer = customerRepository.save(customer);

        CustomerDTO returnDto = customerMapper.customerToCustomerDto(savedCustomer);
        returnDto.setCustomerUrl("/api/v1/customers/" + savedCustomer.getId().toString());
        return returnDto;
    }

    @Override
    public CustomerDTO getCustomerById(Long id) {
        Optional<Customer> customerOptional = customerRepository.findById(id);
        Customer customer = customerOptional.orElseGet(Customer::new);
        return customerMapper.customerToCustomerDto(customer);
    }

    @Override
    public CustomerDTO patchCustomer(Long id, CustomerDTO customerDTO) {
        return customerRepository.findById(id).map(customer -> {
           if (customerDTO.getFirstName() != null) {
               customer.setFirstName(customerDTO.getFirstName());
           }
           if (customerDTO.getLastName() != null) {
               customer.setLastName(customerDTO.getLastName());
           }
           return customerMapper.customerToCustomerDto(customerRepository.save(customer));
        }).orElseThrow(RuntimeException::new);
    }

    @Override
    public void deleteCustomerById(Long id) {
        customerRepository.deleteById(id);
    }
}
