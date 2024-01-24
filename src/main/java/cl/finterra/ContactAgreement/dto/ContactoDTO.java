package cl.finterra.ContactAgreement.dto;

import cl.finterra.ContactAgreement.entity.Contacto;
import lombok.*;

import javax.persistence.Id;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ContactoDTO {
	@Id
	@Getter
	private String id;
	private String name;
	private String email;
	private String phone;
//	private String address;
	private String state;
	private String condition;

	public ContactoDTO(Contacto con) {
		name = con.getName();
		email = con.getEmail();
		phone = con.getPhone();
//		address = con.getAddress();
		state = con.getState();
		condition = con.getCondition();
//		contactoDeudor =  con.getContact().stream()
//				.map(m -> ContactoDTO.builder().email(m.getEmail())*//*.address(m.getAddress())*//*.estado(m.getState())
//						.nombre(m.getName()).telefono(m.getPhone()).build()).collect(Collectors.toList());

	}
	public void setCondicion(String condition) {
		this.condition = condition;
	}

}
