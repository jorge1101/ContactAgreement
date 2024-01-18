package cl.finterra.ContactAgreement.rest;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import cl.finterra.ContactAgreement.dto.ContactoDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import cl.finterra.ContactAgreement.controller.DeudorController;
import cl.finterra.ContactAgreement.dto.DeudorDTO;
import java.time.LocalDate;
@RestController
@RequestMapping("deudor")
public class DeudorRest {
	
	@Autowired
	DeudorController deudorController;
	@GetMapping("/{rut}")
	public ResponseEntity<DeudorDTO> deudor(@PathVariable String rut) {
		return ResponseEntity.ok(deudorController.buscarDeudor(rut));
	}


	@PostMapping
	public void guardar(@RequestBody DeudorDTO deu) {
		// Verificación si los datos son nulos
		if(deu == null) {
			throw new Error();
		}
		// Si no es nulo, entonces guarda los datos
		deudorController.guardar(deu);
		//retorna como respuesta el deudor guardado para evitar el error de syntax(solo si es necesario)
//		return ResponseEntity.ok(deu);
	}

	@GetMapping("/{id}")
	public ResponseEntity<DeudorDTO> getDeudor(@PathVariable String id) {
		DeudorDTO deudorDTO = new DeudorDTO();
		deudorDTO.setRut("20.917.258-");
		deudorDTO.setDv("1");
		deudorDTO.setCompanyName("");
		List<ContactoDTO> contactos = new ArrayList<>();
		ContactoDTO contacto1 = new ContactoDTO();
		contacto1.setName("Company");
		contacto1.setEmail("ctm123@gmail.com");
		contacto1.setPhone("934567866");
		contacto1.setState("new");
		contactos.add(contacto1);
		List<ContactoDTO> contactos2 = new ArrayList<>();
		ContactoDTO contacto2 = new ContactoDTO();
		contacto2.setName("Finterra");
		contacto2.setEmail("ctm2@gmail.com");
		contacto2.setPhone("934567866");
		contacto2.setState("new");
		contactos2.add(contacto2);
		deudorDTO.setPaymentCondition("30");
		deudorDTO.setPaymentMethod("EMISION VALE VISTA");
		deudorDTO.setDetailOther("");
		deudorDTO.setAcceptanceCondition("8");
		deudorDTO.setDetailOtherCondition("");
		deudorDTO.setAdditionalInformation("hola 🕺");
		return ResponseEntity.ok(deudorDTO);
	}

	@GetMapping
	public ResponseEntity<List<DeudorDTO>> getListaPendiente() {
			//consigue la lista desde el DTO de Deudor
		return ResponseEntity.ok(deudorController.getLista());
	}

	@PostMapping("/{id}/agregar-contacto")
	public ResponseEntity<Void> agregarContacto(@PathVariable String id, @RequestBody ContactoDTO contactoDTO) {
		deudorController.agregarContacto(id, contactoDTO);
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}

	@PutMapping("/{id}/eliminar-contacto/{contactoId}")
	public ResponseEntity<Void> eliminarContacto(@PathVariable String id, @PathVariable Long contactoId) {
		deudorController.eliminarContacto(id, contactoId);
		return ResponseEntity.noContent().build();
	}

}
