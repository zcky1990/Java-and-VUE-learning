package app.repository;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import app.mongo.model.Prodi;

public interface ProdiRepository extends MongoRepository<Prodi, String> {
	Prodi findBy_id(ObjectId _id);
	
	@Query(value = "{'isPublished': ?0}")
	Page<Prodi> findAllByIsPublished(boolean status,Pageable pageable);
	
	@Query(value = "{'isPublished': ?0}")
	List<Prodi> findAllByIsPublished(boolean status);
}
