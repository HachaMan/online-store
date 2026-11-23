package com.hachaman.product.service.request;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public record ProductRequest(
        @NotBlank
        @Size(max = 250, message = "Name must be at most 250 characters")
        String name,

        String description,

        @NotNull(message = "Stock is required")
        @PositiveOrZero(message = "Stock cannot be negative")
        Long stock,

        @NotNull(message = "Price is required")
        @Positive(message = "Price must be grater than zero")
        BigDecimal price,

        @NotNull(message = "Category id is required")
        Long categoryId
) {
}
