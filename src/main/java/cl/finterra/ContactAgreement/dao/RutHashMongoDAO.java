package cl.finterra.ContactAgreement.dao;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import cl.finterra.ContactAgreement.entity.RutConHash;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@EnableMongoRepositories
public interface RutHashMongoDAO extends MongoRepository<RutConHash, String> {
	Optional<RutConHash> findByrutHash(String rutHash);
}