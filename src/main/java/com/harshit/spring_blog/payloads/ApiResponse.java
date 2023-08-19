package com.harshit.spring_blog.payloads;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ApiResponse {
  private String messsage;
  private boolean isSuccess;
}
