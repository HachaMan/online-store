package com.hachaman.product.service.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

public record UpdateStockRequest(
        @NotNull(message = "Quantity is required")
        Long quantity
) {
}
