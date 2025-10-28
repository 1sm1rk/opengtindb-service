package de.homelabs.opengtin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class OpengtinServiceApplication {

	public static void main(String[] args) {
		Test test = new Test();
		test.convert();
		SpringApplication.run(OpengtinServiceApplication.class, args);
	}

}
