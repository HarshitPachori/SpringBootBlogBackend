package com.harshit.spring_blog.payloads;

import lombok.Data;

@Data
public class JwtAuthResponse {

  private String token;
  private String refreshToken;
}
