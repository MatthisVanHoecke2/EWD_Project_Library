package validator;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.ewd.project_library.Book;

public class BookValidation implements Validator{

	@Override
	public boolean supports(Class<?> clazz) {
		return Book.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		Book book = (Book) target;
		
		validatePrice(book.getPrice(), errors);
		validateISBN(book.getIsbn(), errors);
		
		
		
	}
	
	private void validatePrice(String price, Errors errors) {
		if(price.isEmpty()) return;
		try {
			double dprice = Double.parseDouble(price);
			if(dprice <= 0 || dprice >= 100) errors.rejectValue("price", "doubleRange.book.price", "must be between €0 and €100");
		}
		catch(NumberFormatException ex) {
			errors.rejectValue("price", "valueType.book.price", "must be a numeric value");
		}
	}
	
	private void validateISBN(String isbn, Errors errors) {
		if(isbn.isEmpty()) {
			errors.rejectValue("isbn", "empty.book.isbn", "must not be empty");
			return;
		}
		
		if(!isbn.matches("[0-9]{13}")) {
			errors.rejectValue("isbn", "missingNumbers.book.isbn", "format does not comply");
			return;
		}
		
		int total = 0;
		for(int i = 0; i < isbn.length()-1; i++) {
			if(i % 2 == 0) total += Character.getNumericValue(isbn.charAt(i));
			else total += (Character.getNumericValue(isbn.charAt(i))*3);
		}
		int control = (int)(Math.ceil(((double)total)/10)*10) - total;
		
		if(Character.getNumericValue(isbn.charAt(isbn.length()-1)) != control) errors.rejectValue("isbn", "invalidCode.book.isbn", "invalid ISBN code");
		
	}

}
