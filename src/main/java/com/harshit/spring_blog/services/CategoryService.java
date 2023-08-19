package com.harshit.spring_blog.services;

import java.util.List;

import com.harshit.spring_blog.payloads.CategoryDto;

public interface CategoryService {
  CategoryDto createCategory(CategoryDto categoryDto);

  CategoryDto updateCategory(CategoryDto categoryDto, Integer categoryId);

  CategoryDto getCategory(Integer categoryId);

  List<CategoryDto> getAllCategories();

  void deleteCategory(Integer categoryId);

}
