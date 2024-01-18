package cl.finterra.ContactAgreement.controller;

//import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.persistence.EntityNotFoundException;

import cl.finterra.ContactAgreement.dao.ContactoMongoDAO;
import io.micrometer.common.util.StringUtils;
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

	private DeudorDTO deudorDTO;
	public void updateContact(String id, ContactoDTO contactoDTO) {
		Optional<Contacto> deudorOptional = contactoMongoDAO.findById(id);

		if (deudorOptional.isPresent()) {
			Contacto contacto = deudorOptional.get();
			// Aquí deberías implementar la lógica para encontrar y actualizar el contacto en la lista
			// Puedes buscar el contacto por su ID y luego actualizar sus campos con los valores de contactoDTO
			contactoMongoDAO.save(contacto);
		} else {
			throw new EntityNotFoundException("No se encontró un deudor con el ID proporcionado");
		}
	}

	public void addContact(String id, ContactoDTO contactoDTO) {
		Optional<Contacto> deudorOptional = contactoMongoDAO.findById(id);

		if (deudorOptional.isPresent()) {
			Contacto deudor = deudorOptional.get();
			// Aquí deberías validar lógica de negocio antes de agregar el contacto
			// Crear el contacto y agregarlo al deudor
			Contacto nuevoContacto = new Contacto(contactoDTO.getName(), contactoDTO.getEmail());
			contactoMongoDAO.save(nuevoContacto);
		} else {
			throw new EntityNotFoundException("No se encontró un deudor con el ID proporcionado");
		}
	}

	public void deleteContact(String id, Long contactoId) {
		Optional<Contacto> contactoOptional = contactoMongoDAO.findById(id);
		if (contactoOptional.isPresent()) {
			Contacto contacto = contactoOptional.get();
			contactoMongoDAO.save(contacto);
		} else {
			throw new EntityNotFoundException("No se encontró un deudor con el ID proporcionado");
		}
	}

	public DeudorDTO findDeudor(String rut) {
		// Validación para asegurar que el rut no sea nulo o vacío
		if (StringUtils.isBlank(rut)) {
			return null;
		}
		Optional<Deudor> deu = deudorMongoDAO.findByRut(rut);
		if (deu.isPresent()) {
			DeudorDTO salida = new DeudorDTO();
			DeudorHome home = new DeudorHome();
			salida = home.getDeudorContacto(rut);

			salida.getContactDeudor().removeIf(r -> deu.get().getContacts().stream().anyMatch(a ->
					a.getName().equalsIgnoreCase(r.getName()) &&
							a.getEmail().equalsIgnoreCase(r.getEmail()) &&
							a.getPhone().equals(r.getPhone()) //&&
							/*a.getAddress().equals(r.getAddress())*/
			));
			for (Contacto iterf : deu.get().getContacts()) {
				if (iterf.getState().equals("delete") || iterf.getState().equals("new")) {
					salida.addContactDeudor(ContactoDTO.builder()
							.name(iterf.getName())
							.email(iterf.getEmail())
							/*.address(iterf.getAddress())*/
							.state(iterf.getState())
							.phone(iterf.getPhone())
							.build());
				}
			}
			salida.setAvanzarJuntos(deu.get().isAvanzarJuntos());
			salida.setPaymentCondition(deu.get().getPaymentCondition());
			salida.setPaymentMethod(deu.get().getPaymentMethod());
			salida.setDetailOther(deu.get().getDetailOther());
			salida.setAcceptanceCondition(deu.get().getAcceptanceCondition());
			salida.setDetailOtherCondition(deu.get().getDetailOtherCondition());
			salida.setAdditionalInformation(deu.get().getAdditionalInformation());
			return salida;
		}
		return null; // Retornar null si no se encuentra el Deudor en la base de datos
	}

	public void guardar(DeudorDTO deu) {
		
		Optional<Deudor> deuTem = deudorMongoDAO.findByRut(deu.getRut());
		Deudor deudor;
		if (!deuTem.isPresent()) {
			deudor = new Deudor();
			deudor.setCompanyName(deu.getCompanyName());
			deudor.setRut(deu.getRut());
		} else {
			deudor = deuTem.get();
			if (deu.getContactDeudor() != null) {
				deu.getContactDeudor();
			}
		}
		deudor.setPaymentMethod(deu.getPaymentMethod());
		deudor.setAvanzarJuntos(deu.isAvanzarJuntos());
		deudor.setPaymentCondition(deu.getPaymentCondition());
		/*deudor.setAddress(deu.getAddress());*/
		deudor.setDetailOther(deu.getDetailOther());
		deudor.setAcceptanceCondition(deu.getAcceptanceCondition());
		deudor.setDetailOtherCondition(deu.getDetailOtherCondition());
		deudor.setAdditionalInformation(deu.getAdditionalInformation());

		deu.getContactDeudor().stream().forEach(t -> {
			deudor.addContact(Contacto.builder().email(t.getEmail())/*.address(t.getAddress())*/
					.state(t.getState()).name(t.getName()).phone(t.getPhone()).build());
		});

		deudor.setDate(LocalDate.now());
		this.deudorMongoDAO.save(deudor);

//		try {
//			correo.enviar(deu);
//		} catch (MessagingException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}

	}

	public List<DeudorDTO> getLista() {
		List<Deudor> tem = this.deudorMongoDAO.findAll();
		tem.forEach(f -> f.getContacts().size());
		return tem.stream().map(m -> new DeudorDTO(m)).collect(Collectors.toList());
	}

    public DeudorDTO getDeudor(DeudorDTO deu){
		Optional<Deudor> deudorRut = deudorMongoDAO.findByRut(deu.getRut());
		return null;
	}


}
