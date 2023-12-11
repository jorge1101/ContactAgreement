package cl.finterra.ContactAgreement.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cl.finterra.ContactAgreement.dao.UsuarioMongoDAO;
import cl.finterra.ContactAgreement.entity.Usuario;

import lombok.NoArgsConstructor;

@NoArgsConstructor
@Service
public class UsuarioController {

	@Autowired
	UsuarioMongoDAO userDao;
	
	public boolean login(Usuario user) {	
		
		System.out.println(user.getUsuario());
		
	 Optional<Usuario> tem = userDao.findByUsuario(user.getUsuario());
	 
	 
	 if(tem.isPresent()) {
		 
		 if(tem.get().getContrasena().equals(user.getContrasena())) {
			 return true;
		 } else {
			 return false;
		 }
		 
		 
	 } else {
		 
		 return false;
	 }
		
		
	}
	
	
	
}
