package com.harshit.spring_blog.security;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtTokenHelper {

  private static final String JWT_SECRET = "5367566B59703373367639792F423F4528482B4D6251655468576D5A7134737";
  // private static final String JWT_SECRET = "jwtTokenKey";
  private static final long JWT_TOKEN_VALIDITY = 5 * 60 * 60; // 5 hrs

  public String getUsernameFromToken(String token) {
    return getClaimsFromToken(token, Claims::getSubject);
  }

  public Date getExpirationDateFromToken(String token) {
    return getClaimsFromToken(token, Claims::getExpiration);
  }

  public <T> T getClaimsFromToken(String token, Function<Claims, T> claimsResolver) {
    final Claims claims = getAllClaimsFromToken(token);
    return claimsResolver.apply(claims);
  }

  private Claims getAllClaimsFromToken(String token) {
    return Jwts
        .parserBuilder()
        .setSigningKey(getSignKey())
        .build()
        .parseClaimsJws(token)
        .getBody();
  }

  public boolean isTokenExpired(String token) {
    Date expirationDate = getExpirationDateFromToken(token);
    return expirationDate.before(new Date());
  }

  public boolean validateToken(String token, UserDetails userDetails) {
    final String username = getUsernameFromToken(token);
    return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
  }

  public String generateToken(String username) {
    Map<String, Object> claims = new HashMap<>();
    return createToken(claims, username);
  }

  public String createToken(Map<String, Object> claims, String username) {
    return Jwts.builder()
        .setClaims(claims)
        .setSubject(username)
        .setIssuedAt(new Date(System.currentTimeMillis()))
        .setExpiration(new Date(System.currentTimeMillis() + 1000 * JWT_TOKEN_VALIDITY))
        .signWith(getSignKey(), SignatureAlgorithm.HS256).compact();
    // .signWith(getSignKey(), SignatureAlgorithm.HS512).compact();
  }

  private Key getSignKey() {
    byte[] keyBytes = Decoders.BASE64.decode(JWT_SECRET);
    return Keys.hmacShaKeyFor(keyBytes);
  }
}
