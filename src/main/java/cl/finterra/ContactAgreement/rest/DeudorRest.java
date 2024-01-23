package cl.finterra.ContactAgreement.rest;

import java.io.ByteArrayOutputStream;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import cl.finterra.ContactAgreement.dto.ContactoDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import cl.finterra.ContactAgreement.controller.DeudorController;
import cl.finterra.ContactAgreement.dto.DeudorDTO;
import java.time.LocalDate;
import java.util.zip.GZIPOutputStream;

@RestController
@RequestMapping("deudor")
public class DeudorRest {
	
	@Autowired
	DeudorController deudorController;
//	@GetMapping("/{rut}")
//	public ResponseEntity<DeudorDTO> deudor(@PathVariable String rut) {
//		return ResponseEntity.ok(deudorController.findDeudor(rut));
//	}
	public static final ContactoDTO contactoDTO = new ContactoDTO();
	@PostMapping
	public  ResponseEntity<DeudorDTO> guardar(@RequestBody DeudorDTO deu) {
		// Verificación si los datos son nulos
		if(deu == null ) {
			throw new Error();
		}
		// Si no es nulo, entonces guarda los datos
		deudorController.guardar(deu);
		return ResponseEntity.ok(deu);
	}
	/*
	@PostMapping
	public ResponseEntity<DeudorDTO> guardar(@RequestBody DeudorDTO deudorDTO) {
		// Verificación si los datos son nulos
		if (deudorDTO == null || deudorDTO.getContactDeudor() == null) {
			throw new Error("Los datos del deudor o los contactos son nulos.");
		}

		// Verificar duplicados en la lista de contactos
		List<ContactoDTO> contactosGuardados = deudorDTO.getContactDeudor();
		for (ContactoDTO nuevoContacto : deudorDTO.getContactDeudor()) {
			if (contactosGuardados.stream().anyMatch(
					c -> c.getId().equals(nuevoContacto.getId())
							&& c.getName().equals(nuevoContacto.getName())
							&& c.getPhone().equals(nuevoContacto.getPhone())
			)) {
				// Manejar duplicados según tu lógica
				System.out.println("qweewcdww");
//				throw new DuplicadosException("Ya existe un contacto con la misma ID, nombre y teléfono en la lista.");
			}
		}
		/*
		    public DuplicadosException(String mensaje) {
        super(mensaje);
    }
	 Si no hay duplicados, entonces guardar los datos
		deudorController.guardar(deudorDTO);
		return ResponseEntity.ok(deudorDTO);
}
	 */
	public static String toInitcap(String input) {
		if (input == null || input.isEmpty()) {
			return input;
		}

		// primer carácter en mayusculas
		return Character.toUpperCase(input.charAt(0)) + input.substring(1);
	}

	@PutMapping("/{id}/state")
	public ResponseEntity<DeudorDTO> updateState(@PathVariable Long id, @RequestBody List<ContactoDTO> nuevosContactos) {

		DeudorDTO deudorDTO = DeudorDTO.obtenerDeudorPorId(id);

		// Modificar el estado de la lista de contactos en el DeudorDTO
		deudorDTO.setContactos(nuevosContactos);

		// Guardar el DeudorDTO actualizado
		DeudorDTO deudorActualizado = deudorController.guardarDeudor(contactoDTO);

		return new ResponseEntity<>(deudorActualizado, HttpStatus.OK);
	}
	@GetMapping("/{id}")
	public ResponseEntity<byte[]> getDeudor(@PathVariable String id) {

		DeudorDTO deudorDTO = new DeudorDTO();
		deudorDTO.setDv("1");
		deudorDTO.setRut("20.917.258");
		deudorDTO.setCompanyName(toInitcap("finterra"));
		deudorDTO.setPaymentCondition("30");
		deudorDTO.setPaymentMethod("EMISIÓN VALE VISTA");
		deudorDTO.setDetailOther("");
		deudorDTO.setAcceptanceCondition("8");
		deudorDTO.setDetailOtherCondition("");
		deudorDTO.setAvanzarJuntos(true);
		deudorDTO.setAdditionalInformation("hola 🕺");
		List<ContactoDTO> Contacto = new ArrayList<>();
		ContactoDTO contacto = new ContactoDTO();
		contacto.setId("id1");
		contacto.setName("CompanyName");
		contacto.setEmail("ctm123@gmail.com");
		contacto.setPhone("967543233");
		contacto.setState("new");
		Contacto.add(contacto);
		deudorDTO.setContactDeudor(Contacto);
		try {
			// Convierte deudorDTO a JSON
			String jsonDeudor = convertirDeudorDTOaJSON(deudorDTO);

			// Comprime el JSON
			byte[] datosComprimidos = comprimirDatos(jsonDeudor);

			// Configura las cabeceras para indicar que la respuesta está comprimida
			HttpHeaders headers = new HttpHeaders();
			headers.set("Content-Encoding", "gzip");

			// Retorna la respuesta comprimida
			return new ResponseEntity<>(datosComprimidos, headers, HttpStatus.OK);
		} catch (Exception e) {
			// Maneja errores
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	private String convertirDeudorDTOaJSON(DeudorDTO deudorDTO) {
		try {
			//  convertir el objeto a JSON
			ObjectMapper objectMapper = new ObjectMapper();
			return objectMapper.writeValueAsString(deudorDTO);
		} catch (Exception e) {
			// Maneja errores
			e.printStackTrace();
			return "";
		}
	}
	//funcion que comprime los datos
	private byte[] comprimirDatos(String datos) throws Exception {
		ByteArrayOutputStream byteOutputStream = new ByteArrayOutputStream();
		try (GZIPOutputStream gzipOutputStream = new GZIPOutputStream(byteOutputStream)) {
			gzipOutputStream.write(datos.getBytes("UTF-8"));
		}
		return byteOutputStream.toByteArray();
	}

	@GetMapping
	public ResponseEntity<List<DeudorDTO>> getListaPendiente() {
			//consigue la lista desde el DTO de Deudor
		return ResponseEntity.ok(deudorController.getLista());
	}
    //funcion que agrega contactos
	@PostMapping("/{id}/agregar-contacto")
	public ResponseEntity<Void> addContact(@PathVariable String id, @RequestBody ContactoDTO contactoDTO) {
		deudorController.addContact(id, contactoDTO);
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}
	//funcion que elimina contactos
	@PutMapping("/{id}/eliminar-contacto/{contactoId}")
	public ResponseEntity<Void> deleteContact(@PathVariable String id, @PathVariable Long contactoId) {
		deudorController.deleteContact(id, contactoId);
		return ResponseEntity.noContent().build();
	}

}
