package cl.finterra.ContactAgreement.dao;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import cl.finterra.ContactAgreement.entity.Usuario;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;


@EnableMongoRepositories
public interface UsuarioMongoDAO extends MongoRepository<Usuario, String> {
	Optional<Usuario> findByUsuario(String usuario);
}