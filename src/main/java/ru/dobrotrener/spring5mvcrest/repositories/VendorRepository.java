package ru.dobrotrener.spring5mvcrest.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.dobrotrener.spring5mvcrest.domain.Vendor;

public interface VendorRepository  extends JpaRepository<Vendor, Long> {
    Vendor findByName(String name);
}
