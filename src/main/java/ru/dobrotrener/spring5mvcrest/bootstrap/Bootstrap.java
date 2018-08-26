package ru.dobrotrener.spring5mvcrest.bootstrap;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import ru.dobrotrener.spring5mvcrest.domain.Category;
import ru.dobrotrener.spring5mvcrest.domain.Customer;
import ru.dobrotrener.spring5mvcrest.domain.Vendor;
import ru.dobrotrener.spring5mvcrest.repositories.CategoryRepository;
import ru.dobrotrener.spring5mvcrest.repositories.CustomerRepository;
import ru.dobrotrener.spring5mvcrest.repositories.VendorRepository;

import java.util.Arrays;


@Slf4j
@Component
public class Bootstrap implements CommandLineRunner {

    private CategoryRepository categoryRepository;

    private CustomerRepository customerRepository;

    private VendorRepository vendorRepository;

    public Bootstrap(CategoryRepository categoryRepository,
                     CustomerRepository customerRepository,
                     VendorRepository vendorRepository) {
        this.categoryRepository = categoryRepository;
        this.customerRepository = customerRepository;
        this.vendorRepository = vendorRepository;
    }

    /**
     * CommandLineRunner - Spring boot specific interface
     *
     * @param args
     * @throws Exception
     */
    @Override
    public void run(String... args) throws Exception {
        createCategories();
        createCustomers();
        createVendors();
    }

    private void createVendors() {
        Vendor vendor1 = new Vendor("Western Tasty Fruits Ltd.");
        Vendor vendor2 = new Vendor("Exotic Fruits Company");
        Vendor vendor3 = new Vendor("Home Fruits");
        Vendor vendor4 = new Vendor("Fun Fresh Fruits Ltd.");
        Vendor vendor5 = new Vendor("Nuts for Nuts Company");
        Vendor vendor6 = new Vendor("JUMO");
        vendorRepository.saveAll(Arrays.asList(
                vendor1,
                vendor2,
                vendor3,
                vendor4,
                vendor5,
                vendor6
                ));
        log.info("Vendors loaded on bootstrap: " + vendorRepository.count());
    }

    private void createCustomers() {
        Customer customer1 = new Customer("Michael", "Lachappele");
        Customer customer2 = new Customer("David", "Winter");
        Customer customer3 = new Customer("Anne", "Hine");
        Customer customer4 = new Customer("Alice", "Eastman");
        Customer customer5 = new Customer("Freddy", "Meyers");
        Customer customer6 = new Customer("Albert", "Einstein");
        Customer customer7 = new Customer("Joe", "Buck");
        customerRepository.saveAll(Arrays.asList(
                customer1,
                customer2,
                customer3,
                customer4,
                customer5,
                customer6,
                customer7
        ));
        log.info("Customers loaded on bootstrap: " + customerRepository.count());
    }


    private void createCategories() {
        Category fruits = new Category();
        fruits.setName("Fruits");
        categoryRepository.save(fruits);

        Category dried = new Category();
        dried.setName("Dried");
        categoryRepository.save(dried);

        Category fresh = new Category();
        fresh.setName("Fresh");
        categoryRepository.save(fresh);

        Category exotic = new Category();
        exotic.setName("Exotic");
        categoryRepository.save(exotic);

        Category nuts = new Category();
        nuts.setName("Nuts");
        categoryRepository.save(nuts);

        log.info("Categories loaded on bootstrap: " + categoryRepository.count());

    }
}
