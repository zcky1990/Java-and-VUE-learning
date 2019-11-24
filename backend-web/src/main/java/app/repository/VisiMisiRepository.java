package app.repository;


import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import app.mongo.model.VisiMisi;

public interface VisiMisiRepository extends MongoRepository<VisiMisi, String> {
	VisiMisi findBy_id(ObjectId _id);

	@Query(value = "{'slug' : ?0}")
	VisiMisi findBySlug(String slug);
	
	VisiMisi findByVisiMisiPublishStatus(Boolean status);
	
}