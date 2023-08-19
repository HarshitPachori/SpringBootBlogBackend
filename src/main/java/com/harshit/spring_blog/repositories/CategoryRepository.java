package com.harshit.spring_blog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.harshit.spring_blog.models.Category;

public interface CategoryRepository extends JpaRepository<Category,Integer>{
  
}
