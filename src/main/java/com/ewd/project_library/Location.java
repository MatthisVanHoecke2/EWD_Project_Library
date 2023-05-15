package com.ewd.project_library;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Location {
	
	@NotEmpty
	@Pattern(regexp = "^[a-zA-Z]+", message = "can only contain letters")
	private String name;
	
	@NotEmpty
	private String placecode1;
	
	@NotEmpty
	private String placecode2;
}
