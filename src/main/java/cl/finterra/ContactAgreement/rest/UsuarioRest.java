package cl.finterra.ContactAgreement.rest;

import java.util.logging.Logger;
import ch.qos.logback.core.boolex.Matcher;
import ch.qos.logback.core.net.SMTPAppenderBase;
import cl.finterra.ContactAgreement.Security.JwtTokenProvider;
import cl.finterra.ContactAgreement.Security.SecurityConfig;
import cl.finterra.ContactAgreement.Security.TokenRefreshException;
import cl.finterra.ContactAgreement.controller.UsuarioController;
import cl.finterra.ContactAgreement.dao.UsuarioMongoDAO;
import cl.finterra.ContactAgreement.entity.Usuario;
import com.fasterxml.jackson.annotation.JacksonInject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import cl.finterra.ContactAgreement.dto.UsuarioDTO;
import javax.validation.Valid;
import java.util.Objects;
import java.util.Optional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import cl.finterra.ContactAgreement.entity.Usuario;
@RestController
@RequestMapping("usuario")
public class UsuarioRest {
	@Autowired
	UsuarioController userController;
	@Autowired
	private UsuarioMongoDAO userDao;
	@Autowired
	private PasswordEncoder passwordEncoder;
	private static final Logger LOGGER = Logger.getLogger(UsuarioRest.class.getName());

	private final JwtTokenProvider tokenProvider;
	public UsuarioRest(JwtTokenProvider tokenProvider) {
		this.tokenProvider = tokenProvider;
	}
	@CrossOrigin(origins = "http://localhost:8081")
	@PostMapping("/login")
	public ResponseEntity<?> login(@Valid @RequestBody Usuario usuario, BindingResult result) {
		// Buscar el usuario en la base de datos por su rut
		Optional<Usuario> authenticatedUser = userDao.findByRut(usuario.getRut());

		// validacion si el usuario existe y la contraseña es correcta validada mediante hashing de spring BCrypt
		if (authenticatedUser.isPresent() && passwordEncoder.matches(usuario.getPassword(), authenticatedUser.get().getPassword())) {
			if (result.hasErrors()) { //si hay errores
				StringBuilder errorMessage = new StringBuilder("Error de validación: ");
				for (FieldError error : result.getFieldErrors()) {
					errorMessage.append(error.getField())
							.append(" ")
							.append(error.getDefaultMessage())
							.append("; ");
				}
				return ResponseEntity.badRequest().body(errorMessage.toString());
			}
//			try {
//				Thread.sleep(2000);
//			} catch (InterruptedException e) {
//				e.printStackTrace();
//			}

			// La contraseña es correcta, proceder con la autenticación
			String accessToken = tokenProvider.generateAccessToken(usuario.getEmail());
			authenticatedUser.get().setAccessToken(accessToken);
			return ResponseEntity.ok(authenticatedUser.get());

		} else {
			String errorMessage = "Credenciales inválidas. Usuario o contraseña incorrectos.";
			LOGGER.warning("Intento de inicio de sesión fallido para el usuario con el rut: " + usuario.getRut() + ". " + errorMessage);
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorMessage);
		}

	}
	@Autowired
	private UsuarioMongoDAO usuarioRepository;
	@PostMapping("/renovar-token") //no recomendado, deberia funcionar de forma implicita y no llamandose
	public ResponseEntity<?> renewToken(@RequestBody String refreshToken) {
		try {
			String newAccessToken = tokenProvider.renewAccessToken(refreshToken);
			return ResponseEntity.ok(newAccessToken);
		} catch (TokenRefreshException e) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
		}
	}
	public void cambiarContrasena(Usuario usuario, String nuevaContrasena, BindingResult result) {
		// Validar la nueva contraseña
		validatePassword(nuevaContrasena, result);
		// Obtener usuario
		Usuario usuarioEnDB = usuarioRepository.findById(usuario.getId()).orElse(null);
		// Verificar si la nueva contraseña es igual a la contraseña actual
		if (usuarioEnDB != null && nuevaContrasena.equals(usuarioEnDB.getPassword())) {
			result.addError(new FieldError("Usuario", "password", "La nueva contraseña no puede ser igual a la contraseña actual"));
		}
	}
	private void validatePassword(String password, BindingResult result) {
		// validaciones para que contenga los caracteres necesarios
		if (!password.matches("(.*[0-9]){2,}.*") || !password.contains(".") || !password.matches(".*[A-Z].*") || !password.matches(".*[a-z].*")) {
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
			// Actualizar la contraseña utilizando la nueva contraseña encriptada
			usuarioExistente.setPassword(passwordEncoder.encode(usuarioConNuevaContrasena.getPassword()));
			// No es necesario almacenar la nueva contraseña sin encriptar en el usuario
			// usuarioExistente.setNuevaContrasena(usuarioConNuevaContrasena.getPassword());
			// Guardar el usuario actualizado
			Usuario usuarioActualizado = userDao.save(usuarioExistente);
			return new ResponseEntity<>(usuarioActualizado, HttpStatus.OK);
		} else {
			return new ResponseEntity<>("Error interno del servidor", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}