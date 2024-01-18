package cl.finterra.ContactAgreement.controller;

import cl.finterra.ContactAgreement.dao.ContactoMongoDAO;
import cl.finterra.ContactAgreement.dao.UsuarioMongoDAO;
import cl.finterra.ContactAgreement.dto.ContactoDTO;
import cl.finterra.ContactAgreement.dto.DeudorDTO;
import cl.finterra.ContactAgreement.entity.Contacto;
import cl.finterra.ContactAgreement.entity.Deudor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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

            List<Contacto> tem = this.agregarEliminarMongoDAO.findAll();
            tem.forEach(f -> f.getAgregarEliminar().size());
            return tem.stream().map(m -> new ContactoDTO(m)).collect(Collectors.toList()).toString();

    }

    public void deleteById(String id) {
    }
}
