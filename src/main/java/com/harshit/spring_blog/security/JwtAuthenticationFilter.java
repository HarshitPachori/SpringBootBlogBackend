package com.harshit.spring_blog.security;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

  @Autowired
  private UserDetailsService userDetailsService;

  @Autowired
  private JwtTokenHelper jwtTokenHelper;

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {

    /*
     * 1. get token from header
     * Token -> Bearer 15544578787fsh78f7hgf7fg857nhjgf74hgf.fhgfh.fhgfnj
     */

    String requestToken = request.getHeader("Authorization");
    System.out.println(request);
    String token = null;
    String username = null;
    if (requestToken != null && requestToken.startsWith("Bearer ")) {
      // remove bearer word and space after it
      token = requestToken.substring(7);
      try {
        username = jwtTokenHelper.getUsernameFromToken(token);
      } catch (IllegalArgumentException e) {
        System.out.println("Unable to get Jwt Token");
      } catch (ExpiredJwtException e) {
        System.out.println("Jwt Token is expired ");
      } catch (MalformedJwtException e) {
        System.out.println("Invalid Jwt Token ");
      }
    } else {
      System.out.println("Jwt token does not begin with Bearer");
    }

    // once we get the token now we validate it
    if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
      UserDetails userDetails = userDetailsService.loadUserByUsername(username);
      if (jwtTokenHelper.validateToken(token, userDetails)) {
        // authentication
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails,
            null, userDetails.getAuthorities());
        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);

      } else {
        System.out.println("Invalid Jwt Token");
      }
    } else {
      System.out.println("Username is null or context is not null");
    }

    filterChain.doFilter(request, response);
  }

}
