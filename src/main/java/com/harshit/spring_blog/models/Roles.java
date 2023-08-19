package com.harshit.spring_blog.models;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class Roles {

  @Id
  private int roleId;

  private String roleName;
}
