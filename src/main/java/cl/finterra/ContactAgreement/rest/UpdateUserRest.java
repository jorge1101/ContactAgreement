package cl.finterra.ContactAgreement.rest;

import cl.finterra.ContactAgreement.dao.UsuarioMongoDAO;
import cl.finterra.ContactAgreement.entity.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
@RestController
@RequestMapping("/usuarios")
public class UpdateUserRest {

    @Autowired
    private UsuarioMongoDAO userDao;

    @PutMapping("/{id}/actualizar-contrasena")
    public ResponseEntity<Usuario> actualizarContrasena(@PathVariable String id, @RequestBody Usuario usuarioConNuevaContrasena) {
        // Verifica si el usuario con la ID especificada existe
        if (!userDao.existsById(id)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        // Obtiene el usuario existente
        Usuario usuarioExistente = userDao.findById(id).orElse(null);

        if (usuarioExistente != null) {
            // Actualiza la contraseña
            usuarioExistente.setPassword(usuarioConNuevaContrasena.getPassword());

            // Guarda el usuario actualizado
            Usuario usuarioActualizado = userDao.save(usuarioExistente);

            return new ResponseEntity<>(usuarioActualizado, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
