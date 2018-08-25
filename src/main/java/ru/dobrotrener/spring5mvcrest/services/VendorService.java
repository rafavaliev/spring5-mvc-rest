package ru.dobrotrener.spring5mvcrest.services;

import ru.dobrotrener.spring5mvcrest.api.v1.model.VendorDTO;

import java.util.List;

public interface VendorService {
    List<VendorDTO> getAllVendors();
    VendorDTO createVendor(VendorDTO VendorDTO);
    VendorDTO saveVendorByDTO(Long id, VendorDTO VendorDTO);
    VendorDTO getVendorById(Long id);
    VendorDTO patchVendor(Long id, VendorDTO VendorDTO);
    void deleteVendorById(Long id);
}
