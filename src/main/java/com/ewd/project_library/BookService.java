package com.ewd.project_library;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import domain.Author;
import domain.Book;
import domain.Location;
import repository.UserBookRepository;

@Service
public class BookService {
	@Autowired
	private UserBookRepository userBookRepo;
	
	public BookDetails getDetailsFromBook(Book book) {
		List<Author> authors = book.getBookAuthors().stream().map(el -> el.getAuthor()).collect(Collectors.toList());
		List<Location> locations = book.getLocations();
		int bookRating = userBookRepo.findBookRating(book.getId());
		
		return new BookDetails(book.getId(), book.getName(), book.getIsbn(), book.getPurchasePrice(), bookRating, authors, locations);
	}
	
}
