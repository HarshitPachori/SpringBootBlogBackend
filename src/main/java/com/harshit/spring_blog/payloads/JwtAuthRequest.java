package com.harshit.spring_blog.payloads;

import lombok.Data;

@Data
public class JwtAuthRequest {

  private String username;
  private String password;
}
