package repository;

import org.springframework.data.repository.CrudRepository;

import domain.Location;

public interface LocationRepository extends CrudRepository<Location, Long>{
	
}
