package com.ceiba.ceibaparkin;

import org.assertj.core.api.Assertions;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import com.ceiba.ceibaParking.testdatabuilder.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CeibaParkingApplicationTests {
	@Autowired
	private TestRestTemplate restTemplate;
	
	@Test
	public void contextLoads() {
	}
	
	@Test
	public void registrarVehiculo() {
//		//Arrange
//		//Vehiculo vehiculoTest = new VehiculoTestDataBuilder().build();
//		//Act
//		ResponseEntity<Vehiculo> response = restTemplate.getForEntity("/vehiculo", Vehiculo.class);
//		//Assert
//		Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
//		Assertions.assertThat(response.getBody().getPlaca()).isEqualTo("AAA");
//		Assertions.assertThat(response.getBody().getTipoVehiculo()).isEqualTo("moto");

	}
}
