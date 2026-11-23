package com.hachaman.product;

import com.hachaman.product.entity.Category;
import com.hachaman.product.entity.Product;
import com.hachaman.product.repository.CategoryRepository;
import com.hachaman.product.repository.ProductRepository;
import com.hachaman.product.service.ProductService;
import com.hachaman.product.service.ProductServiceImpl;
import com.hachaman.product.service.request.UpdateStockRequest;
import com.hachaman.product.service.response.ProductResponse;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.Optional;

public class ProductServiceMockTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private CategoryRepository categoryRepository;

    private ProductService productService;

    @BeforeEach
    public void setup() {

        MockitoAnnotations.openMocks(this);

        productService = new ProductServiceImpl(productRepository, categoryRepository);

        Category category = Category.builder().id(1L).build();
        String productName = "computer";
        BigDecimal price = BigDecimal.valueOf(12.5);
        Long quantity = 5L;

        Product computer = Product.builder()
                .id(1L)
                .name(productName)
                .category(category)
                .price(price)
                .stock(quantity)
                .build();

        Mockito.when(productRepository.findById(1L))
                .thenReturn(Optional.of(computer));

        Mockito.when(productRepository.save(computer))
                .thenReturn(computer);

    }

    @Test
    public void whenValidGetId_ThenReturnProduct() {

        Long id = 1L;

        ProductResponse found = productService.getProduct(id);

        Assertions.assertThat(found.name()).isEqualTo("computer");

    }

    @Test
    public void whenValidUpdateStock_ThenReturnNewStock() {

        Long id = 1L;
        Long quantity = 8L;
        Long expectedQuantity = 13L;

        UpdateStockRequest updateStock = new UpdateStockRequest(quantity);

        ProductResponse newStock = productService.updateStock(id, updateStock);

        Assertions.assertThat(newStock.stock()).isEqualTo(expectedQuantity);

    }


}
