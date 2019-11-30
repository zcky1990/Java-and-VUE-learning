package app.repository;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import app.mongo.model.Pages;

public interface PagesRepository extends MongoRepository<Pages, String> {
	Pages findBy_id(ObjectId _id);

	@Query(value = "{'permalink' : ?0}")
	Pages findByPermalink(String permalink);
	
	List<Pages> findByIsPublish(boolean status);
}