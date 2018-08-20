package ru.dobrotrener.spring5mvcrest.controllers.v1;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.dobrotrener.spring5mvcrest.api.v1.model.CustomerDTO;
import ru.dobrotrener.spring5mvcrest.api.v1.model.CustomerListDTO;
import ru.dobrotrener.spring5mvcrest.services.CustomerService;

@Controller
@RequestMapping(value = {"/api/v1/customers/","/api/v1/customers"})
public class CustomerController {
    private CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping
    public ResponseEntity<CustomerListDTO> getAllCustomers() {
        CustomerListDTO listDTO = new CustomerListDTO(customerService.getAllCustomers());
        return new ResponseEntity<>(listDTO, HttpStatus.OK);
    }

    @GetMapping("{name}")
    public ResponseEntity<CustomerDTO> getCustomerByFirstName(@PathVariable String name) {
        CustomerDTO customerDTO = customerService.getCustomerByFirstName(name);
        return new ResponseEntity<>(customerDTO, HttpStatus.OK);
    }

}

