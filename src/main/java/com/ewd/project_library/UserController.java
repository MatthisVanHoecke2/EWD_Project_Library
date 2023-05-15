package com.ewd.project_library;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import repository.UserRepository;

@Controller
@RequestMapping("/user")
public class UserController {
	@Autowired
	private UserRepository repository;
	
	@GetMapping
	public String listUser(Model model) {
		model.addAttribute("userList", repository.findAll());
		model.addAttribute("userName", repository.findByName("Keters"));
		
		return "user";
	}
}
