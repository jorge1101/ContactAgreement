package cl.finterra.ContactAgreement.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import cl.finterra.ContactAgreement.controller.ActualizarRutConHashController;

@RestController
@RequestMapping("actualiza-rut-deudor")
public class ActualizarRutAutomaticamenteRest {

	@Autowired
	private ActualizarRutConHashController controller;
	
	@GetMapping
	public void deudor() {
		System.out.println("preparar para actualizar ");
		controller.actualizar();
	}

}