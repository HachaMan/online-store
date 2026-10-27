package com.hachaman.product.service;

import com.hachaman.product.entity.Category;
import com.hachaman.product.entity.Product;
import com.hachaman.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService{

    private final ProductRepository productRepository;

    @Override
    public List<Product> listAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public Product getProduct(Long id) {
        return productRepository.findById(id).orElse(null);
    }

    @Override
    public Product createProduct(Product product) {

        product.setStatus("CREATED");
        product.setCreatedAt(new Date());
        return productRepository.save(product);

    }

    @Override
    public Product updateProduct(Product product) {

        Product productDB = getProduct(product.getId());

        if (Objects.nonNull(productDB)) {

            productDB.setName(product.getName());
            productDB.setDescription(product.getDescription());
            productDB.setCategory(product.getCategory());
            productDB.setPrice(product.getPrice());

            return productRepository.save(productDB);

        } else
            return null;
    }

    @Override
    public Product deleteProduct(Long id) {

        Product productDB = getProduct(id);

        if (Objects.nonNull(productDB)) {

            productDB.setStatus("DELETED");
            return productRepository.save(productDB);

        } else
            return null;
    }

    @Override
    public List<Product> findByCategory(Category category) {
        return productRepository.findByCategory(category);
    }

    @Override
    public Product updateStock(Long id, Double quantity) {

        Product productDB = getProduct(id);

        if (Objects.nonNull(productDB)) {

            Double stock = productDB.getStock() + quantity;
            productDB.setStock(stock);

            return productRepository.save(productDB);

        } else
            return null;

    }
}
