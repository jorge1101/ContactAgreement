package cl.finterra.ContactAgreement.rest;

import cl.finterra.ContactAgreement.Security.JwtTokenProvider;
import cl.finterra.ContactAgreement.controller.UsuarioController;
import cl.finterra.ContactAgreement.dao.UsuarioMongoDAO;
import cl.finterra.ContactAgreement.entity.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import cl.finterra.ContactAgreement.dto.UsuarioDTO;
import javax.validation.Valid;
import java.util.Optional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("usuario")
public class UsuarioRest {
	@Autowired
	UsuarioController userController;
	@Autowired
	private UsuarioMongoDAO userDao;
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
		Optional<Usuario> authenticatedUser = userController.login(usuario);
		if (authenticatedUser.isPresent()) {
			String accessToken = tokenProvider.generateAccessToken(usuario.getEmail());
			authenticatedUser.get().setAccessToken(accessToken);
			System.out.println("AccessToken: " + accessToken);
			// Devolver el objeto Usuario modificado en la respuesta
			return ResponseEntity.ok(authenticatedUser.get());
		} else {
			// Devolver un objeto Usuario vacío o con datos predeterminados en caso de fallo de autenticación
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new Usuario());
		}
	}

	private void validatePassword(String password, BindingResult result) {
		// Realizar tus validaciones personalizadas aquí
		// Ejemplo: al menos 4 números, un punto y una mayúscula
		// Simplemente como ejemplo, ajusta según tus requisitos
		if (!password.matches("(.*[0-9]){2,}.*") || !password.contains(".") || !password.matches(".*[A-Z].*" ) || !password.matches(".*[a-z].*")) {
			result.addError(new FieldError("Usuario", "password", "La contraseña no cumple con los requisitos mínimos"));
		}
	}

	// Método para obtener detalles de errores de validación
	private String getValidationErrorDetails(BindingResult result) {
		StringBuilder errorMessage = new StringBuilder("Errores de validación: ");
		for (FieldError error : result.getFieldErrors()) {
			errorMessage.append(error.getField())
					.append(" - ")
					.append(error.getDefaultMessage())
					.append("; ");
		}
		return errorMessage.toString();
	}
	@PostMapping("/encontrar-usuario")
	public ResponseEntity<?> buscarUsuarioPorEmail(@RequestBody String email) {
		String rut = UsuarioDTO.getRut();

		// Verificar si tanto el email como el RUT están presentes
		if (email == null && rut == null) {
			return new ResponseEntity<>("Debe proporcionar al menos un parámetro (email o RUT)", HttpStatus.BAD_REQUEST);
		}
		// Obtener el usuario por correo electrónico
		System.out.println(email.getClass().getSimpleName());
		Usuario usuarioEncontrado = userDao.findByEmail(email).orElse(null);
		System.out.println(usuarioEncontrado);
		// Verificar si el usuario con el correo electrónico especificado existe
		if (!userDao.existsByEmail(email)) {
			return new ResponseEntity<>("Usuario no encontrado", HttpStatus.NOT_FOUND);
		}


		if (usuarioEncontrado != null) {
			return new ResponseEntity<>(usuarioEncontrado, HttpStatus.OK);
		} else {
			return new ResponseEntity<>("Error interno del servidor", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping("/nombre")
	public String name(@Valid @RequestBody Usuario usuario, BindingResult result) {
        return UsuarioDTO.getName();
	}
	@PutMapping("/{email}/actualizar-contrasena")
	public ResponseEntity<?> actualizarContrasena(
			@PathVariable String email,
			@Validated @RequestBody Usuario usuarioConNuevaContrasena,
			BindingResult result) {

		// Verificar si el usuario con el correo electrónico especificado existe
		if (!userDao.existsByEmail(email)) {
			return new ResponseEntity<>("Usuario no encontrado", HttpStatus.NOT_FOUND);
		}

		// Realizar validaciones personalizadas
		validatePassword(usuarioConNuevaContrasena.getPassword(), result);

		// Verificar si hay errores de validación
		if (result.hasErrors()) {
			return ResponseEntity.badRequest().body(getValidationErrorDetails(result));
		}

		// Obtener el usuario existente por correo electrónico
		Usuario usuarioExistente = userDao.findByEmail(email).orElse(null);

		if (usuarioExistente != null) {
			// Actualizar la contraseña
			usuarioExistente.setPassword(usuarioConNuevaContrasena.getPassword());

			// Guardar el usuario actualizado
			Usuario usuarioActualizado = userDao.save(usuarioExistente);

			return new ResponseEntity<>(usuarioActualizado, HttpStatus.OK);
		} else {
			return new ResponseEntity<>("Error interno del servidor", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}





}
