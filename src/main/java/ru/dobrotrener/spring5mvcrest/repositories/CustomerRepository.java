package ru.dobrotrener.spring5mvcrest.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.dobrotrener.spring5mvcrest.domain.Category;
import ru.dobrotrener.spring5mvcrest.domain.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Customer findByFirstName(String name);

    Customer findByLastName(String name);
}
