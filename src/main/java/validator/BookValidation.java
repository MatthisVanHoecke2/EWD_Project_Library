package validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.ewd.project_library.Book;

import repository.BookRepository;

public class BookValidation implements Validator{

	@Autowired
	private BookRepository bookRepo;
	
	@Override
	public boolean supports(Class<?> clazz) {
		return Book.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		Book book = (Book) target;
		
		validatePrice(book.getPrice(), errors);
		validateISBN(book.getIsbn(), errors);
		if(bookRepo.findByIsbn(book.getIsbn()) != null) errors.rejectValue("isbn", "book.exists.message", "already exists");
		
		
	}
	
	private void validatePrice(String price, Errors errors) {
		if(price.isEmpty()) return;
		try {
			double dprice = Double.parseDouble(price);
			if(dprice <= 0 || dprice >= 100) errors.rejectValue("price", "book.price.inRange.message", "must be between €0 and €100");
		}
		catch(NumberFormatException ex) {
			errors.rejectValue("price", "numeric.valueType", "must be a numeric value");
		}
	}
	
	private void validateISBN(String isbn, Errors errors) {
		if(isbn.isEmpty()) {
			errors.rejectValue("isbn", "field.empty.message", "must not be empty");
			return;
		}
		
		if(!isbn.matches("[0-9]{13}")) {
			errors.rejectValue("isbn", "book.isbn.missingNumbers", "format does not comply");
			return;
		}
		
		int total = 0;
		for(int i = 0; i < isbn.length()-1; i++) {
			if(i % 2 == 0) total += Character.getNumericValue(isbn.charAt(i));
			else total += (Character.getNumericValue(isbn.charAt(i))*3);
		}
		int control = (int)(Math.ceil(((double)total)/10)*10) - total;
		
		if(Character.getNumericValue(isbn.charAt(isbn.length()-1)) != control) errors.rejectValue("isbn", "book.isbn.invalid", "invalid ISBN code");
		
	}

}
