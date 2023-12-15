package cl.finterra.ContactAgreement.controller;

import cl.finterra.ContactAgreement.dao.AgregarEliminarMongoDAO;
import cl.finterra.ContactAgreement.dao.UsuarioMongoDAO;
import cl.finterra.ContactAgreement.entity.AgregarEliminar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AgregarEliminarController {
    @Autowired
    private AgregarEliminarMongoDAO agregarEliminarMongoDAO;



    @Autowired
    private UsuarioMongoDAO usuarioService;

    public boolean register(AgregarEliminarController nuevoAgregarEliminar) {
        try {

            return true; // El registro fue exitoso
        } catch (Exception e) {
            e.printStackTrace();
            return false; // Hubo un error en el registro
        }
    }

    public AgregarEliminar saveAgregarEliminar(AgregarEliminar agregarEliminar) {
        return agregarEliminarMongoDAO.save(agregarEliminar);
    }

    public String getCorreo() {
        return null;
    }

    public void deleteById(String id) {
    }
}
