package ru.dobrotrener.spring5mvcrest.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.dobrotrener.spring5mvcrest.domain.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
}
