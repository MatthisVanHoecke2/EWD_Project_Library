package com.ewd.project_library;

import java.util.ArrayList;
import java.util.List;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BookDetails {

	private long id;
	
	private String name;
	
	private String isbn;
	
	private double purchasePrice;
	
	private int stars;
	
	private List<domain.Author> bookAuthors = new ArrayList<>();
	
	private List<domain.Location> locations = new ArrayList<>();
}
