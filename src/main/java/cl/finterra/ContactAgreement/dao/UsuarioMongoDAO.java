package cl.finterra.ContactAgreement.dao;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import cl.finterra.ContactAgreement.entity.Usuario;
public interface UsuarioMongoDAO extends MongoRepository<Usuario, String> {
	Optional<Usuario> findByUsuario(String usuario);
}