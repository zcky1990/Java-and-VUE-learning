package app.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import app.mongo.model.Mahasiswa;

public interface MahasiswaRepository extends MongoRepository<Mahasiswa, String> {
	
	@Query(value = "{'timeCreated':{ $gte:?0, $lt:?1}}")
	Page<Mahasiswa> findByTimeCreatedBetween(Date startDate,Date endDate, Pageable pageable);
	
	@Query(value ="{$or: [{'fullName': ?0 }, {'email': ?0}]}")
	Page<Mahasiswa> findByNameOrEmail(String name, Pageable pageable);
	
	@Query(value = "{'timeCreated':{ $gte:?0, $lt:?1}}")
	List<Mahasiswa> findByTimeCreatedBetween(Date startDate,Date endDate);

}