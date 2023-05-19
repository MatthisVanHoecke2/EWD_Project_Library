package com.ewd.project_library;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	
	@Autowired
	private UserDetailsService userDetailsService;

	@Bean
	SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.csrf().and().authorizeHttpRequests(requests -> requests.requestMatchers("/login**").permitAll()
				.requestMatchers("/css/**").permitAll()
				.requestMatchers("/rest/**").permitAll()
				.requestMatchers("/*").hasRole("USER")
				.requestMatchers("/books/popular").hasRole("USER")
				.requestMatchers("/books/details").hasRole("USER")
				.requestMatchers("/**").hasRole("ADMIN")
				)
			.formLogin(form -> form
					.defaultSuccessUrl("/books", true)
					.loginPage("/login"))
					.exceptionHandling().accessDeniedPage("/403");
		
		return http.build();
	}
	
	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService)
		.passwordEncoder(new BCryptPasswordEncoder());
		
	}
	
}
