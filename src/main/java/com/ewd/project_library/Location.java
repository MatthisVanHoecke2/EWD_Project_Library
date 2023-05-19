package com.ewd.project_library;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Location {
	
	@NotEmpty(message = "{field.empty.message}")
	@Pattern(regexp = "^[a-zA-Z]+", message = "{field.onlyLetters.message}")
	private String name;
	
	@NotEmpty(message = "{field.empty.message}")
	private String placecode1;
	
	@NotEmpty(message = "{field.empty.message}")
	private String placecode2;
}
