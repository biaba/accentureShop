package com.project.backend.repositories;

import com.project.backend.models.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.TestPropertySource;

import java.math.BigDecimal;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
@TestPropertySource("classpath:db-application.properties")
class ProductRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private ProductRepository productRepo;

    @Autowired
    private UserRepository userRepo;

    private Product product;

    @BeforeEach
    public void init() {
        this.product = this.createProduct();
    }

    @Test
    void findProductByCategory() {
        productRepo.save(product);
        assertThat(productRepo.findProductByCategory(Category.ACCESSORIES).size()).isEqualTo(1);
        assertThat(productRepo.findAll().size()).isEqualTo(1);
    }

    @Test
    void findById() {
        assertThat(productRepo.findAll().size()).isEqualTo(3);
    }

    @Test
    void getTopProductsByCategory() {
        assertThat(userRepo.findAll().size()).isEqualTo(2);
    }

    @Test
    void getByPriceAscByCategory() {
    }

    private Product createProduct() {
         Product p = new Product("chewing gum", new BigDecimal(0.2), "dentist's treat",
        33, Category.ACCESSORIES, null, null, null);
        return p;
    }
}