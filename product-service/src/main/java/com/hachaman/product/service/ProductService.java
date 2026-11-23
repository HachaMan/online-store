package com.hachaman.product.service;

import com.hachaman.product.service.request.ProductRequest;
import com.hachaman.product.service.request.UpdateProductRequest;
import com.hachaman.product.service.request.UpdateStockRequest;
import com.hachaman.product.service.response.ProductResponse;

import java.util.List;

public interface ProductService {

    List<ProductResponse> listAllProducts();

    ProductResponse getProduct(Long id);

    ProductResponse createProduct(ProductRequest productRequest);

    ProductResponse updateProduct(Long id, UpdateProductRequest updateProductRequest);

    void deleteProduct(Long id);

    List<ProductResponse> findByCategoryId(Long categoryId);

    ProductResponse updateStock(Long id, UpdateStockRequest updateStock);

    List<ProductResponse> findByCategoryName(String categoryName);

}
