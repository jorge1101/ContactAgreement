package cl.finterra.ContactAgreement.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

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
	@NotBlank(message = "La contraseña no puede estar en blanco")
	@Size(min = 6, message = "La contraseña debe tener al menos 6 caracteres")
	@Pattern(
			regexp = "^(?=.*[0-9].*[0-9].*[0-9].*[0-9])(?=.*[A-Z])(?=.*[a-z])(?=.*\\.).{8,}$",
			message = "La contraseña debe tener al menos 8 caracteres, incluir al menos 1 mayúscula, 1 minúscula, 1 punto y 4 números"
	)
	private String password;
	private String name;
	private String accessToken;
	private String rut;



}

