package cl.finterra.ContactAgreement.rest;

import cl.finterra.ContactAgreement.Security.JwtTokenProvider;
import cl.finterra.ContactAgreement.controller.AgregarEliminarController;
import cl.finterra.ContactAgreement.entity.AgregarEliminar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("agregar-eliminar")
public class RegistrarRest {

    @Autowired
    private AgregarEliminarController AgregarEliminarController;

    private JwtTokenProvider tokenProvider;

    public void AgregarEliminarRest(JwtTokenProvider tokenProvider) {
        this.tokenProvider = tokenProvider;
    }

    public RegistrarRest(JwtTokenProvider tokenProvider) {
        this.tokenProvider = tokenProvider;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody AgregarEliminar nuevoAgregarEliminar) {
        System.out.println("Registro de AgregarEliminar: " + nuevoAgregarEliminar);

        // ... (lógica adicional para persistir AgregarEliminar en la base de datos)

        // Guardar en la base de datos utilizando el método save del repositorio
        AgregarEliminar savedAgregarEliminar = AgregarEliminarController.saveAgregarEliminar(nuevoAgregarEliminar);

        if (savedAgregarEliminar != null) {
            String accessToken = tokenProvider.generateAccessToken(savedAgregarEliminar.getCorreo());
            System.out.println("AccessToken generado: " + accessToken);

            // Devolver el objeto AgregarEliminar modificado en la respuesta
            return ResponseEntity.ok(savedAgregarEliminar);
        } else {
            // Devolver un objeto AgregarEliminar vacío o con datos predeterminados en caso de fallo de registro
            System.out.println("Fallo de registro. No se pudo registrar.");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new AgregarEliminar());
        }
    }

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

}
