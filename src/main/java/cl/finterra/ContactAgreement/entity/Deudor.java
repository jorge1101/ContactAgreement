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

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "deudor")
public class Deudor {
	@Id
	private String id;
	private String rut;
	private String dv;
	private String nombre;
	private String direccion;
	private String rutHash;
	private String formaDepago;
	private String detalleOtro;
	private String condicionPago;
	private boolean contactarAvanzarJuntos;
	private String condicionAcepacion;
	private String detalleOtroCondicion;
	private String informacionAdicional;
	private LocalDate fecha;

	@Field("agregarEliminar")
	public List<AgregarEliminar> agregarEliminar;

	public void addAgregarEliminar(AgregarEliminar item) {
		if(agregarEliminar == null) {
			agregarEliminar = new ArrayList<AgregarEliminar>();
		}

		agregarEliminar.add(item);
	}

	public void addAllAgregarEliminar(List<AgregarEliminar> items) {
		if(agregarEliminar == null) {
			agregarEliminar = new ArrayList<AgregarEliminar>();
		}

		agregarEliminar.addAll(items);
	}
}
