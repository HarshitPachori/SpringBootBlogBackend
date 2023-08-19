package com.harshit.spring_blog.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.harshit.spring_blog.models.User;

public interface UserRepository extends JpaRepository<User, Integer> {

  Optional<User> findFirstByEmail(String email);
}
