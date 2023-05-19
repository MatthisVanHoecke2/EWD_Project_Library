package com.ewd.project_library;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import exceptions.AuthorNotFoundException;
import exceptions.BookNotFoundException;
import repository.BookAuthorRepository;
import repository.BookRepository;

@RestController
@RequestMapping(value = "/rest")
public class BookRestController {

	@Autowired
	private BookAuthorRepository bookAuthorRepo;
	
	@Autowired
	private BookRepository bookRepo;
	
	@Autowired
	private BookService service;
	
	@GetMapping(value = "/books/author/{id}")
	public List<domain.Book> getAllBooksByAuthor(@PathVariable("id") long id) {
		List<domain.BookAuthor> bookAuthors = bookAuthorRepo.findByAuthorId(id);
		if(bookAuthors.size() == 0) throw new AuthorNotFoundException(Long.valueOf(id).intValue());
		return bookAuthors.stream().map(el -> el.getBook()).collect(Collectors.toList());
	}
	
	@GetMapping(value = "/books/isbn/{isbn}")
	public BookDetails getAllBooksByAuthor(@PathVariable("isbn") String isbn) {
		domain.Book book = bookRepo.findByIsbn(isbn);
		if(book == null) throw new BookNotFoundException(isbn);
		return service.getDetailsFromBook(book);
	}
	
}
