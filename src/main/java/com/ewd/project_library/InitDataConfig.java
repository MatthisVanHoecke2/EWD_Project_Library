package com.ewd.project_library;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import domain.Author;
import domain.Book;
import domain.BookAuthor;
import domain.Location;
import domain.User;
import domain.UserBook;
import repository.AuthorRepository;
import repository.BookAuthorRepository;
import repository.BookRepository;
import repository.LocationRepository;
import repository.UserBookRepository;
import repository.UserRepository;

@Component
public class InitDataConfig implements CommandLineRunner{

	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private BookRepository bookRepo;
	
	@Autowired
	private AuthorRepository authorRepo;
	
	@Autowired
	private BookAuthorRepository bookAuthorRepo;
	
	@Autowired
	private UserBookRepository userBookRepo;
	
	@Autowired
	private LocationRepository locationRepo;
	
	@Override
	public void run(String... args) throws Exception {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		
		// TODO Auto-generated method stub
		User userA = new User("Keters", encoder.encode("1234"), false), 
				userB = new User("Tania", encoder.encode("1234"), false), 
				userC = new User("Ann", encoder.encode("1234"), false), 
				userD = new User("Jurgen", encoder.encode("1234"), false),
				admin = new User("Admin", encoder.encode("admin"), true);
		userRepo.save(userA);
		userRepo.save(userB);
		userRepo.save(userC);
		userRepo.save(userD);
		userRepo.save(admin);
		
		Book bookA = new Book("hi", "1234567890128", 15.00), 
				bookB = new Book("hello", "1214567890120", 17.00);
		bookRepo.save(bookA);
		bookRepo.save(bookB);
		
		userBookRepo.save(new UserBook(userA, bookA, false));
		userBookRepo.save(new UserBook(userB, bookB, true));
		userBookRepo.save(new UserBook(userC, bookA, false));
		userBookRepo.save(new UserBook(userD, bookB, false));
		userBookRepo.save(new UserBook(userA, bookB, false));
		
		Author authorA = new Author("Jeff", "Bezos"), 
				authorB = new Author("Elon", "Musk"), 
				authorC = new Author("Jack", "Sparrow"),
				authorD = new Author("Wilbur", "Soot"), 
				authorE = new Author("Tommy", "Innit");
		authorRepo.save(authorA);
		authorRepo.save(authorB);
		authorRepo.save(authorC);
		authorRepo.save(authorD);
		authorRepo.save(authorE);
		
		bookAuthorRepo.save(new BookAuthor(bookA, authorA));
		bookAuthorRepo.save(new BookAuthor(bookA, authorB));
		bookAuthorRepo.save(new BookAuthor(bookA, authorC));
		bookAuthorRepo.save(new BookAuthor(bookB, authorD));
		bookAuthorRepo.save(new BookAuthor(bookB, authorE));
		bookAuthorRepo.save(new BookAuthor(bookB, authorA));
		
		locationRepo.save(new Location(bookA, 50, 100, "LibraryA"));
		locationRepo.save(new Location(bookB, 100, 150, "LibraryB"));
		locationRepo.save(new Location(bookA, 150, 200, "LibraryC"));
	}

}
