package cl.finterra.ContactAgreement.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
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

}
