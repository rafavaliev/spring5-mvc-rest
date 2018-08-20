package ru.dobrotrener.spring5mvcrest.services;

import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ru.dobrotrener.spring5mvcrest.api.v1.model.CustomerDTO;
import ru.dobrotrener.spring5mvcrest.domain.Customer;
import ru.dobrotrener.spring5mvcrest.repositories.CustomerRepository;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@Slf4j
public class CustomerServiceTest {

    public static final String NAME = "FirstName";
    @Mock
    private CustomerRepository customerRepository;

    private CustomerService customerService;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        this.customerService = new CustomerServiceImpl(customerRepository);
    }

    @Test
    public void getAllCustomers() {
        //given
        Customer customer1 = new Customer();
        Customer customer2 = new Customer();
        Customer customer3 = new Customer();

        when(customerRepository.findAll())
                .thenReturn(Arrays.asList(customer1, customer2, customer3));

        //when
        List<CustomerDTO> customerDTOList = customerService.getAllCustomers();

        //then
        log.info(customerDTOList.toString());
        assertNotNull(customerDTOList);
        assertEquals(3, customerDTOList.size());
    }

    @Test
    public void getCustomerByFirstName() {
        //given
        Customer customer = new Customer();
        customer.setFirstname(NAME);

        when(customerRepository.findByFirstname(anyString())).thenReturn(customer);
        //when
        CustomerDTO customerDTO = customerService.getCustomerByFirstName(NAME);

        //then
        log.info(customerDTO.toString());
        assertNotNull(customerDTO);
        assertEquals(customer.getFirstname(), customerDTO.getFirstname());
    }

    @Test
    public void getCustomerByLastName() {
        //given
        Customer customer = new Customer();
        customer.setFirstname(NAME);

        when(customerRepository.findByLastname(anyString())).thenReturn(customer);
        //when
        CustomerDTO customerDTO = customerService.getCustomerByLastName(NAME);

        //then
        log.info(customerDTO.toString());
        assertNotNull(customerDTO);
        assertEquals(customer.getLastname(), customerDTO.getLastname());
    }
}