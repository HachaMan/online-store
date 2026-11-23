package com.hachaman.product.service.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public record UpdateProductRequest(
        @NotBlank
        @Size(max = 250, message = "Name must be at most 250 characters")
        String name,

        String description,

        @NotNull(message = "Price is required")
        @Positive(message = "Price must be grater than zero")
        BigDecimal price,

        @NotNull(message = "Category id is required")
        Long categoryId
) {
}
