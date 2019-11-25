package app.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import app.mongo.model.HeroBanner;

public interface HeroBannerRepository extends MongoRepository<HeroBanner, String> {
	List<HeroBanner> findAllByIsPublished(boolean status);
}
