package cl.finterra.ContactAgreement.dao.home;

import cl.finterra.ContactAgreement.dto.ContactoDTO;
import cl.finterra.ContactAgreement.dto.DeudorDTO;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

public class DeudorHome {

	private final MongoClient mongoClient;
	private final MongoDatabase database;

	public DeudorHome() {
		this.mongoClient = MongoClients.create("mongodb://localhost:27017");
		this.database = mongoClient.getDatabase("ContactPyme");
	}

	public List<String> getRutClienteDeudor() {
		List<String> salida = new ArrayList<>();
		MongoCollection<Document> clientesCollection = database.getCollection("clientes");

//		try (MongoCursor<Document> cursor = clientesCollection.distinct("CL_RUT", String.class).iterator()) {
//			while (cursor.hasNext()) {
//				salida.add(cursor.next());
//			}
//		}

		return salida;
	}

	public DeudorDTO getDedorContacto(String rut) {
		DeudorDTO doc = getDeudor(rut);
		List<ContactoDTO> listContacto = new ArrayList<>();

		MongoCollection<Document> contactosCollection = database.getCollection("contactos_deudor");

		try (MongoCursor<Document> cursor = contactosCollection.find(new Document("cl_rut", rut)).iterator()) {
			while (cursor.hasNext()) {
				Document contactDoc = cursor.next();
				ContactoDTO tem = ContactoDTO.builder()
						.correo(contactDoc.getString("cde_mail"))
						.telefono(contactDoc.getString("cde_fono"))
						.direccion(contactDoc.getString("cde_dir"))
						.nombre(contactDoc.getString("cde_nombre")).build();
				listContacto.add(tem);
			}
		}

		doc.setContactoDeduor(listContacto);
		return doc;
	}

	private DeudorDTO getDeudor(String rut) {
		DeudorDTO deu = null;
		MongoCollection<Document> clientesCollection = database.getCollection("clientes");

		Document query = new Document("CL_RUT", rut);

		try (MongoCursor<Document> cursor = clientesCollection.find(query).iterator()) {
			if (cursor.hasNext()) {
				Document result = cursor.next();
				deu = new DeudorDTO();
				deu.setDireccion(result.getString("CL_DIREC"));
				deu.setRut(rut);
				deu.setDv(result.getString("CL_DIG"));

				String nombre = result.getString("CL_NOMBRE");
				String apePat = result.getString("CL_APEPAT");
				String apeMat = result.getString("CL_APEMAT");

				// Construir el nombre según CL_TIPPER
				if ("N".equals(result.getString("CL_TIPPER"))) {
					deu.setRazonSocial(apePat + " " + apeMat + " " + nombre);
				} else if ("J".equals(result.getString("CL_TIPPER"))) {
					deu.setRazonSocial(apePat + " " + apeMat + nombre);
				} else {
					deu.setRazonSocial("Tipo No Válido");
				}
			}
		}

		return deu;
	}
}
