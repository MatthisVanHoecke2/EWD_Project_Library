package com.ewd.project_library;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import domain.Author;
import domain.Book;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import repository.BookRepository;
import repository.UserBookRepository;
import validator.BookValidation;

@Slf4j
@Controller
@RequestMapping("/books")
public class BookController {
	@Autowired
	private BookRepository bookRepo;
	
	@Autowired
	private UserBookRepository userBookRepo;
	
	@Autowired
	private BookValidation bookValidation;
	
	@GetMapping
	public String listBooks(Model model, HttpServletRequest request) {
		log.info("get books");
		
		model.addAttribute("bookList", bookRepo.findAll());
		
		boolean isAdmin = false;
		if(request.isUserInRole("ADMIN")) isAdmin = true;
		model.addAttribute("isAdmin", isAdmin);
		
		return "books";
	}
	
	@PostMapping
	public String onSubmit(@RequestParam(name = "id") String id, @RequestParam(name = "action") String action, Model model) {
		switch(action.toLowerCase()) {
		case "add" -> log.info("post add book id=" + id + " to favorites");
		case "remove" -> log.info("post remove book id=" + id + " from favorites");
		}
		
		model.addAttribute("bookList", bookRepo.findAll());
		
		return "books";
	}
	
	
	@GetMapping("/details")
	public String listBookDetails(Model model, @RequestParam(name = "id") String id) {
		log.info("get book details of book" + "id " + id);
		
		Book book = bookRepo.findById(Long.parseLong(id)).orElse(null);
		model.addAttribute("bookDetails", book);
		
		long rating = userBookRepo.findBookRating(Long.parseLong(id));
		model.addAttribute("rating", rating);
		
		return "bookdetails";
	}
	
	@GetMapping("/popular")
	public String listPopularBooks(Model model) {
		log.info("get popular books");
		
		List<FavoredBook> fb = bookRepo.getBookByMostFavorites().stream().map(el -> {
			long id = (long) el[0];
			String name = (String) el[2];
			int favAmount = el[4] == null ? 0 : ((Long)el[4]).intValue();
			List<Author> authors = bookRepo.getAuthorsByBook(id);
			return new FavoredBook(id, name, favAmount, authors);
		}).collect(Collectors.toList());
		
		model.addAttribute("bookList", fb);
		
		return "popular";
	}
	
	@GetMapping("/add")
	public String addBook(Model model) {
		log.info("get add book");
		
		com.ewd.project_library.Book book = new com.ewd.project_library.Book();
		book.setAuthorList(new ArrayList<>(Arrays.asList(new com.ewd.project_library.Author())));
		book.setLocationList(new ArrayList<>(Arrays.asList(new com.ewd.project_library.Location())));
		model.addAttribute("book", book);
		
		return "addbooks";
	}
	
	@PostMapping("/add/author")
	public String onAddAuthor(com.ewd.project_library.Book book, @RequestParam(name = "remove", required = false) Integer index, Model model) {
		if(index != null) {
			book.getAuthorList().remove(index.intValue());
			log.info("post remove author");
		}
		else {
			book.getAuthorList().add(new com.ewd.project_library.Author());
			log.info("post add author");
		}
		
		return "addbooks";
	}
	
	@PostMapping("/add/location")
	public String onAddLocation(com.ewd.project_library.Book book, @RequestParam(name = "remove", required = false) Integer index, Model model) {
		if(index != null) {
			book.getLocationList().remove(index.intValue());
			log.info("post remove location");
		}
		else {
			book.getLocationList().add(new com.ewd.project_library.Location());
			log.info("post add location");
		}
		
		return "addbooks";
	}
	
	@PostMapping("/add/save")
	public String onAddBook(@Valid com.ewd.project_library.Book book, BindingResult result) {
		log.info("post save book");
		
		bookValidation.validate(book, result);
		if(result.hasErrors()) return "addbooks";
		
		
		return "redirect:/books";
	}
}
