package com.harshit.spring_blog.security;

import java.io.IOException;
import java.io.PrintWriter;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/*
 * making this method as component to make it as autowired later in our project
 */

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

  /*
   * the commence method will called when an unauthorized user tries to access the
   * api's
   */

  @Override
  public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException)
      throws IOException, ServletException {

    response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Access Denied !" + authException.getLocalizedMessage());
    PrintWriter writer = response.getWriter();
    writer.println("{\"error\":\"Access Denied !! " + authException.getMessage() + "\"}");
  }

}
