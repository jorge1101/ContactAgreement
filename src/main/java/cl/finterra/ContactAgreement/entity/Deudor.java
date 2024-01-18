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
	private String companyName;
/*	@NotBlank(message = "El campo direccion no puede estar en blanco")*/
	/*private String address;*/
	private String rutHash;
	@NotBlank(message = "El campo formaDepago no puede estar en blanco")
	private String paymentMethod;
	private String detailOther;
	@NotBlank(message = "El campo condicionPago no puede estar en blanco")
	private String paymentCondition;
	@NotNull(message = "El campo contactarAvanzarJuntos no puede ser nulo")
	private boolean AvanzarJuntos;
	@NotBlank(message = "El campo condicionAcepacion no puede estar en blanco")
	private String acceptanceCondition;
	@NotBlank(message = "El campo detalleOtroCondicion no puede estar en blanco")
	private String detailOtherCondition;
	@Size(max = 1000, message = "El campo informacionAdicional debe tener como máximo {max} caracteres")
	private String additionalInformation;
	private LocalDate date;


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
