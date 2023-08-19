package com.harshit.spring_blog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.harshit.spring_blog.models.Comment;

public interface CommentRepository extends JpaRepository<Comment, Integer> {

}
