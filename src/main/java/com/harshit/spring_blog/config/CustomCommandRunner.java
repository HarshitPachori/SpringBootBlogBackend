package com.harshit.spring_blog.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import com.harshit.spring_blog.models.Roles;
import com.harshit.spring_blog.repositories.RoleRepository;
import com.harshit.spring_blog.utils.AppConstants;

@Configuration
public class CustomCommandRunner implements CommandLineRunner {

  @Autowired
  private RoleRepository roleRepository;

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
