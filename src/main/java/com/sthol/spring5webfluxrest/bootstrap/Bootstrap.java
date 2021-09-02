package com.sthol.spring5webfluxrest.bootstrap;

import com.sthol.spring5webfluxrest.domain.Category;
import com.sthol.spring5webfluxrest.domain.Vendor;
import com.sthol.spring5webfluxrest.repository.CategoryRepository;
import com.sthol.spring5webfluxrest.repository.VendorRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class Bootstrap implements CommandLineRunner {
    private final CategoryRepository categoryRepository;
    private final VendorRepository vendorRepository;

    public Bootstrap(CategoryRepository categoryRepository, VendorRepository vendorRepository) {
        this.categoryRepository = categoryRepository;
        this.vendorRepository = vendorRepository;
    }


    @Override
    public void run(String... args) throws Exception {
        generateCategory();

        generateVendor();
    }

    private void generateVendor() {
        if (vendorRepository.count().block() == 0) {
            Vendor vendor1 = new Vendor();
            vendor1.setFirstName("FirstName1");
            vendor1.setLastName("LastName1");
            vendorRepository.save(vendor1).block();

            Vendor vendor2 = new Vendor();
            vendor2.setFirstName("FirstName2");
            vendor2.setLastName("LastName2");
            vendorRepository.save(vendor2).block();

            Vendor vendor3 = new Vendor();
            vendor3.setFirstName("FirstName3");
            vendor3.setLastName("LastName3");
            vendorRepository.save(vendor3).block();

            System.out.println("Vendors were created :" + vendorRepository.count().block());
        }
    }

    private void generateCategory() {
        if (categoryRepository.count().block() == 0) {
            Category category1 = new Category();
            category1.setDescription("Category1");
            categoryRepository.save(category1).block();

            Category category2 = new Category();
            category2.setDescription("Category2");
            categoryRepository.save(category2).block();

            Category category3 = new Category();
            category3.setDescription("Category3");
            categoryRepository.save(category3).block();

            System.out.println("Categories were created :" + categoryRepository.count().block());
        }
    }
}
