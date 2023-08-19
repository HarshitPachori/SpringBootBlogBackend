package com.harshit.spring_blog.payloads;

import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PostResponse {

  private List<PostDto> content;

  private int pageNumber;

  private int pageSize;

  private long totalElements; // total records

  private int totalPages;

  private boolean isLastPage;
}
