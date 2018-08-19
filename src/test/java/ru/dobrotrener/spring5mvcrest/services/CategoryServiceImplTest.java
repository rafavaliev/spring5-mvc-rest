package ru.dobrotrener.spring5mvcrest.services;

import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ru.dobrotrener.spring5mvcrest.api.v1.mapper.CategoryMapper;
import ru.dobrotrener.spring5mvcrest.api.v1.model.CategoryDTO;
import ru.dobrotrener.spring5mvcrest.domain.Category;
import ru.dobrotrener.spring5mvcrest.repositories.CategoryRepository;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@Slf4j
public class CategoryServiceImplTest {

    public static final String FRUITS = "Fruits";
    public static final Long ID_1 = 1L;
    @Mock
    private CategoryRepository categoryRepository;

    private CategoryMapper categoryMapper;

    private CategoryService categoryService;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        categoryService = new CategoryServiceImpl(categoryRepository);
    }

    @Test
    public void getAllCategories() {
        //given
        List<Category> categories = Arrays.asList(new Category(), new Category(), new Category());

        when(categoryRepository.findAll()).thenReturn(categories);

        //when
        List<CategoryDTO> categoryDTOs = categoryService.getAllCategories();

        //then
        log.info(categoryDTOs.toString());
        assertNotNull(categoryDTOs);
        assertEquals(3, categoryDTOs.size());
    }

    @Test
    public void getCategoryByName() {
        //given
        Category category = new Category();
        category.setId(ID_1);
        category.setName(FRUITS);

        when(categoryRepository.findByName(anyString())).thenReturn(category);

        //when
        CategoryDTO categoryDTO = categoryService.getCategoryByName(FRUITS);

        //then
        log.info(categoryDTO.toString());
        assertNotNull(categoryDTO);
        assertEquals(ID_1, categoryDTO.getId());
        assertEquals(FRUITS, categoryDTO.getName());



    }
}