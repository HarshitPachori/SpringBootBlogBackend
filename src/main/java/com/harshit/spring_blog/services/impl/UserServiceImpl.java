package com.harshit.spring_blog.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.harshit.spring_blog.exceptions.ResourceNotFoundException;
import com.harshit.spring_blog.models.Roles;
import com.harshit.spring_blog.models.User;
import com.harshit.spring_blog.payloads.UserDto;
import com.harshit.spring_blog.repositories.RoleRepository;
import com.harshit.spring_blog.repositories.UserRepository;
import com.harshit.spring_blog.services.UserService;
import com.harshit.spring_blog.utils.AppConstants;

@Service
public class UserServiceImpl implements UserService {

  @Autowired
  private UserRepository userRepository;
  @Autowired
  private RoleRepository roleRepository;

  @Autowired
  private ModelMapper modelMapper;

  @Autowired
  private PasswordEncoder passwordEncoder;

  @Override
  public UserDto createUser(UserDto userDto) {
    User user = dtoToUser(userDto);
    User savedUser = userRepository.save(user);
    return userToDto(savedUser);
  }

  @Override
  public UserDto updateUser(UserDto userDto, Integer userId) {
    User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));

    user.setName(userDto.getName());
    user.setEmail(userDto.getEmail());
    user.setPassword(userDto.getPassword());
    user.setAbout(userDto.getAbout());
    User updatedUser = userRepository.save(user);
    UserDto userDto1 = userToDto(updatedUser);
    return userDto1;
  }

  @Override
  public UserDto getUserById(Integer userId) {
    User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));
    return userToDto(user);
  }

  @Override
  public List<UserDto> getAllUsers() {
    List<User> users = userRepository.findAll();
    List<UserDto> userDtos = users.stream().map(user -> userToDto(user)).collect(Collectors.toList());
    return userDtos;
  }

  @Override
  public void deleteUserByUserId(Integer userId) {
    User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));
    userRepository.delete(user);
  }

  // methods to convert dto to entity and vice versa
  private User dtoToUser(UserDto userDto) {
    User user = modelMapper.map(userDto, User.class);
    // to use model mapper both source and destination should have same fields
    // User user = new User();
    // user.setId(userDto.getId());
    // user.setName(userDto.getName());
    // user.setEmail(userDto.getEmail());
    // user.setPassword(userDto.getPassword());
    // user.setAbout(userDto.getAbout());
    return user;
  }

  private UserDto userToDto(User user) {
    UserDto userDto = this.modelMapper.map(user, UserDto.class);
    // UserDto userDto = new UserDto();
    // userDto.setId(user.getId());
    // userDto.setName(user.getName());
    // userDto.setEmail(user.getEmail());
    // userDto.setPassword(user.getPassword());
    // userDto.setAbout(user.getAbout());
    return userDto;
  }

  @Override
  public UserDto registerUser(UserDto userDto) {
    User user = modelMapper.map(userDto, User.class);
    user.setPassword(passwordEncoder.encode(user.getPassword()));
    Roles roles = roleRepository.findById(AppConstants.NORMAL_USER).get();
    user.getRoles().add(roles);
    User registeredUser = userRepository.save(user);
    return modelMapper.map(registeredUser, UserDto.class);
  }

}
