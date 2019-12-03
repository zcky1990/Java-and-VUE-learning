package app.repository;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import app.mongo.model.Faculty;

public interface FacultyRepository extends MongoRepository<Faculty, String> {
	Faculty findBy_id(ObjectId _id);
	
	@Query(value = "{'isPublished': ?0}")
	Page<Faculty> findAllByIsPublished(boolean status,Pageable pageable);
	
	@Query(value = "{'isPublished': ?0}")
	List<Faculty> findAllByIsPublished(boolean status);
}
