package cl.finterra.ContactAgreement.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "usuario")
public class Usuario {

	@Id
	private String id;

	private String email; //cambiar por correo
	private String password;
	private String accessToken;



}

