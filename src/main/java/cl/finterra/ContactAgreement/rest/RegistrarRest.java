package cl.finterra.ContactAgreement.rest;

import cl.finterra.ContactAgreement.Security.JwtTokenProvider;
import cl.finterra.ContactAgreement.controller.AgregarEliminarController;
import cl.finterra.ContactAgreement.entity.Usuario;
import cl.finterra.ContactAgreement.entity.AgregarEliminar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<AgregarEliminarController> register(@RequestBody AgregarEliminarController agregarEliminar) {
        System.out.println("Registro de AgregarEliminar: " + agregarEliminar);

        // Puedes realizar lógica adicional aquí para persistir AgregarEliminar en la base de datos

        // Generar usuario con email y contraseña asociado al AgregarEliminar
        Usuario usuario = Usuario.builder()
                .email(agregarEliminar.getCorreo())
                .password("genera-una-contraseña-aleatoria-o-lógica")
                .build();
        boolean registrationSuccess = AgregarEliminarController.register(agregarEliminar, usuario);

        if (registrationSuccess) {
            String accessToken = tokenProvider.generateAccessToken(usuario.getEmail());
            usuario.setAccessToken(accessToken);
            System.out.println("AccessToken generado: " + accessToken);

            // Devolver el objeto AgregarEliminar modificado en la respuesta
            return ResponseEntity.ok(agregarEliminar);
        } else {
            // Devolver un objeto AgregarEliminar vacío o con datos predeterminados en caso de fallo de registro
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new AgregarEliminarController());
        }
    }
}
