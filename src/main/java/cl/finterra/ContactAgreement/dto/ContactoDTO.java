package cl.finterra.ContactAgreement.dto;

import cl.finterra.ContactAgreement.entity.Contacto;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ContactoDTO {
    @Getter
	private String name;
	@Getter
	private String email;
	@Getter
	private String phone;
	@Getter
	private String address;
	@Getter
	private String state;

	public ContactoDTO(Contacto con) {
		name = con.getName();
		email = con.getEmail();
		phone = con.getPhone();
		address = con.getAddress();
		state = con.getState();

//		contactoDeudor =  con.getAgregarEliminar().stream()
//				.map(m -> ContactoDTO.builder().correo(m.getCorreo()).direccion(m.getDireccion()).estado(m.getEstado())
//						.nombre(m.getNombre()).telefono(m.getTelefono()).build()).collect(Collectors.toList());

	}





}
