package com.ewd.project_library;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Author {
	@NotEmpty
	private String firstName;
	@NotEmpty
	private String lastName;
}
