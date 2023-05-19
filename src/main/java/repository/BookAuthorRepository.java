package repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import domain.BookAuthor;

public interface BookAuthorRepository extends CrudRepository<BookAuthor, Long>{
	
	@Query("SELECT ba FROM BookAuthor ba WHERE ba.author.id = :id")
	public List<BookAuthor> findByAuthorId(long id);
}
