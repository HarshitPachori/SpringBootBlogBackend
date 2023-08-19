package com.harshit.spring_blog.repositories;


import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.harshit.spring_blog.models.Category;
import com.harshit.spring_blog.models.Post;
import com.harshit.spring_blog.models.User;

public interface PostRepository extends JpaRepository<Post, Integer> {

  // custom binder methods
  Page<Post> findByUser(User user,Pageable p );

  Page<Post> findByCategory(Category category,Pageable p );

  List<Post> findByTitleContaining(String title); 
}
