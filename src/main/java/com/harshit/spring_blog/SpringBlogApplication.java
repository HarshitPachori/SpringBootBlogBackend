package com.harshit.spring_blog;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.harshit.spring_blog.models.Roles;
import com.harshit.spring_blog.repositories.RoleRepository;
import com.harshit.spring_blog.utils.AppConstants;

@SpringBootApplication
public class SpringBlogApplication implements CommandLineRunner {

	@Autowired
	private RoleRepository roleRepository;

	public static void main(String[] args) {
		SpringApplication.run(SpringBlogApplication.class, args);
	}

	// created model mapper here bcz this class already has springbootapplication
	// annotation so we dont need to make a configuration file
	// we used bean annotation here so spring container will automatically create
	// its object at runtime where we do autowire for this model mapper class

	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}

	@Override
	public void run(String... args) throws Exception {
		// command line runner
		try {
			Roles role1 = new Roles();
		role1.setRoleId(AppConstants.ADMIN_USER);
		role1.setRoleName("ROLE_ADMIN");
		Roles role2 = new Roles();
		role2.setRoleId(AppConstants.NORMAL_USER);
		role2.setRoleName("ROLE_NORMAL");
		List<Roles> roles = List.of(role1, role2);
		roleRepository.saveAll(roles);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
