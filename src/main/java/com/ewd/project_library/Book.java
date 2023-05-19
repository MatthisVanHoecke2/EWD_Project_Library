package com.ewd.project_library;

import java.util.List;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;
import validator.ValidLocation;

@Setter
@Getter
public class Book {
	@NotEmpty(message = "{field.empty.message}")
	private String name;
	
	private String isbn;
	
	private String price;
	
	@Valid
	private List<Author> authorList;
	
	@Valid
	private List<@ValidLocation Location> locationList;
	
}
