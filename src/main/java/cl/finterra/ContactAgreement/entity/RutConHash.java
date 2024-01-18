package cl.finterra.ContactAgreement.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "rutConHash")
public class RutConHash {
	@Id
	private String id;
	private String rut;
	private String rutHash;

	public RutConHash(String t, String s) {
	}
}