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

	public boolean login(Usuario user) {

		System.out.println(user.getEmail());

	 Optional<Usuario> tem = userDao.findByEmail(user.getEmail());



		if(tem.isPresent()) {

			if(tem.get().getPassword().equals(user.getPassword())) {

				return true;
			} else {
				return false;
			}


		} else {

			return false;
		}


	}



}
