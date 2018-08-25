package ru.dobrotrener.spring5mvcrest.services;

import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import ru.dobrotrener.spring5mvcrest.api.v1.mapper.VendorMapper;
import ru.dobrotrener.spring5mvcrest.repositories.VendorRepository;

import static org.junit.Assert.*;

@Slf4j
public class VendorServiceImplTest {

    @Mock
    VendorRepository vendorRepository;

    VendorService vendorService;

    VendorMapper vendorMapper = VendorMapper.INSTANCE;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        vendorService = new VendorServiceImpl(vendorRepository);
    }

    @Test
    public void getAllVendors() {
        //given


        //when


        //then
    }

    @Test
    public void createVendor() {
        //given


        //when


        //then
    }

    @Test
    public void saveVendorByDTO() {
        //given


        //when


        //then
    }

    @Test
    public void getVendorById() {
        //given


        //when


        //then
    }

    @Test
    public void patchVendor() {
        //given


        //when


        //then
    }

    @Test
    public void deleteVendorById() {
        //given


        //when


        //then
    }
}