package cl.finterra.ContactAgreement.rest;
import cl.finterra.ContactAgreement.dao.DeudorMongoDAO;
import cl.finterra.ContactAgreement.entity.Deudor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("Deudor")
public class ListarRest {

    @Autowired
    private DeudorMongoDAO DeudorMongoDAO;

    @GetMapping("/list")
    public ResponseEntity<?> listarDeudores() {
        try {
            List<Deudor> deudores = DeudorMongoDAO.findAll();

            if (deudores != null) {
                return ResponseEntity.ok(deudores);  // Devolver la lista de deudores si hay resultados
            } else {
                return ResponseEntity.noContent().build();  // Devolver sin contenido si no hay deudores
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al obtener deudores");
        }
    }
}
