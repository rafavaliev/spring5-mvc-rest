package ru.dobrotrener.spring5mvcrest.api.v1.model;

import lombok.Data;
import ru.dobrotrener.spring5mvcrest.controllers.v1.CustomerController;

@Data
public class CustomerDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private String customerUrl;

    public String getCustomerUrl() {
        return CustomerController.BASE_URL + id;
    }
}
