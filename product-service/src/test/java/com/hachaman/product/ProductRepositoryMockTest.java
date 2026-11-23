package com.hachaman.product;

import com.hachaman.product.entity.Category;
import com.hachaman.product.entity.Product;
import com.hachaman.product.entity.ProductStatus;
import com.hachaman.product.repository.ProductRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

@DataJpaTest(properties = {
        "spring.cloud.config.enabled=false",
        "spring.cloud.config.import-check.enabled=false",
        "spring.sql.init.mode=always",
        "spring.jpa.defer-datasource-initialization=true"
})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
public class ProductRepositoryMockTest {

    @Autowired
    private ProductRepository productRepository;

    @Test
    public void whenFindByCategory_thenReturnListProduct() {

        Category shoesCategory = Category.builder().id(1L).build();

        Product product01 = Product.builder()
                .name("Computer")
                .category(shoesCategory)
                .description("")
                .stock(10L)
                .price(BigDecimal.valueOf(1240.99))
                .status(ProductStatus.CREATED)
                .createdAt(Instant.now()).build();

        productRepository.save(product01);

        List<Product> found = productRepository.findByCategoryId(product01.getCategory().getId());

        Assertions.assertThat(found.size()).isEqualTo(3);

    }

}
