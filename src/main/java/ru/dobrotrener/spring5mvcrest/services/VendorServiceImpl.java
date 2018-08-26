package ru.dobrotrener.spring5mvcrest.services;

import org.springframework.stereotype.Service;
import ru.dobrotrener.spring5mvcrest.api.v1.mapper.VendorMapper;
import ru.dobrotrener.spring5mvcrest.api.v1.model.VendorDTO;
import ru.dobrotrener.spring5mvcrest.controllers.v1.VendorController;
import ru.dobrotrener.spring5mvcrest.domain.Vendor;
import ru.dobrotrener.spring5mvcrest.exceptions.ResourceNotFoundException;
import ru.dobrotrener.spring5mvcrest.repositories.VendorRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class VendorServiceImpl implements VendorService {

    VendorRepository vendorRepository;

    VendorMapper vendorMapper = VendorMapper.INSTANCE;

    public VendorServiceImpl(VendorRepository vendorRepository) {
        this.vendorRepository = vendorRepository;
    }

    @Override
    public List<VendorDTO> getAllVendors() {
        return vendorRepository.findAll()
                .stream()
                .map(vendor -> vendorMapper.vendorToVendorDTO(vendor))
                .collect(Collectors.toList());
    }

    @Override
    public VendorDTO createVendor(VendorDTO vendorDTO) {
        return saveAndReturnDTO(vendorMapper.vendorDTOtoVendor(vendorDTO));
    }

    @Override
    public VendorDTO saveVendorByDTO(Long id, VendorDTO vendorDTO) {
        Vendor vendor = vendorMapper.vendorDTOtoVendor(vendorDTO);
        vendor.setId(id);
        vendor.setVendorUrl(getCustomerUrl(vendorDTO.getId()));
        return saveAndReturnDTO(vendor);
    }

    @Override
    public VendorDTO getVendorById(Long id) {
        return vendorRepository.findById(id)
                .map(vendorMapper::vendorToVendorDTO)
                .map(vendorDTO -> {
                    vendorDTO.setVendorUrl(getCustomerUrl(id));
                    return vendorDTO;
                })
                .orElseThrow(ResourceNotFoundException::new);
    }

    private String getCustomerUrl(Long id) {
        return VendorController.BASE_URL + id;
    }

    @Override
    public VendorDTO patchVendor(Long id, VendorDTO vendorDTO) {
        return vendorRepository.findById(id)
                .map(vendor -> {
                    if (vendorDTO.getName() != null) {
                        vendor.setName(vendorDTO.getName());
                    }
                    return vendorMapper.vendorToVendorDTO(vendorRepository.save(vendor));
                })
                .orElseThrow(ResourceNotFoundException::new);
    }

    @Override
    public void deleteVendorById(Long id) {
        getVendorById(id);
        vendorRepository.deleteById(id);

    }

    private VendorDTO saveAndReturnDTO(Vendor vendor) {
        Vendor savedVendor = vendorRepository.save(vendor);

        VendorDTO returnDto = vendorMapper.vendorToVendorDTO(savedVendor);
        returnDto.setVendorUrl(VendorController.BASE_URL + savedVendor.getId().toString());
        return returnDto;
    }
}
