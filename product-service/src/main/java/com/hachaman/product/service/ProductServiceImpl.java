package com.hachaman.product.service;

import com.hachaman.product.entity.Category;
import com.hachaman.product.entity.Product;
import com.hachaman.product.entity.ProductStatus;
import com.hachaman.product.repository.CategoryRepository;
import com.hachaman.product.repository.ProductRepository;
import com.hachaman.product.service.request.ProductRequest;
import com.hachaman.product.service.request.UpdateProductRequest;
import com.hachaman.product.service.request.UpdateStockRequest;
import com.hachaman.product.service.response.ProductResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService{

    private final ProductRepository productRepository;

    private final CategoryRepository categoryRepository;

    @Override
    @Transactional(readOnly = true)
    public List<ProductResponse> listAllProducts() {

        return productRepository.findAll()
                .stream()
                .map(ProductResponse::new)
                .toList();

    }

    @Override
    @Transactional(readOnly = true)
    public ProductResponse getProduct(Long id) {
        return productRepository.findById(id)
                .map(ProductResponse::new)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found"));
    }

    @Override
    @Transactional
    public ProductResponse createProduct(ProductRequest productRequest) {

        Category category = categoryRepository.findById(productRequest.categoryId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Category not found"));

        Product product = new Product();

        product.setName(productRequest.name());
        product.setDescription(productRequest.description());
        product.setStock(productRequest.stock());
        product.setPrice(productRequest.price());
        product.setCategory(category);
        product.setStatus(ProductStatus.CREATED);
        product.setCreatedAt(Instant.now());

        Product saved = productRepository.save(product);

        return new ProductResponse(saved);

    }

    @Override
    @Transactional
    public ProductResponse updateProduct(Long id, UpdateProductRequest updateProductRequest) {

        Category category = categoryRepository.findById(updateProductRequest.categoryId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Category not found"));

        Product updateProduct = productRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Product not found"));

        updateProduct.setName(updateProductRequest.name());
        updateProduct.setDescription(updateProductRequest.description());
        updateProduct.setPrice(updateProductRequest.price());
        updateProduct.setCategory(category);

        return new ProductResponse(updateProduct);

    }

    @Override
    @Transactional
    public void deleteProduct(Long id) {

        Product deleteProduct = productRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found"));

        deleteProduct.setStatus(ProductStatus.DELETED);

    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductResponse> findByCategoryId(Long categoryId) {

        if (!categoryRepository.existsById(categoryId))
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Category not found");

        return productRepository.findByCategoryId(categoryId)
                .stream()
                .map(ProductResponse::new)
                .toList();
    }

    @Override
    @Transactional
    public ProductResponse updateStock(Long id, UpdateStockRequest updateStock) {

        Product updateProduct = productRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found"));

        long newStock = updateProduct.getStock() + updateStock.quantity();

        if (newStock < 0)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Insufficient stock: available " + updateProduct.getStock());

        updateProduct.setStock(newStock);

        return new ProductResponse(updateProduct);

    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductResponse> findByCategoryName(String categoryName) {

        if (!categoryRepository.existsByName(categoryName))
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Category not found");

        return productRepository.findByCategoryName(categoryName)
                .stream()
                .map(ProductResponse::new)
                .toList();
    }
}
