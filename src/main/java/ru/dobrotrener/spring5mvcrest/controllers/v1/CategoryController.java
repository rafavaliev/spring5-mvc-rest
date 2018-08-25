package ru.dobrotrener.spring5mvcrest.controllers.v1;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.dobrotrener.spring5mvcrest.api.v1.model.CategoryDTO;
import ru.dobrotrener.spring5mvcrest.api.v1.model.CategoryListDTO;
import ru.dobrotrener.spring5mvcrest.services.CategoryService;

@Controller
@RequestMapping(value = {CategoryController.BASE_URL, CategoryController.BASE_URL2})
public class CategoryController {

    public static final String BASE_URL = "/api/v1/categories/";
    public static final String BASE_URL2 = "/api/v1/categories";
    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    public ResponseEntity<CategoryListDTO> getAllCategories() {
        CategoryListDTO listDTO = new CategoryListDTO(categoryService.getAllCategories());
        return new ResponseEntity<>(listDTO, HttpStatus.OK);
    }

    @GetMapping("{name}")
    public ResponseEntity<CategoryDTO> getCategoryByName(@PathVariable String name) {
        CategoryDTO categoryDTO = categoryService.getCategoryByName(name);
        return new ResponseEntity<>(categoryDTO, HttpStatus.OK);
    }

}
