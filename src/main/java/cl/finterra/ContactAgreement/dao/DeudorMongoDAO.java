package cl.finterra.ContactAgreement.dao;

import java.util.List;
import java.util.Optional;

import cl.finterra.ContactAgreement.rest.ListarRest;
import org.springframework.data.mongodb.repository.MongoRepository;

import cl.finterra.ContactAgreement.entity.Deudor;
public interface DeudorMongoDAO extends MongoRepository<Deudor, String> {
	Optional<Deudor> findByRut(String rut);
	List<Deudor> findAll();

}
