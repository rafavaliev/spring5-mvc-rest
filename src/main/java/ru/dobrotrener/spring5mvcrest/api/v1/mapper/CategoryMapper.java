package ru.dobrotrener.spring5mvcrest.api.v1.mapper;

import org.mapstruct.Mapping;
import ru.dobrotrener.spring5mvcrest.api.v1.model.CategoryDTO;
import ru.dobrotrener.spring5mvcrest.domain.Category;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;


@Mapper
public interface CategoryMapper {
    CategoryMapper INSTANCE = Mappers.getMapper(CategoryMapper.class);

    CategoryDTO categoryToCategoryDTO(Category category);
}