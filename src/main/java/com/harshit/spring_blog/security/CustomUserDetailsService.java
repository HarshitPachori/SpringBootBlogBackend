package com.harshit.spring_blog.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.harshit.spring_blog.exceptions.ResourceNotFoundException;
import com.harshit.spring_blog.models.User;
import com.harshit.spring_blog.repositories.UserRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {

  @Autowired
  private UserRepository userRepository;

  @Override
  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    // loading user from database using username
    User user = userRepository.findFirstByEmail(email)
        .orElseThrow(() -> new ResourceNotFoundException("User", "email", email));
    return user;
  }

}
