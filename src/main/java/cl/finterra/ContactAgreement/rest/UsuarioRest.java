package cl.finterra.ContactAgreement.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cl.finterra.ContactAgreement.controller.UsuarioController;
import cl.finterra.ContactAgreement.entity.Usuario;

@RestController
@RequestMapping("usuario")
public class UsuarioRest {

	@Autowired
	UsuarioController userController;
	
	@PostMapping("/login")
	public ResponseEntity<Boolean> login(@RequestBody Usuario usuario) {
		System.out.println("Login : " + usuario);

		return ResponseEntity.ok(userController.login(usuario));
	}




	
	
}
