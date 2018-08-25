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
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.dobrotrener.spring5mvcrest.controllers.v1.AbstractRestControllerTest.asJsonString;

@Slf4j
public class CustomerControllerTest {

    private static final Long ID_1 = 1L;
    private static final Long ID_2 = 2L;
    private static final Long ID_3 = 3L;
    private static final String FIRST_NAME = "FirstName";
    private static final String LAST_NAME = "LastName";
    private static final String UPDATED_NAME = "UpdatedName";

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
                get(CustomerController.BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.customers", hasSize(3)))
                .andDo(result -> log.info(result.getResponse().getContentAsString()));

    }

    @Test
    public void getCustomerById() throws Exception {
        //given
        CustomerDTO customer = new CustomerDTO();
        customer.setId(ID_1);
        customer.setFirstName(FIRST_NAME);
        customer.setLastName(LAST_NAME);

        when(customerService.getCustomerById(anyLong())).thenReturn(customer);

        //when
        mockMvc.perform(
                get(CustomerController.BASE_URL + "1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName", equalTo(FIRST_NAME)))
                .andExpect(jsonPath("$.id", equalTo(1)))
                .andDo(result -> log.info(result.getResponse().getContentAsString()));
    }

    @Test
    public void createCustomerTest() throws Exception {
        //given
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setFirstName(FIRST_NAME);
        customerDTO.setLastName(LAST_NAME);

        CustomerDTO returnDto = new CustomerDTO();
        returnDto.setId(ID_1);
        returnDto.setFirstName(customerDTO.getFirstName());
        returnDto.setLastName(customerDTO.getLastName());
        returnDto.setCustomerUrl(CustomerController.BASE_URL + "1");

        when(customerService.createCustomer(any(CustomerDTO.class))).thenReturn(returnDto);

        //when
        mockMvc.perform(post(CustomerController.BASE_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(customerDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.firstName", equalTo(FIRST_NAME)))
                .andExpect(jsonPath("$.customerUrl", equalTo(CustomerController.BASE_URL + "1")))
                .andDo(result -> log.info(result.getResponse().getContentAsString()));

    }

    @Test
    public void updateCustomerTest() throws Exception {
        //given
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setFirstName(FIRST_NAME);
        customerDTO.setLastName(LAST_NAME);
        customerDTO.setId(ID_1);

        CustomerDTO returnDto = new CustomerDTO();
        returnDto.setFirstName(customerDTO.getFirstName());
        returnDto.setLastName(customerDTO.getLastName());
        returnDto.setCustomerUrl(CustomerController.BASE_URL + "1");
        returnDto.setId(ID_1);

        when(customerService.saveCustomerByDTO(anyLong(), any(CustomerDTO.class)))
                .thenReturn(returnDto);

        //when
        mockMvc.perform(put(CustomerController.BASE_URL + "1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(customerDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName", equalTo(FIRST_NAME)))
                .andExpect(jsonPath("$.id", equalTo(1)))
                .andExpect(jsonPath("$.customerUrl", equalTo(CustomerController.BASE_URL + "1")))
                .andDo(result -> log.info(result.getResponse().getContentAsString()));

    }

    @Test
    public void patchCustomerTest() throws Exception {
        //given
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setFirstName(FIRST_NAME);
        customerDTO.setLastName(LAST_NAME);
        customerDTO.setId(ID_1);

        CustomerDTO returnDto = new CustomerDTO();
        returnDto.setFirstName(customerDTO.getFirstName());
        returnDto.setLastName(UPDATED_NAME);
        returnDto.setCustomerUrl(CustomerController.BASE_URL + "1");
        returnDto.setId(ID_1);

        when(customerService.patchCustomer(anyLong(), any(CustomerDTO.class)))
                .thenReturn(returnDto);

        //when
        mockMvc.perform(patch(CustomerController.BASE_URL + "1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(customerDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName", equalTo(FIRST_NAME)))
                .andExpect(jsonPath("$.id", equalTo(1)))
                .andExpect(jsonPath("$.customerUrl", equalTo(CustomerController.BASE_URL + "1")))
                .andDo(result -> log.info(result.getResponse().getContentAsString()));

    }

    @Test
    public void deleteCustomerTest() throws Exception {
        mockMvc.perform(delete(CustomerController.BASE_URL + "1"))
                .andExpect(status().isOk())
                .andDo(result -> log.info(result.getResponse().getContentAsString()));

    }
}