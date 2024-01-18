package cl.finterra.ContactAgreement.controller;

import cl.finterra.ContactAgreement.dao.UsuarioMongoDAO;
import cl.finterra.ContactAgreement.entity.Usuario;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Optional;
@NoArgsConstructor
@Service
public class UsuarioController {
	@Autowired
	UsuarioMongoDAO userDao;

	public Optional<Usuario> login(Usuario user) {
		System.out.println(user.getEmail());

//		Optional<Usuario> tem = userDao.findByEmail(user.getEmail());
		Optional<Usuario> tem = userDao.findByRut(user.getRut());
//		if (tem.isPresent() || temo.isPresent()) {
		if (tem.isPresent() ) { //si encuentra un rut con la variable tem entonces sigue con el procedimiento
			// Verificar las contraseñas y realizar tu lógica
			if ((tem.isPresent() && tem.get().getPassword().equals(user.getPassword())) ||
					(tem.isPresent() && tem.get().getPassword().equals(user.getPassword()))) {
				return tem.isPresent() ? tem : tem;
			}
		}
		return Optional.empty();
	}
}
