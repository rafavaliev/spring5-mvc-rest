package ru.dobrotrener.spring5mvcrest.api.v1.mapper;

import org.junit.Before;
import org.junit.Test;
import ru.dobrotrener.spring5mvcrest.api.v1.model.CustomerDTO;
import ru.dobrotrener.spring5mvcrest.domain.Customer;

import static org.junit.Assert.*;

public class CustomerMapperTest {

    private static final Long ID_1 = 1L;
    private static final String FIRST_NAME = "FirstName";
    private static final String LAST_NAME = "LastName";

    private CustomerMapper customerMapper;

    @Before
    public void setUp() throws Exception {
        this.customerMapper = CustomerMapper.INSTANCE;
    }

    @Test
    public void customerToCustomerDto() {
        //given
        Customer customer = new Customer();
        customer.setId(ID_1);
        customer.setFirstname(FIRST_NAME);
        customer.setLastname(LAST_NAME);

        //when
        CustomerDTO dto = customerMapper.customerToCustomerDto(customer);

        //then
        assertNotNull(dto);
        assertEquals(customer.getId(), dto.getId());
        assertEquals(customer.getFirstname(), dto.getFirstname());
        assertEquals(customer.getLastname(), dto.getLastname());
    }

    @Test
    public void customerDtoToCustomer() {
        //given
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setId(ID_1);
        customerDTO.setFirstname(FIRST_NAME);
        customerDTO.setLastname(LAST_NAME);

        //when
        Customer customer = customerMapper.customerDtoToCustomer(customerDTO);

        //then
        assertNotNull(customer);
        assertEquals(customerDTO.getId(), customer.getId());
        assertEquals(customerDTO.getFirstname(), customer.getFirstname());
        assertEquals(customerDTO.getLastname(), customer.getLastname());
    }
}