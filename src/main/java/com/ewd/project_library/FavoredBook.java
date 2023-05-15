package com.ewd.project_library;

import java.util.ArrayList;
import java.util.List;

import domain.Author;
import lombok.Getter;

@Getter
public class FavoredBook {
	private long id;
	private String name;
	private int favoredAmount;
	private List<Author> authors;
	public FavoredBook(long id, String name, int favoredAmount, List<Author> authors) {
		this.id = id;
		this.name = name;
		this.favoredAmount = favoredAmount;
		this.authors = new ArrayList<>(authors);
	}
}
