package ru.dobrotrener.spring5mvcrest.api.v1.model;

import lombok.Data;
import ru.dobrotrener.spring5mvcrest.controllers.v1.VendorController;

@Data
public class VendorDTO {
    private Long id;
    private String name;
    private String vendorUrl;

    public String getVendorUrl() {
        return VendorController.BASE_URL + id;
    }
}
