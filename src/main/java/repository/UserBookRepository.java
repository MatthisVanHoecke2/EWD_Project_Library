package repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import domain.UserBook;

public interface UserBookRepository extends CrudRepository<UserBook, Long>{
	
	@Query(value = "SELECT count(*) FROM user_book WHERE book_id = :bookId AND is_favorite = 1", nativeQuery = true)
	public long findBookRating(Long bookId);
}
