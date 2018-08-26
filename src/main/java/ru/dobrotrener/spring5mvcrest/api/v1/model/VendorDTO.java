package ru.dobrotrener.spring5mvcrest.api.v1.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.dobrotrener.spring5mvcrest.controllers.v1.VendorController;

@Data
public class VendorDTO {

    private Long id;

    @ApiModelProperty(value = "Vendor name", required = true)
    private String name;

    @JsonProperty("vendor_url")
    private String vendorUrl;

    public String getVendorUrl() {
        return VendorController.BASE_URL + id;
    }


    public VendorDTO() {
    }

    public VendorDTO(String name) {
        this.name = name;
    }

    public VendorDTO(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}
