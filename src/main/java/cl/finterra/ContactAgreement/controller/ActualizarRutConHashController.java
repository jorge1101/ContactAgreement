package cl.finterra.ContactAgreement.controller;

//import java.sql.SQLException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cl.finterra.ContactAgreement.dao.RutHashMongoDAO;
import cl.finterra.ContactAgreement.dao.home.DeudorHome;
import cl.finterra.ContactAgreement.entity.RutConHash;

@Service
public class ActualizarRutConHashController {

	@Autowired
	private RutHashMongoDAO rutDao;
	public void actualizar() {
		List<String> tem = null;
		List<RutConHash> listaAGuardar = new ArrayList<RutConHash>();
		DeudorHome home = new DeudorHome();
//		home.conect();
		tem = home.getRutClienteDeudor();
//			home.close();
//				catch (SQLException e) {
//			home.close();
//			e.printStackTrace();
//		}
		System.out.println("total de home " + tem.size()  );
//		TODO: mejorar este codigo para que no consuma tantos recursos
	List<String> rut = rutDao.findAll().stream().map(m -> m.getRut()).collect(Collectors.toList());
	System.out.println("total guaraddo " + rut.size());
	tem.removeIf(r -> rut.contains(r));
	System.out.println("a actualizar " + tem.size());

	if (tem.size() > 0) {
		tem.stream().forEach(t -> {
			listaAGuardar.add(new RutConHash(t, DigestUtils.sha256Hex(t)));
		});
		rutDao.saveAll(listaAGuardar) ;
	}
	System.out.println("actualizacion lista");}
}
