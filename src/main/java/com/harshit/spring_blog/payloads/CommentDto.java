package com.harshit.spring_blog.payloads;

import lombok.Data;

@Data
public class CommentDto {
  private Integer id;
  private String content;
}
