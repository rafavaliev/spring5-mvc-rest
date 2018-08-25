package ru.dobrotrener.spring5mvcrest.controllers.v1;

import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ru.dobrotrener.spring5mvcrest.api.v1.model.VendorDTO;
import ru.dobrotrener.spring5mvcrest.controllers.RestResponseEntityExceptionHandler;
import ru.dobrotrener.spring5mvcrest.domain.Vendor;
import ru.dobrotrener.spring5mvcrest.exceptions.ResourceNotFoundException;
import ru.dobrotrener.spring5mvcrest.services.VendorService;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.any;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static ru.dobrotrener.spring5mvcrest.controllers.v1.AbstractRestControllerTest.asJsonString;

@Slf4j
public class VendorControllerTest {

    private static final Long ID_1 = 1L;
    private static final Long ID_2 = 2L;
    private static final Long ID_3 = 3L;
    private static final String NAME = "VendorName";
    private static final String UPDATED_NAME = "UpdatedVendorName";

    @Mock
    private VendorService vendorService;

    @InjectMocks
    private VendorController vendorController;

    private MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders
                .standaloneSetup(vendorController)
                .setControllerAdvice(new RestResponseEntityExceptionHandler())
                .build();
    }

    @Test
    public void testGetAllVendors() throws Exception {
        //given
        List<VendorDTO> vendors = Arrays.asList(
                new VendorDTO(ID_1, NAME),
                new VendorDTO(ID_2, NAME),
                new VendorDTO(ID_3, NAME));

        when(vendorService.getAllVendors()).thenReturn(vendors);

        //when
        mockMvc.perform(
                get(VendorController.BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.vendors", hasSize(3)))
                .andDo(result -> log.info(result.getResponse().getContentAsString()));

    }

    @Test
    public void testGetVendorById() throws Exception {
        //given
        VendorDTO vendorDTO = new VendorDTO();
        vendorDTO.setId(ID_1);
        vendorDTO.setName(NAME);

        when(vendorService.getVendorById(anyLong())).thenReturn(vendorDTO);
        //when
        mockMvc.perform(
                get(VendorController.BASE_URL + "1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", equalTo(NAME)))
                .andExpect(jsonPath("$.id", equalTo(1)))
                .andDo(result -> log.info(result.getResponse().getContentAsString()));

    }

    @Test
    public void testGetVendorByIdNotFound() throws Exception {
        //given
        when(vendorService.getVendorById(anyLong())).thenThrow(ResourceNotFoundException.class);
        //when
        mockMvc.perform(
                get(VendorController.BASE_URL + "1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andDo(result -> log.info(result.getResponse().getContentAsString()));

    }

    @Test
    public void testCreateVendor() throws Exception {
        //given
        VendorDTO vendorDTO = new VendorDTO();
        vendorDTO.setName(NAME);

        VendorDTO savedVendorDTO = new VendorDTO();
        savedVendorDTO.setId(ID_1);
        savedVendorDTO.setName(NAME);
        savedVendorDTO.setVendorUrl(VendorController.BASE_URL + "1");

        when(vendorService.createVendor(ArgumentMatchers.any(VendorDTO.class))).thenReturn(savedVendorDTO);

        //when
        mockMvc.perform(
                post(VendorController.BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(vendorDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name", equalTo(NAME)))
                .andExpect(jsonPath("$.vendorUrl", equalTo(VendorController.BASE_URL + "1")))
                .andDo(result -> log.info(result.getResponse().getContentAsString()));

    }

    @Test
    public void testUpdateVendor() throws Exception {
        //given
        VendorDTO vendorDTO = new VendorDTO(ID_1, NAME);

        VendorDTO updateVendorDTO = new VendorDTO(ID_1, NAME);

        when(vendorService.saveVendorByDTO(anyLong(), ArgumentMatchers.any(VendorDTO.class)))
                .thenReturn(updateVendorDTO);

        //when
        mockMvc.perform(
                put(VendorController.BASE_URL + "1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(vendorDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", equalTo(NAME)))
                .andExpect(jsonPath("$.id", equalTo(1)))
                .andExpect(jsonPath("$.vendorUrl", equalTo(VendorController.BASE_URL + "1")))
                .andDo(result -> log.info(result.getResponse().getContentAsString()));
    }

    @Test
    public void testPatchVendor() throws Exception {
        //given
        VendorDTO vendorDTO = new VendorDTO(ID_1, NAME);


        VendorDTO pathedVendorDTO = new VendorDTO(vendorDTO.getId(), UPDATED_NAME);

        when(vendorService.patchVendor(anyLong(), ArgumentMatchers.any(VendorDTO.class)))
                .thenReturn(pathedVendorDTO);

        //when
        mockMvc.perform(
                patch(VendorController.BASE_URL + "1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(vendorDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", equalTo(UPDATED_NAME)))
                .andExpect(jsonPath("$.id", equalTo(1)))
                .andExpect(jsonPath("$.vendorUrl", equalTo(VendorController.BASE_URL + "1")))
                .andDo(result -> log.info(result.getResponse().getContentAsString()));
    }

    @Test
    public void testDeleteVendor() throws Exception {
        //given
        when(vendorService.getVendorById(anyLong())).thenReturn(new VendorDTO());

        //when
        mockMvc.perform(
                delete(VendorController.BASE_URL + "1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(result -> log.info(result.getResponse().getContentAsString()));

    }
}