package com.harshit.spring_blog.payloads;

import com.harshit.spring_blog.models.RefreshToken;

import lombok.Data;

@Data
public class RefreshTokenRequest {
  private RefreshToken refreshToken;
}
