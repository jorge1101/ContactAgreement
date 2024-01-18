package cl.finterra.ContactAgreement.dao;

import cl.finterra.ContactAgreement.entity.Usuario;
import org.springframework.data.mongodb.repository.MongoRepository;

import cl.finterra.ContactAgreement.entity.Contacto;

import java.util.Optional;

public interface ContactoMongoDAO extends MongoRepository<Contacto, String> {

    Optional<Usuario> findByEmail(String email);
    Optional<Contacto> findById(String id);
}