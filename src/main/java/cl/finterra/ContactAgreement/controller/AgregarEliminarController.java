package cl.finterra.ContactAgreement.controller;

import cl.finterra.ContactAgreement.entity.AgregarEliminar;
import cl.finterra.ContactAgreement.entity.Usuario;
import cl.finterra.ContactAgreement.dao.AgregarEliminarMongoDAO;
import cl.finterra.ContactAgreement.dao.UsuarioMongoDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class AgregarEliminarController {

    @Autowired
    private AgregarEliminarMongoDAO agregarEliminarController;

    @Autowired
    private UsuarioMongoDAO usuarioService;

    public boolean register(AgregarEliminarController agregarEliminar, Usuario usuario) {
        try {
            // Asumiendo que tienes servicios para manejar la lógica de persistencia en la base de datos
            agregarEliminar.saveAgregarEliminar(agregarEliminar);

            return true; // El registro fue exitoso
        } catch (Exception e) {
            e.printStackTrace();
            return false; // Hubo un error en el registro
        }
    }

    private void saveAgregarEliminar(AgregarEliminarController agregarEliminar) {
    }

    public String getCorreo() {
        return null;
    }

    public void deleteById(String id) {
    }
}
