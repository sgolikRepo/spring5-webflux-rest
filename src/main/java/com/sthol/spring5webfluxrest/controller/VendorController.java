package com.sthol.spring5webfluxrest.controller;

import com.sthol.spring5webfluxrest.domain.Vendor;
import com.sthol.spring5webfluxrest.repository.VendorRepository;
import org.reactivestreams.Publisher;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v01/vendors")
public class VendorController {
    private final VendorRepository vendorRepository;


    public VendorController(VendorRepository vendorRepository) {
        this.vendorRepository = vendorRepository;
    }

    @GetMapping
    public Flux<Vendor> list() {
        return vendorRepository.findAll();
    }

    @GetMapping("/{id}")
    public Mono<Vendor> getById(@PathVariable String id) {
        return vendorRepository.findById(id);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public Mono<Void> create(@RequestBody Publisher<Vendor> publisher) {
        return vendorRepository.saveAll(publisher).then();
    }

    @PutMapping("/{id}")
    public Mono<Vendor> update(@PathVariable String id, @RequestBody Vendor vendor) {
        vendor.setId(id);
        return vendorRepository.save(vendor);
    }

    @PatchMapping("/{id}")
    public Mono<Vendor> patch(@PathVariable String id, @RequestBody Vendor vendor) {
        Vendor foundVendor = vendorRepository.findById(id).block();
        Mono<Vendor> returnVendor = Mono.just(foundVendor);

        if (!foundVendor.getFirstName().equals(vendor.getFirstName())) {
            foundVendor.setFirstName(vendor.getFirstName());
        }

        if (!foundVendor.getLastName().equals(vendor.getLastName())) {
            foundVendor.setLastName(vendor.getLastName());
        }

        returnVendor = vendorRepository.save(foundVendor);

        return returnVendor;
    }
}
