package ru.dobrotrener.spring5mvcrest.controllers.v1;

import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ru.dobrotrener.spring5mvcrest.api.v1.model.CustomerDTO;
import ru.dobrotrener.spring5mvcrest.domain.Customer;
import ru.dobrotrener.spring5mvcrest.services.CustomerService;

import java.util.Arrays;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.dobrotrener.spring5mvcrest.controllers.v1.AbstractRestControllerTest.asJsonString;

@Slf4j
public class CustomerControllerTest {

    public static final Long ID_1 = 1L;
    public static final Long ID_2 = 2L;
    public static final Long ID_3 = 3L;
    public static final String FIRST_NAME = "FirstName";
    public static final String LAST_NAME = "LastName";
    public static final String API_V1_CUSTOMERS = "/api/v1/customers/";

    private MockMvc mockMvc;

    @Mock
    private CustomerService customerService;

    @InjectMocks
    private CustomerController customerController;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(customerController).build();
    }

    @Test
    public void getAllCustomers() throws Exception {
        //given
        CustomerDTO customer1 = new CustomerDTO();
        customer1.setId(ID_1);
        customer1.setFirstName(FIRST_NAME + ID_1);
        customer1.setLastName(LAST_NAME + ID_1);

        CustomerDTO customer2 = new CustomerDTO();
        customer2.setId(ID_2);
        customer2.setFirstName(FIRST_NAME + ID_1);
        customer2.setLastName(LAST_NAME + ID_1);

        CustomerDTO customer3 = new CustomerDTO();
        customer3.setId(ID_3);
        customer3.setFirstName(FIRST_NAME + ID_1);
        customer3.setLastName(LAST_NAME + ID_1);

        when(customerService.getAllCustomers()).thenReturn(
                Arrays.asList(customer1, customer2, customer3));

        //when
        mockMvc.perform(
                get("/api/v1/customers")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.customers", hasSize(3)))
                .andDo(result -> log.info(result.getResponse().getContentAsString()));

    }

    @Test
    public void getCustomerByFirstName() throws Exception {
        //given
        CustomerDTO customer = new CustomerDTO();
        customer.setId(ID_1);
        customer.setFirstName(FIRST_NAME);
        customer.setLastName(LAST_NAME);

        when(customerService.getCustomerByFirstName(anyString())).thenReturn(customer);

        //when
        mockMvc.perform(
                get(API_V1_CUSTOMERS + FIRST_NAME)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName", equalTo(FIRST_NAME)))
                .andDo(result -> log.info(result.getResponse().getContentAsString()));
    }

    @Test
    public void createCustomerTest() throws Exception {
        //given
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setFirstName(FIRST_NAME);
        customerDTO.setLastName(LAST_NAME);

        CustomerDTO returnDto = new CustomerDTO();
        returnDto.setFirstName(customerDTO.getFirstName());
        returnDto.setLastName(customerDTO.getLastName());
        returnDto.setCustomerUrl(API_V1_CUSTOMERS + "1");

        when(customerService.createCustomer(any(CustomerDTO.class))).thenReturn(returnDto);

        //when
        mockMvc.perform(put(API_V1_CUSTOMERS)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(customerDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.firstName", equalTo(FIRST_NAME)))
                .andExpect(jsonPath("$.customerUrl", equalTo(API_V1_CUSTOMERS+"1")))
                .andDo(result -> log.info(result.getResponse().getContentAsString()));

    }
}