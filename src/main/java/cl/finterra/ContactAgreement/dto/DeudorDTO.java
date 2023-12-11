package cl.finterra.ContactAgreement.dto;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import cl.finterra.ContactAgreement.entity.Deudor;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DeudorDTO {

	private String rut;
	private String dv;
	private String razonSocial;
	private String direccion;
	private List<ContactoDTO> contactoDeduor;
	private String condicionPago;
	private boolean avanzarJuntos;
	private String formaDePago;
	private String detalleOtro;
	private String condicionAcepacion;
	private String detalleOtroCondicion;
	private String informacionAdicional;
	private LocalDate fecha;

	public void addContactoDeudor(ContactoDTO con) {
		if (this.contactoDeduor == null) {
			this.contactoDeduor = new ArrayList<ContactoDTO>();
		}

		this.contactoDeduor.add(con);

	}

	public DeudorDTO(Deudor deu) {
		
		fecha = deu.getFecha();
		rut = deu.getRut();
		dv = deu.getDv();
		razonSocial = deu.getNombre();
		direccion = deu.getDireccion();
		condicionPago = deu.getCondicionPago();
		avanzarJuntos = deu.isContactarAvanzarJuntos();
		formaDePago = deu.getFormaDepago();
		detalleOtro = deu.getDetalleOtro();
		condicionAcepacion = deu.getCondicionAcepacion();
		detalleOtroCondicion = deu.getDetalleOtroCondicion();
		informacionAdicional = deu.getInformacionAdicional();

		contactoDeduor =  deu.getAgregarEliminar().stream()
				.map(m -> ContactoDTO.builder().correo(m.getCorreo()).direccion(m.getDireccion()).estado(m.getEstado())
						.nombre(m.getNombre()).telefono(m.getTelefono()).build()).collect(Collectors.toList());
	

	}

}
