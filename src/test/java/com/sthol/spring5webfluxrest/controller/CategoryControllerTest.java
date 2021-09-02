package com.sthol.spring5webfluxrest.controller;

import com.sthol.spring5webfluxrest.domain.Category;
import com.sthol.spring5webfluxrest.repository.CategoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.reactivestreams.Publisher;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;


class CategoryControllerTest {
    public static final String API_V_01_CATEGORIES = "/api/v01/categories";
    WebTestClient webTestClient;
    CategoryController categoryController;
    CategoryRepository categoryRepository;

    Category category1;
    Category category2;

    @BeforeEach
    void setUp() {
        categoryRepository = Mockito.mock(CategoryRepository.class);
        categoryController = new CategoryController(categoryRepository);
        webTestClient = WebTestClient.bindToController(categoryController).build();

        category1 = new Category();
        category1.setDescription("Category1");

        category2 = new Category();
        category2.setDescription("Category2");
    }

    @Test
    void list() {

        BDDMockito.given(categoryRepository.findAll())
                .willReturn(Flux.just(category1,category2));

        webTestClient.get().uri(API_V_01_CATEGORIES)
                .exchange()
                .expectBodyList(Category.class)
                .hasSize(2);
    }

    @Test
    void getById() {
        BDDMockito.given(categoryRepository.findById("someId"))
                .willReturn(Mono.just(category1));


        webTestClient.get().uri(API_V_01_CATEGORIES + "/someId")
                .exchange()
                .expectBody(Category.class);
    }

    @Test
    void testCreateCategory() {
       BDDMockito.given(categoryRepository.saveAll(any(Publisher.class)))
               .willReturn(Flux.just(category1));

       Mono<Category> customerMono = Mono.just(category1);
       webTestClient.post().uri(API_V_01_CATEGORIES)
               .body(customerMono, Category.class)
               .exchange()
               .expectStatus()
               .isCreated();
    }

    @Test
    void testUpdate() {
        BDDMockito.given(categoryRepository.save(any(Category.class)))
                .willReturn(Mono.just(category1));

        Mono<Category> updateMono = Mono.just(category1);

        webTestClient.put().uri(API_V_01_CATEGORIES+"/abcdefg")
                .body(updateMono,Category.class)
                .exchange()
                .expectStatus()
                .isOk();
    }

    @Test
    void testPatchWithChanges() {
        BDDMockito.given(categoryRepository.findById(anyString()))
                .willReturn(Mono.just(category2));

        BDDMockito.given(categoryRepository.save(any(Category.class)))
                .willReturn(Mono.just(category1));

        Mono<Category> updateMono = Mono.just(category1);

        webTestClient.patch().uri(API_V_01_CATEGORIES+"/abcdefg")
                .body(updateMono,Category.class)
                .exchange()
                .expectStatus()
                .isOk();

        BDDMockito.verify(categoryRepository).save(any());
    }

    @Test
    void testPatchWithNoChanges() {
        BDDMockito.given(categoryRepository.findById(anyString()))
                .willReturn(Mono.just(category1));

        BDDMockito.given(categoryRepository.save(any(Category.class)))
                .willReturn(Mono.just(category1));

        Mono<Category> updateMono = Mono.just(category1);

        webTestClient.patch().uri(API_V_01_CATEGORIES+"/abcdefg")
                .body(updateMono,Category.class)
                .exchange()
                .expectStatus()
                .isOk();
    }
}