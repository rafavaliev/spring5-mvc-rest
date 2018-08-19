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
@RequestMapping(value = {"/api/v1/categories/","/api/v1/categories"})
public class CategoryController {

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
