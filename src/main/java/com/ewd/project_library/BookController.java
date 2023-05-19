package com.ewd.project_library;

import java.security.Principal;
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
import domain.BookAuthor;
import domain.User;
import domain.UserBook;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import repository.AuthorRepository;
import repository.BookAuthorRepository;
import repository.BookRepository;
import repository.LocationRepository;
import repository.UserBookRepository;
import repository.UserRepository;
import validator.BookValidation;

@Slf4j
@Controller
@RequestMapping("/books")
public class BookController {
	@Autowired
	private AuthorRepository authorRepo;
	
	@Autowired
	private BookAuthorRepository bookAuthorRepo;
	
	@Autowired
	private LocationRepository locationRepo;
	
	@Autowired
	private BookRepository bookRepo;
	
	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private UserBookRepository userBookRepo;
	
	@Autowired
	private BookValidation bookValidation;
	
	@GetMapping
	public String listBooks(Model model, Principal principal) {
		log.info("get books");
		
		User user = userRepo.findByName(principal.getName());
		
		model.addAttribute("bookList", bookRepo.findAll());
		model.addAttribute("favoriteList", user.getUserBooks().stream()
				.filter(el -> el.isFavorite())
				.map(el -> el.getBook().getId())
				.collect(Collectors.toList()));
		model.addAttribute("isAdmin", user.isAdmin());
		
		return "books";
	}
	
	@PostMapping
	public String onSubmit(@RequestParam(name = "id") String id, @RequestParam(name = "action") String action, Model model, Principal principal) {
		long bookId = Long.parseLong(id);
		User user = userRepo.findByName(principal.getName());
		List<UserBook> userBooks = user.getUserBooks();
		Book book = bookRepo.findById(bookId).orElse(null);
		
		if(book == null) return "redirect:/books";
		switch(action.toLowerCase()) {
		case "add" -> {
			log.info("post add book id=" + id + " to favorites");
			
			UserBook ub = userBooks.stream().filter(el -> el.getBook().getId() == bookId).findAny().orElse(new UserBook(user, book, true));
			ub.setFavorite(true);
			userBookRepo.save(ub);
		}
		case "remove" -> {
			log.info("post remove book id=" + id + " from favorites");
			
			UserBook ub = userBooks.stream().filter(el -> el.getBook().getId() == bookId).findAny().orElse(new UserBook(user, book, false));
			ub.setFavorite(false);
			userBookRepo.save(ub);
		}
		}
		
		return "redirect:/books";
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
		
		saveBook(book);
		
		return "redirect:/books";
	}
	
	private void saveBook(com.ewd.project_library.Book book) {
		double price = book.getPrice().isEmpty() ? 0 : Double.parseDouble(book.getPrice());
		Book nBook = new Book(book.getName(), book.getIsbn(), price);
		
		List<BookAuthor> bookAuthors = new ArrayList<>();
		for(com.ewd.project_library.Author auth : book.getAuthorList()) {
			String firstName = auth.getFirstName(), lastName = auth.getLastName();
			Author author = authorRepo.findByFirstNameAndLastName(firstName, lastName).orElse(new Author(firstName, lastName));
			authorRepo.save(author);
			
			bookAuthors.add(new BookAuthor(nBook, author));
		}
		
		List<domain.Location> locations = new ArrayList<>();
		for(com.ewd.project_library.Location loc : book.getLocationList()) {
			domain.Location location = new domain.Location(nBook, Integer.parseInt(loc.getPlacecode1()), Integer.parseInt(loc.getPlacecode2()), loc.getName());
			locations.add(location);
		}
		
		bookRepo.save(nBook);
		
		locations.forEach(el -> locationRepo.save(el));
		bookAuthors.forEach(el -> bookAuthorRepo.save(el));
		
		nBook.setBookAuthors(bookAuthors);
		nBook.setLocations(locations);
		bookRepo.save(nBook);
	}
}
