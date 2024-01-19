package cl.finterra.ContactAgreement.rest;

import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import cl.finterra.ContactAgreement.correo.ConfigurarCorreo;
import cl.finterra.ContactAgreement.dto.DeudorDTO;
@RestController
@RequestMapping("version")
public class VersionRest {
	
	@Autowired
	ConfigurarCorreo correo;

	@GetMapping
	public ResponseEntity<String> login() {

		DeudorDTO t = new DeudorDTO();
		t.setCompanyName("raxzon");
		t.setRut("34738467384");
		t.setDv("7");//pendientes los comentados
		try {
			correo.enviar(t);
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return ResponseEntity.ok("Verson 1.7");
	}
	@GetMapping(value = "correo")
	public ResponseEntity<String> correo() {
		DeudorDTO t = new DeudorDTO();
		t.setCompanyName("razon");
		t.setRut("34738467384");
		t.setDv("7");
		try {
			correo.enviar(t);
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ResponseEntity.ok("enviado...");
	}
	
	
	
	
	
}
