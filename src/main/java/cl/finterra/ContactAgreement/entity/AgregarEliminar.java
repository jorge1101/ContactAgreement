package cl.finterra.ContactAgreement.entity;

import java.time.LocalDate;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Document(collection = "agregar_eliminar")
public class AgregarEliminar {

	@Id
	private String id;
	private LocalDate fecha;
	private String nombre;
	private String correo;
	private String telefono;
	private String cargo;
	private String direccion;
	private String estado;
}
