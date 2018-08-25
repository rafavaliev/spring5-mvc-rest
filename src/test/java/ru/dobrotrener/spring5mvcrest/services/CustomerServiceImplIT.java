package ru.dobrotrener.spring5mvcrest.services;


import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;
import ru.dobrotrener.spring5mvcrest.api.v1.model.CustomerDTO;
import ru.dobrotrener.spring5mvcrest.bootstrap.Bootstrap;
import ru.dobrotrener.spring5mvcrest.domain.Customer;
import ru.dobrotrener.spring5mvcrest.repositories.CategoryRepository;
import ru.dobrotrener.spring5mvcrest.repositories.CustomerRepository;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@DataJpaTest
@Slf4j
public class CustomerServiceImplIT {

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    CategoryRepository categoryRepository;

    CustomerService customerService;

    @Before
    public void setUp() throws Exception {
        log.info("Loading customer data");
        log.info("Customers in DB: " + customerRepository.findAll().size());

        //setup data for testing
        Bootstrap bootstrap = new Bootstrap(categoryRepository, customerRepository);
        bootstrap.run();

        customerService = new CustomerServiceImpl(customerRepository);
    }

    @Test
    public void patchCustomerUpdateFirstName() throws Exception {
        //given
        String updatedName = "UpdatedName";
        Long id = getCustomerId();

        Customer originalCustomer = customerRepository.getOne(id);
        assertNotNull(originalCustomer);
        String originalFirstName = originalCustomer.getFirstName();
        String originalLastName = originalCustomer.getLastName();

        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setFirstName(updatedName);

        //when
        customerService.patchCustomer(id, customerDTO);

        //then
        Customer updatedCustomer = customerRepository.findById(id).get();

        log.info("Original: " + originalCustomer + ", updated: " + updatedCustomer);
        assertNotNull(updatedCustomer);
        assertEquals(updatedName, updatedCustomer.getFirstName());
        assertThat(originalFirstName, not(equalTo(updatedCustomer.getFirstName())));
        assertThat(originalLastName, equalTo(updatedCustomer.getLastName()));

    }

    @Test
    public void patchCustomerUpdateLastName() throws Exception {
        //given
        String updatedName = "UpdatedName";
        Long id = getCustomerId();

        Customer originalCustomer = customerRepository.getOne(id);
        assertNotNull(originalCustomer);
        String originalFirstName = originalCustomer.getFirstName();
        String originalLastName = originalCustomer.getLastName();

        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setLastName(updatedName);

        //when
        customerService.patchCustomer(id, customerDTO);

        //then
        Customer updatedCustomer = customerRepository.findById(id).get();

        log.info("Original: " + originalCustomer + ", updated: " + updatedCustomer);
        assertNotNull(updatedCustomer);
        assertEquals(updatedName, updatedCustomer.getLastName());
        assertThat(originalFirstName, equalTo(updatedCustomer.getFirstName()));
        assertThat(originalLastName, not(equalTo(updatedCustomer.getLastName())));
    }


    private Long getCustomerId() {
        return customerRepository.findAll()
                .stream()
                .findFirst()
                .map(Customer::getId).orElseThrow(RuntimeException::new);
    }
}
