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
        customer.setFirstName(FIRST_NAME);
        customer.setLastName(LAST_NAME);

        //when
        CustomerDTO dto = customerMapper.customerToCustomerDto(customer);

        //then
        assertNotNull(dto);
        assertEquals(customer.getId(), dto.getId());
        assertEquals(customer.getFirstName(), dto.getFirstName());
        assertEquals(customer.getLastName(), dto.getLastName());
    }

    @Test
    public void customerDtoToCustomer() {
        //given
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setId(ID_1);
        customerDTO.setFirstName(FIRST_NAME);
        customerDTO.setLastName(LAST_NAME);

        //when
        Customer customer = customerMapper.customerDtoToCustomer(customerDTO);

        //then
        assertNotNull(customer);
        assertEquals(customerDTO.getId(), customer.getId());
        assertEquals(customerDTO.getFirstName(), customer.getFirstName());
        assertEquals(customerDTO.getLastName(), customer.getLastName());
    }
}