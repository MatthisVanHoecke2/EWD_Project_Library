package repository;

import org.springframework.data.repository.CrudRepository;

import domain.BookAuthor;

public interface BookAuthorRepository extends CrudRepository<BookAuthor, Long>{

}
