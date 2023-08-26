package com.harshit.spring_blog.services;

import java.time.Instant;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.harshit.spring_blog.exceptions.ResourceNotFoundException;
import com.harshit.spring_blog.models.RefreshToken;
import com.harshit.spring_blog.models.User;
import com.harshit.spring_blog.repositories.RefreshTokenRepository;
import com.harshit.spring_blog.repositories.UserRepository;
import com.harshit.spring_blog.security.JwtTokenHelper;
import com.harshit.spring_blog.utils.AppConstants;

@Service
public class RefreshTokenService {

  @Autowired
  private RefreshTokenRepository refreshTokenRepository;

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private JwtTokenHelper jwtTokenHelper;

  public RefreshToken createRefreshToken(String username) {
    User user = userRepository.findFirstByEmail(username)
        .orElseThrow(() -> new ResourceNotFoundException("username", "username", username));
    // RefreshToken refreshToken =
    // refreshTokenRepository.findRefreshTokenByUser(user).get();
    RefreshToken refreshToken = user.getRefreshToken();
    if (refreshToken == null) {
      refreshToken = RefreshToken
          .builder()
          .refreshToken(UUID.randomUUID().toString())
          .expiry(Instant.now().plusMillis(AppConstants.REFRESH_TOKEN_VALIDITY * 1000))
          .user(user)
          .build();
    } else {
      refreshToken.setExpiry(Instant.now().plusMillis(AppConstants.REFRESH_TOKEN_VALIDITY * 1000));
    }

    RefreshToken savedRefreshToken = refreshTokenRepository.save(refreshToken);
    return savedRefreshToken;
  }

  public boolean verifyRefreshToken(RefreshToken refreshToken) {
    if (refreshToken.getExpiry().compareTo(Instant.now()) < 0) {
      refreshTokenRepository.delete(refreshToken);
      return false;
    } else {
      return true;
    }
  }

  public String createJwtTokenFromRefreshToken(RefreshToken refreshToken) {
    RefreshToken refreshToken1 = refreshTokenRepository.findByRefreshToken(refreshToken.getRefreshToken()).orElseThrow(
        () -> new ResourceNotFoundException("RefreshToken", "RefreshToken", refreshToken.getRefreshToken()));
    String username = refreshToken1.getUser().getUsername();
    String jwtToken = jwtTokenHelper.generateToken(username);
    return jwtToken;
  }
}
