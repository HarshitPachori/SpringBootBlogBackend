package com.harshit.spring_blog.payloads;

import java.util.HashSet;
import java.util.Set;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserDto {

  // to enable these validations we have to use @ valid annotation in the
  // controller

  private int id;

  @NotEmpty(message = "this field can't be empty")
  @Size(min = 4, message = "Username must be minimum of 4 characters")
  private String name;

  @Email(regexp = "[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,3}", flags = Pattern.Flag.CASE_INSENSITIVE, message = "Email address is not valid")
  private String email;

  @NotEmpty(message = "this field can't be empty")
  @Size(min = 8, message = "password must be of atleast 8 characters")
  @Pattern(regexp = ".*[A-Z].*", message = "Password must contain at least one uppercase letter")
  @Pattern(regexp = ".*[@#$%^&+=].*", message = "Password must contain at least one special character (@#$%^&+=)")
  private String password;

  @NotEmpty(message = "this field can't be empty")
  private String about;

  private Set<RolesDto> roles = new HashSet<>();


}

// data transfer ke liye payload use karenge jisse ham database ki entities i.e
// user model ise directly expose nahi karege