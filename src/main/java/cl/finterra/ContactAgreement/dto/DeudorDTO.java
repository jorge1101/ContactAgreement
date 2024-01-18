package cl.finterra.ContactAgreement.dto;

import cl.finterra.ContactAgreement.entity.Contacto;
import cl.finterra.ContactAgreement.entity.Deudor;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DeudorDTO {
	private String rut;
	private String dv;
	private String companyName;
/*	private String address;*/
	private List<ContactoDTO> contactDeudor;
	private String paymentCondition;
	private boolean avanzarJuntos;
	private String paymentMethod;
	private String detailOther;
	private String acceptanceCondition;
	private String detailOtherCondition;
	private String additionalInformation;
	private LocalDate date;

	public DeudorDTO(Contacto m) {
	}

	public void addContactDeudor(ContactoDTO con) {
		if (this.contactDeudor == null) {
			this.contactDeudor = new ArrayList<ContactoDTO>();
		}

		this.contactDeudor.add(con);
	}

	public DeudorDTO(Deudor deu) {

      //modificar nombres para que coincidan con el front, tambien desde el resto de usos de la funcion
		date = deu.getDate();
		rut = deu.getRut();
		dv = deu.getDv();
		companyName = deu.getCompanyName();
/*		address = deu.getAddress();*/
		paymentCondition = deu.getPaymentCondition();//paymentCondition
		avanzarJuntos = deu.isAvanzarJuntos();
		paymentMethod = deu.getPaymentMethod();
		detailOther = deu.getDetailOther();
		acceptanceCondition = deu.getAcceptanceCondition();
		detailOtherCondition = deu.getDetailOtherCondition();
		additionalInformation = deu.getAdditionalInformation();

		contactDeudor =  deu.getContacts().stream()
				.map(m -> ContactoDTO.builder().email(m.getEmail())/*.address(m.getAddress())*/.state(m.getState())
						.name(m.getName()).phone(m.getPhone()).build()).collect(Collectors.toList());
	}


	public void setContactDeudor(List<ContactoDTO> contactDeudor) {
		if (contactDeudor == null) {
			this.contactDeudor = new ArrayList<>();
		} else {
			this.contactDeudor = contactDeudor;
		}
	}




}
