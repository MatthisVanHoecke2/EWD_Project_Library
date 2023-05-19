package com.ewd.project_library;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Author {
	@NotEmpty(message = "{field.empty.message}")
	private String firstName;
	@NotEmpty(message = "{field.empty.message}")
	private String lastName;
}
