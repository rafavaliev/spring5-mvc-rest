package ru.dobrotrener.spring5mvcrest.api.v1.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import ru.dobrotrener.spring5mvcrest.controllers.v1.CustomerController;

@Data
public class CustomerDTO {

    private Long id;
    @ApiModelProperty(value = "First name", required = true)
    private String firstName;
    @ApiModelProperty(value = "Last name", required = true)
    private String lastName;

    @JsonProperty("customer_url")
    private String customerUrl;

    public String getCustomerUrl() {
        return CustomerController.BASE_URL + id;
    }
}
