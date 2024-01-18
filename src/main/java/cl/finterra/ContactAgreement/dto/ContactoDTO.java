package cl.finterra.ContactAgreement.dto;

import cl.finterra.ContactAgreement.entity.Contacto;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ContactoDTO {
	private String id;
	private String name;
	private String email;
	private String phone;
/*	private String address;*/
	private String state;

	public ContactoDTO(Contacto con) {

		name = con.getName();
		email = con.getEmail();
		phone = con.getPhone();
	/*	address = con.getAddress();*/
		state = con.getState();

/*		contactoDeudor =  con.getContact().stream()
				.map(m -> ContactoDTO.builder().email(m.getEmail())*//*.address(m.getAddress())*//*.estado(m.getState())
						.nombre(m.getName()).telefono(m.getPhone()).build()).collect(Collectors.toList());*/

	}


}
