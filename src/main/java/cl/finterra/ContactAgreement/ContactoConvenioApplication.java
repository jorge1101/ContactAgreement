package cl.finterra.ContactAgreement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;



@SpringBootApplication //(exclude = { DataSourceAutoConfiguration.class, DataSourceTransactionManagerAutoConfiguration.class, HibernateJpaAutoConfiguration.class })
public class ContactoConvenioApplication {

	public static void main(String[] args) {
		SpringApplication.run(ContactoConvenioApplication.class, args);
	}

}
