package com.harshit.spring_blog.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.harshit.spring_blog.models.RefreshToken;
import com.harshit.spring_blog.models.User;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Integer> {

  Optional<RefreshToken> findByRefreshToken(String refreshToken);
  Optional<RefreshToken> findRefreshTokenByUser(User user);
}
