package cl.finterra.ContactAgreement.dao;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import cl.finterra.ContactAgreement.entity.Deudor;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@EnableMongoRepositories
public interface DeudorMongoDAO extends MongoRepository<Deudor, String> {
	Optional<Deudor> findByRut(String rut);
}
