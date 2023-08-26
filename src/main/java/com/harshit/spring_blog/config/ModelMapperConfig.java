package com.harshit.spring_blog.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {
  
  	// created model mapper here bcz this class already has springbootapplication
	// annotation so we dont need to make a configuration file
	// we used bean annotation here so spring container will automatically create
	// its object at runtime where we do autowire for this model mapper class

  @Bean
  public ModelMapper modelMapper(){
    return new ModelMapper();
  }
}
