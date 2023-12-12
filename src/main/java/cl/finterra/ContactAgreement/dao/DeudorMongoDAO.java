package cl.finterra.ContactAgreement.dao;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import cl.finterra.ContactAgreement.entity.Deudor;
public interface DeudorMongoDAO extends MongoRepository<Deudor, String> {
	Optional<Deudor> findByRut(String rut);
}
