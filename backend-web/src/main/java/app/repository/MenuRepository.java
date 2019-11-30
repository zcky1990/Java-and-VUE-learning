package app.repository;

import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import app.mongo.model.Menu;

public interface MenuRepository extends MongoRepository<Menu, String> {
	Menu findBy_id(ObjectId _id);

}