package com.harshit.spring_blog;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.harshit.spring_blog.repositories.UserRepository;

@SpringBootTest
class SpringBlogApplicationTests {

	@Autowired
	private UserRepository userRepository;

	@Test
	void contextLoads() {
	}

	@Test
	public void repoTest() {
		//reflection api
		String className = userRepository.getClass().getName();
		String packageName = userRepository.getClass().getPackageName();
		System.out.println("class " + className);
		System.out.println("package  " + packageName);
	}

}
