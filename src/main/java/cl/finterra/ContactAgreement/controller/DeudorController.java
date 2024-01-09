package cl.finterra.ContactAgreement.controller;

//import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.persistence.EntityNotFoundException;

import cl.finterra.ContactAgreement.dao.ContactoMongoDAO;
import jakarta.mail.MessagingException;
//import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cl.finterra.ContactAgreement.correo.ConfigurarCorreo;
import cl.finterra.ContactAgreement.dao.DeudorMongoDAO;
import cl.finterra.ContactAgreement.dao.RutHashMongoDAO;
import cl.finterra.ContactAgreement.dao.home.DeudorHome;
import cl.finterra.ContactAgreement.dto.ContactoDTO;
import cl.finterra.ContactAgreement.dto.DeudorDTO;
import cl.finterra.ContactAgreement.entity.Contacto;
import cl.finterra.ContactAgreement.entity.Deudor;
import cl.finterra.ContactAgreement.entity.RutConHash;

import lombok.NoArgsConstructor;

@Service
@NoArgsConstructor
public class DeudorController {

	@Autowired
	RutHashMongoDAO rutDao;
	@Autowired
	DeudorMongoDAO deudorMongoDAO;
	@Autowired
	ConfigurarCorreo correo;
	@Autowired
	ContactoMongoDAO contactoMongoDAO;
	public void actualizarContacto(String id, ContactoDTO contactoDTO) {
		Optional<Contacto> deudorOptional = contactoMongoDAO.findById(id);

		if (deudorOptional.isPresent()) {
			Contacto contacto = deudorOptional.get();
			// Aquí deberías implementar la lógica para encontrar y actualizar el contacto en la lista
			// Puedes buscar el contacto por su ID y luego actualizar sus campos con los valores de contactoDTO
			// ...


			contactoMongoDAO.save(contacto);
		} else {
			throw new EntityNotFoundException("No se encontró un deudor con el ID proporcionado");
		}
	}

	public void agregarContacto(String id, ContactoDTO contactoDTO) {
		Optional<Contacto> deudorOptional = contactoMongoDAO.findById(id);

		if (deudorOptional.isPresent()) {
			Contacto deudor = deudorOptional.get();

			// Aquí deberías validar lógica de negocio antes de agregar el contacto

			// Crear el contacto y agregarlo al deudor
			Contacto nuevoContacto = new Contacto(contactoDTO.getNombre(), contactoDTO.getCorreo());

			contactoMongoDAO.save(nuevoContacto);
		} else {
			throw new EntityNotFoundException("No se encontró un deudor con el ID proporcionado");
		}
	}

	public void eliminarContacto(String id, Long contactoId) {
		Optional<Contacto> contactoOptional = contactoMongoDAO.findById(id);


		if (contactoOptional.isPresent()) {
			Contacto contacto = contactoOptional.get();

			contactoMongoDAO.save(contacto);
		} else {
			throw new EntityNotFoundException("No se encontró un deudor con el ID proporcionado");
		}
	}


	public DeudorDTO buscarDeudor(String rutHash) {
		
		if(rutHash.equals("")) {	
			return null;
		}
	  Optional<RutConHash> tem = rutDao.findByRutHash(rutHash);
	  
	  if(!tem.isPresent()) {
		  return null;
	  }
		
		DeudorDTO salida = new DeudorDTO();
		String rut = rutDao.findByRutHash(rutHash).get().getRut();
		DeudorHome home = new DeudorHome();

		salida = home.getDeudorContacto(rut);
//			home.close();

		Optional<Deudor> deu = deudorMongoDAO.findByRut(salida.getRut());

		if (deu.isPresent()) {

			salida.getContactoDeudor().removeIf(r -> deu.get().getAgregarEliminar().stream().anyMatch(a -> {
				return (a.getNombre().equalsIgnoreCase(r.getNombre()) && a.getCorreo().equalsIgnoreCase(r.getCorreo())
						&& a.getTelefono().equals(r.getTelefono()) && a.getDireccion().equals(r.getDireccion()));
			}));

			for (Contacto iterf : deu.get().getAgregarEliminar()) {

				if(iterf.getEstado().equals("eliminar") || iterf.getEstado().equals("nuevo")) {
					salida.addContactoDeudor(ContactoDTO.builder().nombre(iterf.getNombre()).correo(iterf.getCorreo())
							.direccion(iterf.getDireccion()).estado(iterf.getEstado()).telefono(iterf.getTelefono())
							.build());
				}
			}

			salida.setAvanzarJuntos(deu.get().isContactarAvanzarJuntos());
			salida.setCondicionPago(deu.get().getCondicionPago());
			salida.setFormaDePago(deu.get().getFormaDepago());
			salida.setDetalleOtro(deu.get().getDetalleOtro());
			salida.setCondicionAcepacion(deu.get().getCondicionAcepacion());
			salida.setDetalleOtroCondicion(deu.get().getDetalleOtroCondicion());
			salida.setInformacionAdicional(deu.get().getInformacionAdicional());

		}

		return salida;
	}


	public void guardar(DeudorDTO deu) {

		
		Optional<Deudor> deuTem = deudorMongoDAO.findByRut(deu.getRut());
		Deudor deudor;

		if (!deuTem.isPresent()) {
			deudor = new Deudor();
			deudor.setNombre(deu.getRazonSocial());
			deudor.setRut(deu.getRut());

		} else {
			deudor = deuTem.get();
			if (deu.getContactoDeudor() != null) {
				deu.getContactoDeudor();
			}
		}
		deudor.setFormaDepago(deu.getFormaDePago());
		deudor.setContactarAvanzarJuntos(deu.isAvanzarJuntos());
		deudor.setCondicionPago(deu.getCondicionPago());
		deudor.setDireccion(deu.getDireccion());
		deudor.setDetalleOtro(deu.getDetalleOtro());
		deudor.setCondicionAcepacion(deu.getCondicionAcepacion());
		deudor.setDetalleOtroCondicion(deu.getDetalleOtroCondicion());
		deudor.setInformacionAdicional(deu.getInformacionAdicional());

		deu.getContactoDeudor().stream().forEach(t -> {
			deudor.addAgregarEliminar(Contacto.builder().correo(t.getCorreo()).direccion(t.getDireccion())
					.estado(t.getEstado()).nombre(t.getNombre()).telefono(t.getTelefono()).build());
		});

		deudor.setFecha(LocalDate.now());
		this.deudorMongoDAO.save(deudor);
		
		try {
			correo.enviar(deu);
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}


	public List<DeudorDTO> getLista() {
		List<Deudor> tem = this.deudorMongoDAO.findAll();
		tem.forEach(f -> f.getAgregarEliminar().size());
		return tem.stream().map(m -> new DeudorDTO(m)).collect(Collectors.toList());
	}





}
