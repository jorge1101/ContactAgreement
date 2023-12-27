package cl.finterra.ContactAgreement.rest;

import cl.finterra.ContactAgreement.dao.UsuarioMongoDAO;
import cl.finterra.ContactAgreement.entity.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
@RestController
@RequestMapping("/usuarios")
public class UpdateUserRest {

    @Autowired
    private UsuarioMongoDAO userDao;

    private void validatePassword(String password, BindingResult result) {
        // Realizar tus validaciones personalizadas aquí
        // Ejemplo: al menos 4 números, un punto y una mayúscula
        // Simplemente como ejemplo, ajusta según tus requisitos
        if (!password.matches("(.*[0-9]){4,}.*") || !password.contains(".") || !password.matches(".*[A-Z].*")) {
            result.addError(new FieldError("Usuario", "password", "La contraseña no cumple con los requisitos mínimos"));
        }
    }

    // Método para obtener detalles detallados de errores de validación
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
        // Verificar si el usuario con el correo electrónico especificado existe
        if (!userDao.existsByEmail(email)) {
            return new ResponseEntity<>("Usuario no encontrado", HttpStatus.NOT_FOUND);
        }

        // Obtener el usuario por correo electrónico
        Usuario usuarioEncontrado = userDao.findByEmail(email).orElse(null);

        if (usuarioEncontrado != null) {
            return new ResponseEntity<>(usuarioEncontrado, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Error interno del servidor", HttpStatus.INTERNAL_SERVER_ERROR);
        }
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
