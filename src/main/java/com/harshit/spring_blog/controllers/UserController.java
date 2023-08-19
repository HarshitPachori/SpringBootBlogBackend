package com.harshit.spring_blog.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.harshit.spring_blog.payloads.ApiResponse;
import com.harshit.spring_blog.payloads.UserDto;
import com.harshit.spring_blog.services.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/users")
public class UserController {

  @Autowired
  private UserService userService;

  // POST create user
  @PostMapping("/")
  public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto) {
    UserDto createdUser = userService.createUser(userDto);
    return new ResponseEntity<UserDto>(createdUser, HttpStatus.CREATED);
  }

  // PUT update user
  @PutMapping("/{userId}")
  public ResponseEntity<UserDto> updateUser(@Valid @RequestBody UserDto userDto,
      @PathVariable("userId") Integer userId) {
    UserDto updatedUser = userService.updateUser(userDto, userId);
    return ResponseEntity.ok(updatedUser);
  }

  // DELETE delete user
  @DeleteMapping("/{userId}")
  public ResponseEntity<ApiResponse> deleteUser(@PathVariable("userId") Integer userId) {
    userService.deleteUserByUserId(userId);
    return new ResponseEntity<ApiResponse>(new ApiResponse("User deleted successfully", true), HttpStatus.OK);
  }

  // GET get all users
  @GetMapping("/")
  public ResponseEntity<List<UserDto>> getAllUsers() {
    return ResponseEntity.ok(userService.getAllUsers());
  }

  // GET get single user
  @GetMapping("/{userId}")
  public ResponseEntity<UserDto> getSingleUser(@PathVariable("userId") Integer userId) {
    return ResponseEntity.ok(userService.getUserById(userId));
  }
}
