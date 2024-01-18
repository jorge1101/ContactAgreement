package cl.finterra.ContactAgreement.entity;

import java.time.LocalDate;
import java.util.Collection;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Pattern;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Document(collection = "contact")
public class Contacto {
	@Id
	private String id;
	@NotNull
	@PastOrPresent
	private LocalDate date;
	@NotBlank
	private String name;
	@NotBlank
	@Pattern(regexp = "^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$", message = "Correo inválido")
	private String email;
	@Pattern(regexp = "9\\d{8}", message = "Teléfono inválido, debe comenzar con 9 y tener 9 números")
	private String phone;
	private String charge;
	private String address;
	private String state;
	private String ContactDeudor;

	public Contacto(String name, String email) {
	}

	public Collection<Object> getContact() {
        return null;
    }
}
