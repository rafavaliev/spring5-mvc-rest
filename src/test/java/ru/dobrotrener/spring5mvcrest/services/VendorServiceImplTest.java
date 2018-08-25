package ru.dobrotrener.spring5mvcrest.services;

import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import ru.dobrotrener.spring5mvcrest.api.v1.mapper.VendorMapper;
import ru.dobrotrener.spring5mvcrest.api.v1.model.VendorDTO;
import ru.dobrotrener.spring5mvcrest.controllers.v1.VendorController;
import ru.dobrotrener.spring5mvcrest.domain.Vendor;
import ru.dobrotrener.spring5mvcrest.exceptions.ResourceNotFoundException;
import ru.dobrotrener.spring5mvcrest.repositories.VendorRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.mockito.internal.verification.VerificationModeFactory.times;
import static org.mockito.Mockito.verify;

@Slf4j
public class VendorServiceImplTest {

    @Mock
    VendorRepository vendorRepository;

    VendorService vendorService;

    VendorMapper vendorMapper = VendorMapper.INSTANCE;

    private static final Long ID_1 = 1L;
    private static final Long ID_2 = 2L;
    private static final Long ID_3 = 3L;
    private static final String NAME = "VendorName";
    private static final String UPDATED_NAME = "UpdatedVendorName";

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        vendorService = new VendorServiceImpl(vendorRepository);
    }

    @Test
    public void getAllVendors() {
        //given
        List<Vendor> vendors = Arrays.asList(
                new Vendor(ID_1, NAME),
                new Vendor(ID_2, NAME),
                new Vendor(ID_3, NAME)
        );
        when(vendorRepository.findAll()).thenReturn(vendors);

        //when
        List<VendorDTO> vendorListDTO = vendorService.getAllVendors();

        //then
        assertNotNull(vendorListDTO);
        log.info(vendorListDTO.toString());
        assertEquals(vendors.size(), vendorListDTO.size());
    }

    @Test
    public void createVendor() {
        //given
        VendorDTO vendorDTO = new VendorDTO(NAME);

        Vendor createdVendor = new Vendor(NAME);
        createdVendor.setId(ID_1);
        createdVendor.setVendorUrl(VendorController.BASE_URL + "1");

        when(vendorRepository.save(any(Vendor.class))).thenReturn(createdVendor);

        //when
        VendorDTO createdVendorDTO = vendorService.createVendor(vendorDTO);

        //then
        assertNotNull(createdVendorDTO);
        log.info(createdVendorDTO.toString());
        assertEquals(vendorDTO.getName(), createdVendorDTO.getName());
        assertEquals(VendorController.BASE_URL + "1", createdVendorDTO.getVendorUrl());
    }

    @Test
    public void saveVendorByDTO() {
        //given
        VendorDTO vendorDTO = new VendorDTO(ID_1, NAME);

        Vendor updatedVendor = new Vendor(ID_1, NAME);
        updatedVendor.setId(ID_1);
        updatedVendor.setVendorUrl(VendorController.BASE_URL + "1");

        when(vendorRepository.save(any(Vendor.class))).thenReturn(updatedVendor);

        //when
        VendorDTO updatedVendorDTO = vendorService.saveVendorByDTO(ID_1, vendorDTO);

        //then
        assertNotNull(updatedVendorDTO);
        log.info(updatedVendorDTO.toString());
        assertEquals(vendorDTO.getName(), updatedVendorDTO.getName());
        assertEquals(VendorController.BASE_URL + "1", updatedVendorDTO.getVendorUrl());
    }

    @Test
    public void getVendorById() {
        //given
        Vendor vendor = new Vendor();
        vendor.setId(ID_1);

        when(vendorRepository.findById(anyLong())).thenReturn(Optional.of(vendor));

        //when
        VendorDTO vendorDTO = vendorService.getVendorById(ID_1);

        //then
        assertNotNull(vendorDTO);
        log.info(vendorDTO.toString());
        assertEquals(vendor.getId(), vendor.getId());
    }

    @Test(expected = ResourceNotFoundException.class)
    public void getVendorByIdNotFound() {
        when(vendorRepository.findById(anyLong())).thenThrow(ResourceNotFoundException.class);
        vendorService.getVendorById(ID_1);
    }


    @Test
    public void deleteVendorById() {
        //given
        when(vendorRepository.findById(anyLong())).thenReturn(Optional.of(new Vendor()));
        //when
        vendorService.deleteVendorById(ID_1);

        //then
        verify(vendorRepository, times(1)).deleteById(anyLong());
    }
}