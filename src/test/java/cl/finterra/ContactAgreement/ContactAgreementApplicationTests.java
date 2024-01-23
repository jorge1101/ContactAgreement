package cl.finterra.ContactAgreement;


import cl.finterra.ContactAgreement.controller.DeudorController;
import cl.finterra.ContactAgreement.dto.ContactoDTO;
import cl.finterra.ContactAgreement.dto.DeudorDTO;
import cl.finterra.ContactAgreement.rest.DeudorRest;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;

@SpringBootTest
@Slf4j
class ContactAgreementApplicationTests {


	@Test   //testeo con log.info para ver la ddifererencia de velocidad y de funciones
	void contextLoads() {
		double time = System.currentTimeMillis();
		for (int i = 0; i < 5000; i++) {
			log.info("prueba");
		}
		log.info(Double.toString(System.currentTimeMillis()-time));
	}

	@Test  //testing con sout para probar las velocidades de respuesta de cada una
	void contextLoads2() {
		double time = System.currentTimeMillis();
		for (int i = 0; i < 5000; i++) {
			System.out.println("n° "+i);
		}
		log.info(Double.toString(System.currentTimeMillis()-time));
	}
	@Mock
	private DeudorController deudorController;

	@InjectMocks
	private DeudorRest deudorRest;

	@Test
	public void testGuardarDeudor() {
		// Configuración de datos de prueba
		DeudorDTO deudorDTO = new DeudorDTO();
		deudorDTO.setRut("20.917.258");
		deudorDTO.setCompanyName("finterra");

		// Configuración del controlador mock
		Mockito.when(deudorController.guardarDeudor(any(ContactoDTO.class)))
				.thenReturn(deudorDTO);

		// Ejecución de la prueba
		ResponseEntity<DeudorDTO> responseEntity = deudorRest.guardar(deudorDTO);

		// Verificación de resultados
		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
		assertEquals(deudorDTO, responseEntity.getBody());
	}

//	@Test
//	public void testUpdateState() {
//		// Configuración de datos de prueba
//		Long deudorId = 1L;
//		List<ContactoDTO> nuevosContactos = new ArrayList<>();
//
//		// Configuración del controlador mock
//		DeudorDTO deudorDTO = new DeudorDTO();
//		Mockito.when(DeudorDTO.obtenerDeudorPorId(deudorId))
//				.thenReturn(deudorDTO);
//		Mockito.when(deudorController.guardarDeudor(any(ContactoDTO.class)))
//				.thenReturn(deudorDTO);
//
//		// Ejecución de la prueba
//		ResponseEntity<DeudorDTO> responseEntity = deudorRest.updateState(deudorId, nuevosContactos);
//
//		// Verificación de resultados
//		assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
//		assertEquals(deudorDTO, responseEntity.getBody());
//	}
}
