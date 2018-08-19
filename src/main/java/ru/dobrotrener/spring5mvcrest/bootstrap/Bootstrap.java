package ru.dobrotrener.spring5mvcrest.bootstrap;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import ru.dobrotrener.spring5mvcrest.domain.Category;
import ru.dobrotrener.spring5mvcrest.repositories.CategoryRepository;


@Slf4j
@Component
public class Bootstrap implements CommandLineRunner {

    private CategoryRepository categoryRepository;

    public Bootstrap(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    /**
     * CommandLineRunner - Spring boot specific interface
     * @param args
     * @throws Exception
     */
    @Override
    public void run(String... args) throws Exception {
        createFriuts();
    }

    private void createFriuts() {
        Category fruits = new Category();
        fruits.setName("Fruits");
        categoryRepository.save(fruits);

        Category dried = new Category();
        fruits.setName("Dried");
        categoryRepository.save(dried);

        Category fresh = new Category();
        fruits.setName("Fresh");
        categoryRepository.save(fresh);

        Category exotic = new Category();
        fruits.setName("Exotic");
        categoryRepository.save(exotic);

        Category nuts = new Category();
        fruits.setName("Nuts");
        categoryRepository.save(nuts);

        log.info("Categories loaded on bootstrap: " + categoryRepository.count());

    }
}
