package app.repository;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import app.mongo.model.Mahasiswa;

public interface MahasiswaRepository extends MongoRepository<Mahasiswa, String> {
	Mahasiswa findBy_id(ObjectId _id);

}