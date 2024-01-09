package cl.finterra.ContactAgreement.dto;

import cl.finterra.ContactAgreement.controller.ContactoController;
import cl.finterra.ContactAgreement.entity.Contacto;
import cl.finterra.ContactAgreement.entity.Deudor;
import lombok.*;

import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ContactoDTO {
    @Getter
	private String nombre;
	@Getter
	private String correo;
	@Getter
	private String telefono;
	@Getter
	private String direccion;
	@Getter
	private String estado;

	public ContactoDTO(Contacto con) {
		nombre = con.getNombre();
		correo = con.getCorreo();
		telefono = con.getTelefono();
		direccion = con.getDireccion();
		estado = con.getEstado();

//		contactoDeudor =  con.getAgregarEliminar().stream()
//				.map(m -> ContactoDTO.builder().correo(m.getCorreo()).direccion(m.getDireccion()).estado(m.getEstado())
//						.nombre(m.getNombre()).telefono(m.getTelefono()).build()).collect(Collectors.toList());

	}





}
