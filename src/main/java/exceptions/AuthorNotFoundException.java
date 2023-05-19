package exceptions;

public class AuthorNotFoundException extends RuntimeException{

	private static final long serialVersionUID = 1L;

	public AuthorNotFoundException(Integer id) {
		super(String.format("Could not find author with id %d", id));
	}
}
