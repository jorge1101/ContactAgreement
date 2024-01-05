package cl.finterra.ContactAgreement.dao;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import cl.finterra.ContactAgreement.entity.RutConHash;
public interface RutHashMongoDAO extends MongoRepository<RutConHash, String> {
	Optional<RutConHash> findByRutHash(String rutHash);
}