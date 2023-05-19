package com.ewd.project_library;

import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;

import exceptions.AuthorNotFoundException;

@RestControllerAdvice
public class AuthorErrorAdvice {
	@ResponseBody
	@ExceptionHandler(AuthorNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	String authorNotFoundHandler(AuthorNotFoundException ex) {
		return ex.getMessage();
	}
	
}
