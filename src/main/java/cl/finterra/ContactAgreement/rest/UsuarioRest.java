package cl.finterra.ContactAgreement.rest;

import cl.finterra.ContactAgreement.Security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import cl.finterra.ContactAgreement.controller.UsuarioController;
import cl.finterra.ContactAgreement.entity.Usuario;

import javax.validation.Valid;

@RestController
@RequestMapping("usuario")
public class UsuarioRest {

	@Autowired
	UsuarioController userController;

	private final JwtTokenProvider tokenProvider;

	public UsuarioRest(JwtTokenProvider tokenProvider) {
		this.tokenProvider = tokenProvider;
	}

	@PostMapping("/login")
	public ResponseEntity<?> login(@Valid @RequestBody Usuario usuario, BindingResult result) {
		if (result.hasErrors()) {
			// Construir un mensaje de error más descriptivo
			StringBuilder errorMessage = new StringBuilder("Error de validación: ");

			for (FieldError error : result.getFieldErrors()) {
				errorMessage.append(error.getField())
						.append(" ")
						.append(error.getDefaultMessage())
						.append("; ");
			}

			return ResponseEntity.badRequest().body(errorMessage.toString());
		}

		System.out.println("Login: " + usuario);
		boolean loginSuccess = userController.login(usuario);
		System.out.println(loginSuccess);

		if (loginSuccess) {
			String accessToken = tokenProvider.generateAccessToken(usuario.getEmail());
			usuario.setAccessToken(accessToken);
			System.out.println("AccessToken: " + accessToken);

			// Devolver el objeto Usuario modificado en la respuesta
			return ResponseEntity.ok(usuario);
		} else {
			// Devolver un objeto Usuario vacío o con datos predeterminados en caso de fallo de autenticación
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new Usuario());
		}
	}








}
