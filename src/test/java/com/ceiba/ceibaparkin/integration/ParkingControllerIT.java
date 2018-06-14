package com.ceiba.ceibaparkin.integration;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.boot.context.embedded.tomcat.*;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import com.ceiba.ceibaparking.ParkingController;

@RunWith(SpringRunner.class)
public class ParkingControllerIT {

	
	@Test
	public void resgiterMotoTest() throws Exception  {
		
//		mockMvc.perform(MockMvcRequestBuilders.get("/vehiculo")).andExpect(status().isOk())
//		.andExpect((ResultMatcher) jsonPath("placa", "AAA"))
//		.andExpect((ResultMatcher) jsonPath("tipoVehiculo", "MOTO"));
	} 

}
