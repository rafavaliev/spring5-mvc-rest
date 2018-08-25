package ru.dobrotrener.spring5mvcrest.controllers.v1;

import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import ru.dobrotrener.spring5mvcrest.api.v1.model.CategoryDTO;
import ru.dobrotrener.spring5mvcrest.controllers.RestResponseEntityExceptionHandler;
import ru.dobrotrener.spring5mvcrest.exceptions.ResourceNotFoundException;
import ru.dobrotrener.spring5mvcrest.services.CategoryService;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@Slf4j
public class CategoryControllerTest {

    public static final String NUTS = "Nuts";
    public static final String FRUITS = "Fruits";
    public static final Long ID_2 = 2L;
    public static final Long ID_1 = 1L;
    @InjectMocks
    private CategoryController categoryController;

    @Mock
    private CategoryService categoryService;

    private MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        mockMvc = MockMvcBuilders
                .standaloneSetup(categoryController)
                .setControllerAdvice(new RestResponseEntityExceptionHandler())
                .build();
    }

    @Test
    public void testListCategories() throws Exception {
        //given
        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setId(ID_1);
        categoryDTO.setName(FRUITS);

        CategoryDTO categoryDTO2 = new CategoryDTO();
        categoryDTO2.setId(ID_2);
        categoryDTO2.setName(NUTS);

        List<CategoryDTO> categories = Arrays.asList(categoryDTO, categoryDTO2);

        when(categoryService.getAllCategories()).thenReturn(categories);

        //when
        mockMvc.perform(
                get(CategoryController.BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.categories", hasSize(2)))
                .andDo(result -> log.info(result.getResponse().getContentAsString()));
    }

    @Test
    public void testGetCategoryByName() throws Exception {
        //given
        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setId(ID_1);
        categoryDTO.setName(FRUITS);

        when(categoryService.getCategoryByName(anyString())).thenReturn(categoryDTO);

        //when
        mockMvc.perform(
                get(CategoryController.BASE_URL + FRUITS)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", equalTo(FRUITS)))
                .andDo(result -> log.info(result.getResponse().getContentAsString()));
    }

    @Test
    public void testGetCategoryByNameNotFound() throws Exception {
        when(categoryService.getCategoryByName(anyString())).thenThrow(ResourceNotFoundException.class);

        mockMvc.perform(
                get(CategoryController.BASE_URL + "/NotFoundCategory")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andDo(result -> log.info(result.getResponse().getContentAsString()));
    }


}