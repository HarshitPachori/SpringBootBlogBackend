package com.harshit.spring_blog.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.harshit.spring_blog.payloads.ApiResponse;
import com.harshit.spring_blog.payloads.CategoryDto;
import com.harshit.spring_blog.services.CategoryService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

  @Autowired
  private CategoryService categoryService;

  // POST create category
  @PostMapping("/")
  public ResponseEntity<CategoryDto> createCategory(@Valid @RequestBody CategoryDto categoryDto) {
    CategoryDto createdCategory = categoryService.createCategory(categoryDto);
    return new ResponseEntity<CategoryDto>(createdCategory, HttpStatus.CREATED);
  }

  // PUT update category
  @PutMapping("/{categoryId}")
  public ResponseEntity<CategoryDto> updateCategory(@Valid @RequestBody CategoryDto categoryDto,
      @PathVariable("categoryId") Integer categoryId) {
    CategoryDto updatedCategory = categoryService.updateCategory(categoryDto, categoryId);
    return new ResponseEntity<CategoryDto>(updatedCategory, HttpStatus.OK);
  }

  // DELETE delete category
  @DeleteMapping("/{categoryId}")
  public ResponseEntity<ApiResponse> deleteCategory(@PathVariable("categoryId") Integer categoryId) {
    categoryService.deleteCategory(categoryId);
    return new ResponseEntity<ApiResponse>(new ApiResponse("category is deleted successfully", true), HttpStatus.OK);
  }

  // GET get single category
  @GetMapping("/{categoryId}")
  public ResponseEntity<CategoryDto> getSingleCategory(@PathVariable("categoryId") Integer categoryId) {
    CategoryDto category = categoryService.getCategory(categoryId);
    return new ResponseEntity<CategoryDto>(category, HttpStatus.OK);
  }

  // GET get all categories
  @GetMapping("/")
  public ResponseEntity<List<CategoryDto>> getAllCategories() {
    List<CategoryDto> categories = categoryService.getAllCategories();
    return new ResponseEntity<List<CategoryDto>>(categories, HttpStatus.OK);
  }



}
