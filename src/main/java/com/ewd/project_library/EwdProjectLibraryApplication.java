package com.ewd.project_library;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import validator.BookValidation;

@SpringBootApplication
@EnableJpaRepositories("repository")
@EntityScan("domain")
public class EwdProjectLibraryApplication implements WebMvcConfigurer{

	public static void main(String[] args) {
		SpringApplication.run(EwdProjectLibraryApplication.class, args);
	}

	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addRedirectViewController("/", "/books");
		registry.addViewController("/403").setViewName("403");
	}
	
	@Bean
	BookValidation bookValidation() {
		return new BookValidation();
	}
}
