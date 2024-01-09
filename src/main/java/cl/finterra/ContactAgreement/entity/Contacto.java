package cl.finterra.ContactAgreement.entity;

import java.time.LocalDate;
import java.util.Collection;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Pattern;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Document(collection = "contacto")
public class Contacto {

	@NotNull
	@PastOrPresent
	private LocalDate fecha;

	@NotBlank
	private String nombre;

	@NotBlank
	@Pattern(regexp = "^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$", message = "Correo inválido")
	private String correo;

	@NotBlank(message = "Teléfono no puede estar en blanco")
	@Pattern(regexp = "9\\d{8}", message = "Teléfono inválido, debe comenzar con 9 y tener 9 números")
	private String telefono;

	private String cargo;

	@NotBlank
	private String direccion;

	@NotBlank
	private String estado;

	private String ContactoDeudor;

	public Contacto(String nombre, String correo) {
	}

	public Collection<Object> getAgregarEliminar() {
        return null;
    }
}
