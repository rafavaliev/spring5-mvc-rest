package ru.dobrotrener.spring5mvcrest.api.v1.mapper;

import org.junit.Test;
import ru.dobrotrener.spring5mvcrest.api.v1.model.VendorDTO;
import ru.dobrotrener.spring5mvcrest.domain.Vendor;

import static org.junit.Assert.*;

public class VendorMapperTest {

    VendorMapper vendorMapper = VendorMapper.INSTANCE;

    private static final Long ID_1 = 1L;
    private static final String NAME = "VendorName";

    @Test
    public void vendorToVendorDTO() {
        //given
        Vendor vendor = new Vendor();
        vendor.setId(ID_1);
        vendor.setName(NAME);

        //when
        VendorDTO vendorDTO = vendorMapper.vendorToVendorDTO(vendor);

        //then
        assertNotNull(vendorDTO);
        assertEquals(vendor.getId(), vendorDTO.getId());
        assertEquals(vendor.getName(), vendorDTO.getName());
    }

    @Test
    public void vendorDTOtoVendor() {
        //given
        VendorDTO vendorDTO = new VendorDTO();
        vendorDTO.setId(ID_1);
        vendorDTO.setName(NAME);

        //when
        Vendor vendor = vendorMapper.vendorDTOtoVendor(vendorDTO);

        //then
        assertNotNull(vendor);
        assertEquals(vendorDTO.getId(), vendor.getId());
        assertEquals(vendorDTO.getName(), vendor.getName());
    }
}