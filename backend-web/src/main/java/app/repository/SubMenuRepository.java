package app.repository;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import app.mongo.model.SubMenu;

public interface SubMenuRepository extends MongoRepository<SubMenu, String> {
	SubMenu findBy_id(ObjectId _id);

}