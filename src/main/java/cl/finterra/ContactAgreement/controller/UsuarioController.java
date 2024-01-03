package cl.finterra.ContactAgreement.controller;

import cl.finterra.ContactAgreement.dao.UsuarioMongoDAO;
import cl.finterra.ContactAgreement.entity.Usuario;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;
@NoArgsConstructor
@Service
public class UsuarioController {

	@Autowired
	UsuarioMongoDAO userDao;

	public Optional<Usuario> login(Usuario user) {

		System.out.println(user.getEmail());

		Optional<Usuario> tem = userDao.findByEmail(user.getEmail());
		Optional<Usuario> temo = userDao.findByRut(user.getRut());

		if (tem.isPresent() || temo.isPresent()) {
			// Verificar las contraseñas y realizar tu lógica
			if ((tem.isPresent() && tem.get().getPassword().equals(user.getPassword())) ||
					(temo.isPresent() && temo.get().getPassword().equals(user.getPassword()))) {
				return tem.isPresent() ? tem : temo;
			}
		}

		return Optional.empty();
	}



}
