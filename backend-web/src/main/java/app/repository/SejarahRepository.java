package app.repository;


import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import app.mongo.model.Sejarah;

public interface SejarahRepository extends MongoRepository<Sejarah, String> {
	Sejarah findBy_id(ObjectId _id);

	@Query(value = "{'slug' : ?0}")
	Sejarah findBySlug(String slug);
	
	Sejarah findBySejarahPublishStatus(Boolean status);
}