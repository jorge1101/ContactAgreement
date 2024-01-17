package cl.finterra.ContactAgreement.rest;

import java.util.List;

import cl.finterra.ContactAgreement.controller.ContactoController;
import cl.finterra.ContactAgreement.dto.ContactoDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import cl.finterra.ContactAgreement.controller.DeudorController;
import cl.finterra.ContactAgreement.dto.DeudorDTO;

import javax.validation.Valid;

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
		// verificacion si los datos son nulos
		if(deu == null) {
			throw new Error();
		}
		//si no es nulo entonces guarda los datos
		deudorController.guardar(deu);
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
