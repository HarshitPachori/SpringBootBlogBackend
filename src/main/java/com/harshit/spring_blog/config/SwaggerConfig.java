package com.harshit.spring_blog.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;

@Configuration
@OpenAPIDefinition(security = @SecurityRequirement(name = "JWT"))
@SecurityScheme(name = "JWT", type = SecuritySchemeType.HTTP, scheme = "bearer", bearerFormat = "JWT", in = SecuritySchemeIn.HEADER)
public class SwaggerConfig {

  public static final String AUTHORIZATION_HEADER = "Authorization";

  @Bean
  public OpenAPI SpringBlogApi() {
    return new OpenAPI().info(getInfo());
  }

  private Info getInfo() {
    return new Info()
        .title("Blogging Application : Spring Boot")
        .description("This project is developed by Harshit Pachori")
        .version("1.0")
        .termsOfService("Terms of Service")
        .contact(new Contact()
            .name("Harshit Pachori")
            .email("harshitpachori345@gmail.com")
            .url("https://harshitpachoriportfolio.netlify.app/"))
        .license(new License().name("License of APIS").url("API license URL"));
  };
}
