package com.harshit.spring_blog.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.harshit.spring_blog.exceptions.ApiException;
import com.harshit.spring_blog.models.RefreshToken;
import com.harshit.spring_blog.payloads.JwtAuthRequest;
import com.harshit.spring_blog.payloads.JwtAuthResponse;
import com.harshit.spring_blog.payloads.RefreshTokenRequest;
import com.harshit.spring_blog.payloads.UserDto;
import com.harshit.spring_blog.security.JwtTokenHelper;
import com.harshit.spring_blog.services.RefreshTokenService;
import com.harshit.spring_blog.services.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

  @Autowired
  private JwtTokenHelper jwtTokenHelper;

  @Autowired
  private UserDetailsService userDetailsService;

  @Autowired
  private AuthenticationManager authenticationManager;

  @Autowired
  private UserService userService;

  @Autowired
  private RefreshTokenService refreshTokenService;

  @PostMapping("/login")
  public ResponseEntity<JwtAuthResponse> createToken(
      @RequestBody JwtAuthRequest request) throws Exception {
    System.out.println("Username: " + request.getUsername() + " Password: " + request.getPassword());
    System.out.println("1");
    authenticate(request.getUsername(), request.getPassword());
    System.out.println("2");

    UserDetails userDetails = userDetailsService.loadUserByUsername(request.getUsername());
    System.out.println("3");

    RefreshToken refreshToken = refreshTokenService.createRefreshToken(userDetails.getUsername());
    String jwtToken = jwtTokenHelper.generateToken(userDetails.getUsername());
    System.out.println("4");

    JwtAuthResponse authResponse = new JwtAuthResponse();
    authResponse.setToken(jwtToken);
    authResponse.setRefreshToken(refreshToken.getRefreshToken());
    return new ResponseEntity<>(authResponse, HttpStatus.OK);
  }

  private void authenticate(String username, String password) throws Exception {
    System.out.println("Username : " + username + " Password: " + password);
    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username,
        password);
    try {
      authenticationManager.authenticate(authenticationToken);
    } catch (BadCredentialsException e) {
      System.out.println("username or password is wrong !");
      throw new ApiException("username or password is wrong !");
    }
  }

  @PostMapping("/register")
  public ResponseEntity<UserDto> registerUser(
      @Valid @RequestBody UserDto userDto) {
    UserDto registeredUser = userService.registerUser(userDto);
    return new ResponseEntity<UserDto>(registeredUser, HttpStatus.CREATED);
  }

  @PostMapping("/refreshToken")
  public ResponseEntity<JwtAuthResponse> refreshToken(
      @RequestBody RefreshToken refreshToken) {
    String jwtToken = refreshTokenService.createJwtTokenFromRefreshToken(refreshToken);
    JwtAuthResponse jwtAuthResponse = new JwtAuthResponse();
    jwtAuthResponse.setToken(jwtToken);
    jwtAuthResponse.setRefreshToken(refreshToken.getRefreshToken());
    return new ResponseEntity<JwtAuthResponse>(jwtAuthResponse, HttpStatus.CREATED);
  }
}