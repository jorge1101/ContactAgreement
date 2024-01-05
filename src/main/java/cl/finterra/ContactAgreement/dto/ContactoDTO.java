package cl.finterra.ContactAgreement.dto;

import lombok.*;

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
	
	
}
