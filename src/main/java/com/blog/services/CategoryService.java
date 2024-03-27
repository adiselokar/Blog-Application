package com.blog.services;

import com.blog.payloads.CategoryDto;

import java.util.List;

public interface CategoryService {

    CategoryDto createCategory(CategoryDto categoryDto);
    List <CategoryDto> getAllCategories();
    CategoryDto getCategoryById(Long categoryId);
    CategoryDto updateCategory(Long categoryId, CategoryDto categoryDto);
    void deleteCategory(Long categoryId);

}
