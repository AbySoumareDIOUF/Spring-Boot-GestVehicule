package com.tp.vehicule;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication//(exclude = {DataSourceAutoConfiguration.class})
public class VehiculeApplication {

	public static void main(String[] args) {
		SpringApplication.run(VehiculeApplication.class, args);
	}

}
