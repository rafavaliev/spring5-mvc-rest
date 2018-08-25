package ru.dobrotrener.spring5mvcrest.api.v1.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import ru.dobrotrener.spring5mvcrest.controllers.v1.VendorController;

@Data
public class VendorDTO {
    private Long id;
    private String name;
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
