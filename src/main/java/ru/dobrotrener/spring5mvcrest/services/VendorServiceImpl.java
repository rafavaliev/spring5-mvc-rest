package ru.dobrotrener.spring5mvcrest.services;

import org.springframework.stereotype.Service;
import ru.dobrotrener.spring5mvcrest.api.v1.mapper.VendorMapper;
import ru.dobrotrener.spring5mvcrest.api.v1.model.VendorDTO;
import ru.dobrotrener.spring5mvcrest.repositories.VendorRepository;

import java.util.List;

@Service
public class VendorServiceImpl implements VendorService {

    VendorRepository vendorRepository;

    VendorMapper vendorMapper = VendorMapper.INSTANCE;

    public VendorServiceImpl(VendorRepository vendorRepository) {
        this.vendorRepository = vendorRepository;
    }

    @Override
    public List<VendorDTO> getAllVendors() {
        return null;
    }

    @Override
    public VendorDTO createVendor(VendorDTO VendorDTO) {
        return null;
    }

    @Override
    public VendorDTO saveVendorByDTO(Long id, VendorDTO VendorDTO) {
        return null;
    }

    @Override
    public VendorDTO getVendorById(Long id) {
        return null;
    }

    @Override
    public VendorDTO patchVendor(Long id, VendorDTO VendorDTO) {
        return null;
    }

    @Override
    public void deleteVendorById(Long id) {

    }
}
