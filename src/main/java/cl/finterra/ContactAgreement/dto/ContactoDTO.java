package cl.finterra.ContactAgreement.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ContactoDTO {

	private String nombre;
	private String correo;
	private String telefono;
	private String direccion;
	private String estado;
	
	
}
