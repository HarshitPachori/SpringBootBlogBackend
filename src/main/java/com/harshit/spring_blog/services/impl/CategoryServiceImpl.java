package com.harshit.spring_blog.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.harshit.spring_blog.exceptions.ResourceNotFoundException;
import com.harshit.spring_blog.models.Category;
import com.harshit.spring_blog.payloads.CategoryDto;
import com.harshit.spring_blog.repositories.CategoryRepository;
import com.harshit.spring_blog.services.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService {

  @Autowired
  private CategoryRepository categoryRepository;

  @Autowired
  private ModelMapper modelmapper;

  @Override
  public CategoryDto createCategory(CategoryDto categoryDto) {
    Category category = modelmapper.map(categoryDto, Category.class);
    Category savedCategory = categoryRepository.save(category);
    return modelmapper.map(savedCategory, CategoryDto.class);
  }

  @Override
  public CategoryDto updateCategory(CategoryDto categoryDto, Integer categoryId) {
    Category category = categoryRepository.findById(categoryId)
        .orElseThrow(() -> new ResourceNotFoundException("Category", "categoryId", categoryId));
    category.setCategoryTitle(categoryDto.getCategoryTitle());
    category.setCategoryDescription(categoryDto.getCategoryDescription());
    Category updatedCategory = categoryRepository.save(category);
    return modelmapper.map(updatedCategory, CategoryDto.class);
  }

  public CategoryDto getCategory(Integer categoryId) {
    Category category = categoryRepository.findById(categoryId)
        .orElseThrow(() -> new ResourceNotFoundException("Category", "categoryId", categoryId));
    return modelmapper.map(category, CategoryDto.class);
  }

  @Override
  public List<CategoryDto> getAllCategories() {
    List<Category> categories = categoryRepository.findAll();
    List<CategoryDto> categoryDtos = categories.stream().map(category -> modelmapper.map(category, CategoryDto.class))
        .collect(Collectors.toList());
    return categoryDtos;
  }

  @Override
  public void deleteCategory(Integer categoryId) {
    Category category = categoryRepository.findById(categoryId)
        .orElseThrow(() -> new ResourceNotFoundException("Category", "categoryId", categoryId));
    categoryRepository.delete(category);

  }

}
