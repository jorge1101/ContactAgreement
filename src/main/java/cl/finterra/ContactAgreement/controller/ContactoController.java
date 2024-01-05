package cl.finterra.ContactAgreement.controller;

import cl.finterra.ContactAgreement.dao.ContactoMongoDAO;
import cl.finterra.ContactAgreement.dao.UsuarioMongoDAO;
import cl.finterra.ContactAgreement.entity.Contacto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ContactoController {
    @Autowired
    private ContactoMongoDAO agregarEliminarMongoDAO;



    @Autowired
    private UsuarioMongoDAO usuarioService;

    public boolean register(ContactoController nuevoAgregarEliminar) {
        try {

            return true; // El registro fue exitoso
        } catch (Exception e) {
            e.printStackTrace();
            return false; // Hubo un error en el registro
        }
    }

    public Contacto saveAgregarEliminar(Contacto agregarEliminar) {
        return agregarEliminarMongoDAO.save(agregarEliminar);
    }

    public String getCorreo() {
        return null;
    }

    public void deleteById(String id) {
    }
}
