package cl.finterra.ContactAgreement.dao;

import cl.finterra.ContactAgreement.entity.Usuario;
import org.springframework.data.mongodb.repository.MongoRepository;

import cl.finterra.ContactAgreement.entity.AgregarEliminar;

import java.util.Optional;

public interface AgregarEliminarMongoDAO extends MongoRepository<AgregarEliminar, String> {
    Optional<Usuario> findByCorreo(String correo);

}