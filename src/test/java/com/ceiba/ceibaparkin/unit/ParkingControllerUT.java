package com.ceiba.ceibaparkin.unit;



import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.jdbc.TestDatabaseAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import javax.transaction.Transactional;

import com.ceiba.ceibaparking.CeibaParkingApplication;
import com.ceiba.ceibaparking.controller.ParkingController;
import com.ceiba.ceibaparking.entity.VehiculoEntity;
import com.ceiba.ceibaparking.exception.ParqueaderoExcepcion;
import com.ceiba.ceibaparking.model.Carro;
import com.ceiba.ceibaparking.model.Moto;
import com.ceiba.ceibaparking.model.Constantes;
import com.ceiba.ceibaparking.repository.VehiculoRepository;
import com.ceiba.ceibaparking.service.VigilanteService;
import com.ceiba.ceibaParking.testdatabuilder.*;

//@RunWith(SpringRunner.class)
////@WebMvcTest(ParkingController.class)
//@SpringBootTest(classes = CeibaParkingApplication.class)
@Transactional
@RunWith(SpringRunner.class)
@SpringBootTest(classes=CeibaParkingApplication.class)
public class ParkingControllerUT {
	
	@Autowired
	@Qualifier("vehiculoRepository")
	VehiculoRepository vehiculoRepository; 
	
	@Autowired
	@Qualifier("vigilanteServiceImpl")
	VigilanteService vigilanteService;
	
	Carro carro;
	Moto moto;
	
	@Before
	public void arrange() {
		carro = new Carro("BBC-123");
		moto = new Moto("WSW04D",100);
	}
	
	@After
	public void clean() {
	    vehiculoRepository.deleteAll();
	}
	
	@Test
	public void registarCarroValidoTest() {
		//act
		vigilanteService.registrarIngreso(carro);
		//assert
		assertTrue(vehiculoRepository.existsById(carro.getPlaca()));
	}
	
	@Test
	public void resgitrarMotoTest() throws Exception  {
		//act
		vigilanteService.registrarIngreso(moto);
		//assert
		assertTrue(vehiculoRepository.existsById(moto.getPlaca()));
	}
	
	@Test(expected = ParqueaderoExcepcion.class)
	public void verificarSobrecupoCarrosTest() {
		for (int i=0;i<=Constantes.LIMITECARROS;i++) {
			vigilanteService.registrarIngreso(new Carro("BCD-12"+i));
			vigilanteService.registrarIngreso(new Carro("BCE-12"+i));
		}
	}
	
	@Test(expected = ParqueaderoExcepcion.class)
	public void verificarSobrecupoMotosTest() {
		for (int i=0;i<=Constantes.LIMITEMOTOS;i++) {
			vigilanteService.registrarIngreso(new Moto("BCD-12"+i, 300));
			vigilanteService.registrarIngreso(new Moto("BCE-12"+i, 300));
		}
	}
	
	@Test(expected = ParqueaderoExcepcion.class)
	public void verificarPlacaIniciadaPorA() {
		VehiculoTestDataBuilder vehiculoTestDataBuilder = new VehiculoTestDataBuilder();
		vehiculoTestDataBuilder.conPlaca("AAA-111");
		Carro carroPlacaA =vehiculoTestDataBuilder.buildCarro();
		vigilanteService.registrarIngreso(carroPlacaA);
	}

}
