package com.harshit.spring_blog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.harshit.spring_blog.models.Roles;

public interface RoleRepository extends JpaRepository<Roles, Integer> {

}
