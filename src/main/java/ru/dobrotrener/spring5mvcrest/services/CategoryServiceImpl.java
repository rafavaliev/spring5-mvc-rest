package ru.dobrotrener.spring5mvcrest.services;

import javassist.NotFoundException;
import org.springframework.stereotype.Service;
import ru.dobrotrener.spring5mvcrest.api.v1.mapper.CategoryMapper;
import ru.dobrotrener.spring5mvcrest.api.v1.model.CategoryDTO;
import ru.dobrotrener.spring5mvcrest.domain.Category;
import ru.dobrotrener.spring5mvcrest.repositories.CategoryRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
        this.categoryMapper = CategoryMapper.INSTANCE;
    }

    @Override
    public List<CategoryDTO> getAllCategories() {
        List<Category> categories = categoryRepository.findAll();
        List<CategoryDTO> categoryDTOs = categories
                .stream()
                .map(cat -> categoryMapper.categoryToCategoryDTO(cat))
                .collect(Collectors.toList());

        return categoryDTOs;
    }

    @Override
    public CategoryDTO getCategoryByName(String name) {
        Category category = categoryRepository.findByName(name);
        if (category == null) {
            category = new Category();
        }
        return categoryMapper.categoryToCategoryDTO(category);
    }
}
