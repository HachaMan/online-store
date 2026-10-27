package com.hachaman.product.repository;

import com.hachaman.product.entity.Category;
import com.hachaman.product.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByCategory(Category category);
}
