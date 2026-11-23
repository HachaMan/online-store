package com.hachaman.product.service.response;

import com.hachaman.product.entity.Category;

public record CategoryResponse(Long id,
                               String name) {

    public CategoryResponse(Category category){
        this(
                category.getId(),
                category.getName()
        );
    }

}
