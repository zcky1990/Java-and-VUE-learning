package app.repository;

import java.util.Date;

import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import app.mongo.model.Mahasiswa;

public interface MahasiswaRepository extends MongoRepository<Mahasiswa, String> {
	Mahasiswa findBy_id(ObjectId _id);
	
	@Query("{ 'createdDate' : { $gt: ?0, $lt: ?1 } }")
	Page<Mahasiswa> findByCreatedDateBetween(Date startDate, Date endDate, Pageable pageable);

}