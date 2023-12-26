package cl.finterra.ContactAgreement.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "usuario")
public class Usuario {

	@Id
	private String id;

	@NotBlank(message = "El email es obligatorio")
	@Email(message = "El email debe ser válido")
	private String email;

	@NotBlank(message = "La contraseña es obligatoria")
	private String password;
	private String accessToken;



}

