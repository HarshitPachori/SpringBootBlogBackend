package com.harshit.spring_blog.payloads;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CategoryDto {

  private Integer categoryId;

  @NotEmpty(message = "this field can't be empty")
  @Size(min = 4, message = "min size of category title is 4")
  private String categoryTitle;

  @NotEmpty(message = "this field can't be empty")
  @Size(min = 10, message = "min size of category description is 10")
  private String categoryDescription;
}
