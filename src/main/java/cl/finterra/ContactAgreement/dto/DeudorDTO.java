package cl.finterra.ContactAgreement.dto;

import cl.finterra.ContactAgreement.entity.Contacto;
import cl.finterra.ContactAgreement.entity.Deudor;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DeudorDTO {

	private String rut;
	private String dv;
	private String razonSocial;
	private String direccion;
	private List<ContactoDTO> contactoDeudor;
	private String condicionPago;
	private boolean avanzarJuntos;
	private String formaDePago;
	private String detalleOtro;
	private String condicionAcepacion;
	private String detalleOtroCondicion;
	private String informacionAdicional;
	private LocalDate fecha;

	public DeudorDTO(Contacto m) {
	}

	public void addContactoDeudor(ContactoDTO con) {
		if (this.contactoDeudor == null) {
			this.contactoDeudor = new ArrayList<ContactoDTO>();
		}

		this.contactoDeudor.add(con);
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

		contactoDeudor =  deu.getAgregarEliminar().stream()
				.map(m -> ContactoDTO.builder().correo(m.getCorreo()).direccion(m.getDireccion()).estado(m.getEstado())
						.nombre(m.getNombre()).telefono(m.getTelefono()).build()).collect(Collectors.toList());


	}


	public void setContactoDeudor(List<ContactoDTO> contactoDeudor) {
		if (contactoDeudor == null) {
			this.contactoDeudor = new ArrayList<>();
		} else {
			this.contactoDeudor = contactoDeudor;
		}
	}



}
