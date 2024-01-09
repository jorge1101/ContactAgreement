package cl.finterra.ContactAgreement.entity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "deudor")
public class Deudor {
	@Id
	private String id;
	@NotBlank(message = "El rut es obligatorio")
	private String rut;
	private String dv;
	@NotBlank(message = "El campo nombre no puede estar en blanco")
	private String nombre;
	@NotBlank(message = "El campo direccion no puede estar en blanco")
	private String direccion;
	private String rutHash;
	@NotBlank(message = "El campo formaDepago no puede estar en blanco")
	private String formaDepago;
	private String detalleOtro;
	@NotBlank(message = "El campo condicionPago no puede estar en blanco")
	private String condicionPago;
	@NotNull(message = "El campo contactarAvanzarJuntos no puede ser nulo")
	private boolean contactarAvanzarJuntos;
	@NotBlank(message = "El campo condicionAcepacion no puede estar en blanco")
	private String condicionAcepacion;
	@NotBlank(message = "El campo detalleOtroCondicion no puede estar en blanco")
	private String detalleOtroCondicion;
	@Size(max = 1000, message = "El campo informacionAdicional debe tener como máximo {max} caracteres")
	private String informacionAdicional;
	private LocalDate fecha;


	@Field("contacto")
	public List<Contacto> agregarEliminar;

	public void addAgregarEliminar(Contacto item) {
		if(agregarEliminar == null) {
			agregarEliminar = new ArrayList<Contacto>();
		}

		agregarEliminar.add(item);
	}

	public void addAllAgregarEliminar(List<Contacto> items) {
		if(agregarEliminar == null) {
			agregarEliminar = new ArrayList<Contacto>();
		}

		agregarEliminar.addAll(items);
	}


}
