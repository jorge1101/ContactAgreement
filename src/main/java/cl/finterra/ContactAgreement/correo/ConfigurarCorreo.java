package cl.finterra.ContactAgreement.correo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import cl.finterra.ContactAgreement.dto.DeudorDTO;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Component
public class ConfigurarCorreo {


	public JavaMailSender emailSender;

	public void enviar(DeudorDTO deu) throws MessagingException {

		StringBuilder body = new StringBuilder();

		body.append("<h3>Reporte de registro de pagadores </h3>");
		body.append("<p>Nombre: " + deu.getRazonSocial() + " </p>");
		body.append("<p>Rut: " + deu.getRut() + "-" + deu.getDv() + " </p>");
		body.append("<p>Condicion Acepacion: " + deu.getCondicionAcepacion() + " </p>");
		body.append("<p>Condicion de Pago: " + deu.getCondicionPago() + " </p>");
		body.append("<p>Forma de Pago: " + deu.getFormaDePago() + " </p>");
		body.append("<p>Informacion Adicional: " + deu.getInformacionAdicional() + " </p>");

		MimeMessage mimeMessage = emailSender.createMimeMessage();
		MimeMessageHelper message = new MimeMessageHelper(mimeMessage, true, "UTF-8");

		message.setFrom("cestrada@finterra.cl");
		message.setTo("cestrada@finterra.cl");
		message.setSubject("Registro Pagadores");
		message.setText(body.toString(), true);

		emailSender.send(mimeMessage);

		System.out.println("enviado");
	}
}
