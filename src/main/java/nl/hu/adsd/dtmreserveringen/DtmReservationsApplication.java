package nl.hu.adsd.dtmreserveringen;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
public class DtmReservationsApplication {

	public static void main(String[] args) {
		SpringApplication.run(DtmReservationsApplication.class, args);
	}

}
