package ru.dobrotrener.spring5mvcrest.services;

import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ru.dobrotrener.spring5mvcrest.api.v1.model.CustomerDTO;
import ru.dobrotrener.spring5mvcrest.controllers.v1.CustomerController;
import ru.dobrotrener.spring5mvcrest.domain.Customer;
import ru.dobrotrener.spring5mvcrest.repositories.CustomerRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
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
    public void getAllCustomersTest() {
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
    public void getCustomerByFirstNameTest() {
        //given
        Customer customer = new Customer();
        customer.setFirstName(NAME);

        when(customerRepository.findByFirstName(anyString())).thenReturn(customer);
        //when
        CustomerDTO customerDTO = customerService.getCustomerByFirstName(NAME);

        //then
        log.info(customerDTO.toString());
        assertNotNull(customerDTO);
        assertEquals(customer.getFirstName(), customerDTO.getFirstName());
    }

    @Test
    public void getCustomerByLastNameTest() {
        //given
        Customer customer = new Customer();
        customer.setFirstName(NAME);

        when(customerRepository.findByLastName(anyString())).thenReturn(customer);
        //when
        CustomerDTO customerDTO = customerService.getCustomerByLastName(NAME);

        //then
        log.info(customerDTO.toString());
        assertNotNull(customerDTO);
        assertEquals(customer.getLastName(), customerDTO.getLastName());
    }

    @Test
    public void createCustomerTest() throws Exception {
        //given
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setFirstName(NAME);
        customerDTO.setLastName(NAME);

        Customer savedCustomer = new Customer();
        savedCustomer.setId(1L);
        savedCustomer.setFirstName(NAME);
        savedCustomer.setLastName(NAME);

        when(customerRepository.save(any(Customer.class))).thenReturn(savedCustomer);

        //when
        CustomerDTO savedDto = customerService.createCustomer(customerDTO);

        //then
        assertNotNull(savedDto);
        assertEquals(customerDTO.getFirstName(), savedDto.getFirstName());
        assertEquals(CustomerController.BASE_URL + "1", savedDto.getCustomerUrl());

    }

    @Test
    public void saveCustomerByDTOTest() throws Exception {
        //given
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setFirstName(NAME);
        customerDTO.setLastName(NAME);
        customerDTO.setId(1L);

        Customer savedCustomer = new Customer();
        savedCustomer.setId(1L);
        savedCustomer.setFirstName(NAME);
        savedCustomer.setLastName(NAME);

        when(customerRepository.save(any(Customer.class))).thenReturn(savedCustomer);

        //when
        CustomerDTO savedDto = customerService.createCustomer(customerDTO);

        //then
        assertNotNull(savedDto);
        assertEquals(customerDTO.getFirstName(), savedDto.getFirstName());
        assertEquals(CustomerController.BASE_URL + "1", savedDto.getCustomerUrl());
        assertEquals(customerDTO.getId(), savedDto.getId());
    }

    @Test
    public void getCustomerByIdTest() throws Exception {
        //given
        Customer customer = new Customer();
        customer.setId(1L);

        when(customerRepository.findById(anyLong())).thenReturn(Optional.of(customer));

        //when
        CustomerDTO customerDTO = customerService.getCustomerById(1L);

        //then
        assertNotNull(customerDTO);
        assertEquals(customer.getId(), customerDTO.getId());
    }

    @Test
    public void deleteCustomberTest() throws Exception {
        //given
        Long id = 1L;

        //when
        customerService.deleteCustomerById(id);

        //then
        verify(customerRepository, times(1)).deleteById(anyLong());

    }
}