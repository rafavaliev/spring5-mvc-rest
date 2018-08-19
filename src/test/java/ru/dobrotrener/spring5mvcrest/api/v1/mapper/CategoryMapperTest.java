package ru.dobrotrener.spring5mvcrest.api.v1.mapper;

import org.junit.Before;
import org.junit.Test;
import ru.dobrotrener.spring5mvcrest.api.v1.model.CategoryDTO;
import ru.dobrotrener.spring5mvcrest.domain.Category;

import static org.junit.Assert.*;

public class CategoryMapperTest {

    public static final String FRUITS = "Fruits";
    CategoryMapper categoryMapper;
    @Before
    public void setUp() throws Exception {
        categoryMapper = CategoryMapper.INSTANCE;
    }

    @Test
    public void categoryToCategoryDTO() {
        //given
        Category category = new Category();
        category.setId(1L);
        category.setName(FRUITS);

        //when
        CategoryDTO categoryDTO = categoryMapper.categoryToCategoryDTO(category);

        //then
        assertNotNull(categoryDTO);
        assertEquals(Long.valueOf(1L), categoryDTO.getId());
        assertEquals(FRUITS,categoryDTO.getName());
    }
}