package com.hachaman.product;

import com.hachaman.product.entity.Category;
import com.hachaman.product.entity.Product;
import com.hachaman.product.repository.ProductRepository;
import com.hachaman.product.service.ProductService;
import com.hachaman.product.service.ProductServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

public class ProductServiceMockTest {

    @Mock
    private ProductRepository productRepository;

    private ProductService productService;

    @BeforeEach
    public void setup() {

        MockitoAnnotations.openMocks(this);

        productService = new ProductServiceImpl(productRepository);

        Product computer = Product.builder()
                .id(1L)
                .name("computer")
                .category(Category.builder().id(1L).build())
                .price(Double.parseDouble("12.5"))
                .stock(Double.parseDouble("5"))
                .build();

        Mockito.when(productRepository.findById(1L))
                .thenReturn(Optional.of(computer));

        Mockito.when(productRepository.save(computer))
                .thenReturn(computer);

    }

    @Test
    public void whenValidGetId_ThenReturnProduct() {

        Product found = productService.getProduct(1L);

        Assertions.assertThat(found.getName()).isEqualTo("computer");

    }

    @Test
    public void whenValidUpdateStock_ThenReturnNewStock() {

        Product newStock = productService.updateStock(1L, Double.parseDouble("8"));

        Assertions.assertThat(newStock.getStock()).isEqualTo(13);

    }


}
