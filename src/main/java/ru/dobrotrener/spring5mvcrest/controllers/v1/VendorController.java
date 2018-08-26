package ru.dobrotrener.spring5mvcrest.controllers.v1;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.dobrotrener.spring5mvcrest.api.v1.model.VendorDTO;
import ru.dobrotrener.spring5mvcrest.api.v1.model.VendorListDTO;
import ru.dobrotrener.spring5mvcrest.services.VendorService;

@Api(description = "Vendors controller")
@RestController
@RequestMapping(value = {VendorController.BASE_URL, VendorController.BASE_URL2})
public class VendorController {
    public static final String BASE_URL = "/api/v1/vendors/";
    public static final String BASE_URL2 = "/api/v1/vendors";
    private VendorService vendorService;

    public VendorController(VendorService vendorService) {
        this.vendorService = vendorService;
    }

    @ApiOperation(value = "Get list of all vendors")
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public VendorListDTO getAllVendors() {
        return new VendorListDTO(vendorService.getAllVendors());

    }

    @ApiOperation(value = "Get vendor with id")
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public VendorDTO getVendorById(@PathVariable Long id) {
        return vendorService.getVendorById(id);
    }

    @ApiOperation(value = "Create vendor")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public VendorDTO createVendor(@RequestBody VendorDTO customerDTO) {
        return vendorService.createVendor(customerDTO);
    }

    @ApiOperation(value = "Update vendor with id", notes = "Replace vendor with input VendorDTO object")
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public VendorDTO updateVendor(@PathVariable Long id, @RequestBody VendorDTO customerDTO) {
        return vendorService.saveVendorByDTO(id, customerDTO);
    }

    @ApiOperation(value = "Patch vendor with id", notes = "Update vendor with only non-empty input fields")
    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public VendorDTO patchVendor(@PathVariable Long id, @RequestBody VendorDTO customerDTO) {
        return vendorService.patchVendor(id, customerDTO);
    }

    @ApiOperation(value = "Delete vendor with id")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteVendor(@PathVariable Long id) {
        vendorService.deleteVendorById(id);
    }
}
