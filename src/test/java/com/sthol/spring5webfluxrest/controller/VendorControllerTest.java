package com.sthol.spring5webfluxrest.controller;

import com.sthol.spring5webfluxrest.domain.Vendor;
import com.sthol.spring5webfluxrest.repository.VendorRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.junit.jupiter.api.Assertions.*;

class VendorControllerTest {
    public static final String API_V_01_VENDORS = "/api/v01/vendors";
    VendorController vendorController;
    VendorRepository vendorRepository;
    WebTestClient webTestClient;

    Vendor vendor1;
    Vendor vendor2;

    @BeforeEach
    void setUp() {
        vendor1 = new Vendor();
        vendor1.setLastName("LastName1");
        vendor1.setFirstName("FirstName1");

        vendor2 = new Vendor();
        vendor2.setLastName("LastName2");
        vendor2.setFirstName("FirstName2");

        vendorRepository = Mockito.mock(VendorRepository.class);
        vendorController = new VendorController(vendorRepository);

        webTestClient = WebTestClient.bindToController(vendorController).build();
    }

    @Test
    void list() {
        BDDMockito.given(vendorRepository.findAll()).willReturn(Flux.just(vendor1,vendor2));

        webTestClient.get().uri(API_V_01_VENDORS)
                .exchange()
                .expectBodyList(Vendor.class)
                .hasSize(2);
    }

    @Test
    void getById() {
        BDDMockito.given(vendorRepository.findById("someid")).willReturn(Mono.just(vendor1));

        webTestClient.get().uri(API_V_01_VENDORS + "/someid")
                .exchange()
                .expectBody(Vendor.class);
    }
}