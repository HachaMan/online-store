package com.hachaman.product.service.response;

import com.hachaman.product.entity.Product;
import com.hachaman.product.entity.ProductStatus;

import java.math.BigDecimal;

public record ProductResponse(Long id,
                              String name,
                              String description,
                              Long stock,
                              BigDecimal price,
                              ProductStatus status,
                              Long version,
                              CategoryResponse category) {

    public ProductResponse(Product product) {
        this(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getStock(),
                product.getPrice(),
                product.getStatus(),
                product.getVersion(),
                product.getCategory() != null ? new CategoryResponse(product.getCategory()) : null
        );
    }
}
