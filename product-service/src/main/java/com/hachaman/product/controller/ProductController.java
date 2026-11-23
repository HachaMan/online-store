package com.hachaman.product.controller;

import com.hachaman.product.service.ProductService;
import com.hachaman.product.service.request.ProductRequest;
import com.hachaman.product.service.request.UpdateProductRequest;
import com.hachaman.product.service.request.UpdateStockRequest;
import com.hachaman.product.service.response.ProductResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping(value = "/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping
    public ResponseEntity<List<ProductResponse>> listProduct(@RequestParam(name = "categoryId", required = false)
                                                     Long categoryId) {

        List<ProductResponse> products = (Objects.nonNull(categoryId))
                ? productService.findByCategoryId(categoryId)
                : productService.listAllProducts();

        return ResponseEntity.ok(products);

    }

    @GetMapping(value = "/category/{categoryName}")
    public ResponseEntity<List<ProductResponse>> listAllProductsByCategoryName(@PathVariable String categoryName) {

        List<ProductResponse> products = productService.findByCategoryName(categoryName);
        return ResponseEntity.ok(products);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<ProductResponse> getProduct(@PathVariable("id") Long id) {

        ProductResponse product = productService.getProduct(id);
        return ResponseEntity.ok(product);

    }

    @PostMapping
    public ResponseEntity<ProductResponse> createProduct(@Valid @RequestBody ProductRequest product) {

        ProductResponse productCreated = productService.createProduct(product);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(productCreated.id())
                .toUri();

        return ResponseEntity.created(location).body(productCreated);

    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<ProductResponse> updateProduct(@PathVariable("id")  Long id,
                                                         @Valid @RequestBody UpdateProductRequest productRequest) {

        ProductResponse productUpdated = productService.updateProduct(id, productRequest);
        return ResponseEntity.ok(productUpdated);

    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable("id") Long id) {

        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();

    }

    @PatchMapping(value = "/update-stock/{id}")
    public ResponseEntity<ProductResponse> updateStockProduct(@PathVariable Long id,
                                                              @Valid @RequestBody UpdateStockRequest updateStock) {

        ProductResponse product = productService.updateStock(id, updateStock);
        return ResponseEntity.ok(product);

    }

}