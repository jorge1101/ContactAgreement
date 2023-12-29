package cl.finterra.ContactAgreement.dao;

import cl.finterra.ContactAgreement.entity.Usuario;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.Optional;
public interface UsuarioMongoDAO extends MongoRepository<Usuario, String> {

	Optional<Usuario> findByEmail(String usuario);


	boolean existsByEmail(String email);
}