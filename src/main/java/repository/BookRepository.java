package repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import domain.Author;
import domain.Book;

public interface BookRepository extends CrudRepository<Book, Long>{
	
	@Query(value = "with favored as ("
			+ "select book_id, count(*) as favCount from user_book where is_favorite = 1 group by book_id"
			+ ")"
			+ " SELECT b.*, favCount FROM book b LEFT JOIN favored f on b.id = f.book_id"
			+ " ORDER BY favCount desc, name desc", 
			nativeQuery = true)
	public List<Object[]> getBookByMostFavorites();
	
	@Query(value = "SELECT ba.author FROM BookAuthor ba WHERE ba.book.id = :bookId")
	public List<Author> getAuthorsByBook(long bookId);
}
