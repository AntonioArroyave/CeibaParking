package com.ceiba.ceibaparkin;

import static org.springframework.test.web.client.match.MockRestRequestMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.ceiba.ceibaparking.ParkingController;

@RunWith(SpringRunner.class)
@WebMvcTest(ParkingController.class)
public class ParkingControllerTest {
	@Autowired
	private MockMvc mockMvc;
	
	@Test
	public void resgiterMotoTest() throws Exception  {
		
//		mockMvc.perform(MockMvcRequestBuilders.get("/vehiculo")).andExpect(status().isOk())
//		.andExpect((ResultMatcher) jsonPath("placa", "AAA"))
//		.andExpect((ResultMatcher) jsonPath("tipoVehiculo", "MOTO"));
	} 

}
