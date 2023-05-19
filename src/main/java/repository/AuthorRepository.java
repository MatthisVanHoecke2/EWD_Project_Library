package repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import domain.Author;

public interface AuthorRepository extends CrudRepository<Author, Long>{
	Optional<Author> findByFirstNameAndLastName(String firstName, String lastName);
}
