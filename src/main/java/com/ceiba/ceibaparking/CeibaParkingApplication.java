package com.ceiba.ceibaparking;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication 
public class CeibaParkingApplication {

	public static void main(String[] args) {
		SpringApplication.run(CeibaParkingApplication.class, args);
	}
}
