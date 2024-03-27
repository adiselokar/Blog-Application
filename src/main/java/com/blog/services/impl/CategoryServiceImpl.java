package com.blog.services.impl;

import com.blog.entities.Category;
import com.blog.exceptions.ResourseNotFoundException;
import com.blog.payloads.CategoryDto;
import com.blog.repositories.CategoryRepository;
import com.blog.services.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public CategoryDto createCategory( CategoryDto categoryDto ) {
        Category category = modelMapper.map(categoryDto, Category.class);
        Category savedCategory = categoryRepository.save(category);
        return modelMapper.map(savedCategory, CategoryDto.class);
    }

    @Override
    public List <CategoryDto> getAllCategories() {
        List <Category> categories = categoryRepository.findAll();
        List <CategoryDto> categoryDtos = categories.stream()
                .map(category -> modelMapper.map(category, CategoryDto.class))
                .collect(Collectors.toList());
        return categoryDtos;
    }

    @Override
    public CategoryDto getCategoryById( Long categoryId ) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new ResourseNotFoundException("Category Not Found with Id : " + categoryId, HttpStatus.NOT_FOUND));
        return modelMapper.map(category, CategoryDto.class);
    }

    @Override
    public CategoryDto updateCategory( Long categoryId, CategoryDto categoryDto ) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new ResourseNotFoundException("Category Not Found with Id : " + categoryId, HttpStatus.NOT_FOUND));
        category.setTitle(categoryDto.getTitle());
        category.setDescription(categoryDto.getDescription());
        Category updatedCategory = categoryRepository.save(category);
        return modelMapper.map(updatedCategory, CategoryDto.class);
    }

    @Override
    public void deleteCategory( Long categoryId ) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new ResourseNotFoundException("Category Not Found with Id : " + categoryId, HttpStatus.NOT_FOUND));
        categoryRepository.delete(category);
    }
}
