package com.ceiba.ceibaparkin.unit;



import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import javax.transaction.Transactional;
import com.ceiba.ceibaparkin.testdatabuilder.VehiculoTestDataBuilder;
import com.ceiba.ceibaparking.CeibaParkingApplication;
import com.ceiba.ceibaparking.entity.FacturaEntity;
import com.ceiba.ceibaparking.entity.VehiculoEntity;
import com.ceiba.ceibaparking.exception.ParqueaderoExcepcion;
import com.ceiba.ceibaparking.model.Carro;
import com.ceiba.ceibaparking.model.Moto;
import com.ceiba.ceibaparking.model.Constantes;
import com.ceiba.ceibaparking.repository.FacturaRepository;
import com.ceiba.ceibaparking.repository.VehiculoRepository;
import com.ceiba.ceibaparking.repository.converter.VehiculoConverter;
import com.ceiba.ceibaparking.service.impl.VigilanteServiceImpl;
import com.ceiba.ceibaparking.validation.ingreso.ValidarPlacaIniciadaEnA;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

//@RunWith(SpringRunner.class)
////@WebMvcTest(ParkingController.class)
//@SpringBootTest(classes = CeibaParkingApplication.class)
@Transactional
@RunWith(SpringRunner.class)
@SpringBootTest(classes=CeibaParkingApplication.class)
public class ParkingControllerUT {
	
	private static final Log LOG = LogFactory.getLog(ParkingControllerUT.class);
	
	@Autowired
	@Qualifier("vehiculoRepository")
	VehiculoRepository vehiculoRepository; 
	
	@Autowired
	@Qualifier("facturaRepository")
	FacturaRepository facturaRepository;
	
	@Autowired
	@Qualifier("vigilanteServiceImpl")
	VigilanteServiceImpl vigilanteService;
	
	@Autowired
	@Qualifier("vehiculoConverter")
	VehiculoConverter vehiculoConverter;
	
	Carro carro;
	Moto moto;
	
	@Before
	public void arrange() {
		carro = new Carro("BBC-123");
		moto = new Moto("CCC-456",600);
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
			vigilanteService.registrarIngreso(new Moto("MCD-12"+i, 300));
			vigilanteService.registrarIngreso(new Moto("MCE-12"+i, 300));
		}
	}
	
	@Test(expected = ParqueaderoExcepcion.class)
	public void verificarPlacaIniciadaPorADiaInvalido() {
		VehiculoTestDataBuilder vehiculoTestDataBuilder = new VehiculoTestDataBuilder();
		vehiculoTestDataBuilder.conPlaca("AAA-111");
		Carro carroPlacaA =vehiculoTestDataBuilder.buildCarro();
		Calendar hoy = mock(Calendar.class);
		when(hoy.get(Calendar.DAY_OF_WEEK)).thenReturn(Calendar.FRIDAY);
		vigilanteService.registrarIngreso(carroPlacaA);
	}
	
	@Test
	public void verificarPlacaIniciadaPorADiaValido() {
		VehiculoTestDataBuilder vehiculoTestDataBuilder = new VehiculoTestDataBuilder();
		vehiculoTestDataBuilder.conPlaca("AAA-111");
		Carro carroPlacaA =vehiculoTestDataBuilder.buildCarro();
		ValidarPlacaIniciadaEnA hoy = mock(ValidarPlacaIniciadaEnA.class);
		when(hoy.getDia()).thenReturn(2);
		vigilanteService.registrarIngreso(carroPlacaA);
		assertTrue(vehiculoRepository.existsById(carroPlacaA.getPlaca()));
	}
	
	@Test
	public void facturaCarroValido() {
		vigilanteService.registrarIngreso(carro);
		assertNotNull(facturaRepository.findByPlaca(carro.getPlaca()));
	}
	
	@Test
	public void facturaMotoValido() {
		vigilanteService.registrarIngreso(moto);
		assertNotNull(facturaRepository.findByPlaca(moto.getPlaca()));
	}
	
	@Test
	public void calcularHorasTotalesTest() {
		Date fechaEntrada = new GregorianCalendar(2018, 1, 1, 1, 0,0).getTime();
		Date fechaSalida = new GregorianCalendar(2018, 1, 1, 8, 0,0).getTime();
		FacturaEntity factura = mock(FacturaEntity.class);
		when(factura.getFechaEntrada()).thenReturn(fechaEntrada);
		long horasTotales=vigilanteService.calcularHorasTotales(factura, fechaSalida);
		assertEquals(8, horasTotales);
	}
	
	@Test
	public void calcularHorasTotalesConMinitosTest() {
		Date fechaEntrada = new GregorianCalendar(2018, 1, 1, 1, 0, 0).getTime();
		 Date fechaSalida = new GregorianCalendar(2018, 1, 1, 8, 50, 0).getTime();
		FacturaEntity factura = mock(FacturaEntity.class);
		when(factura.getFechaEntrada()).thenReturn(fechaEntrada);
		long horasTotales=vigilanteService.calcularHorasTotales(factura, fechaSalida);
		assertEquals(8, horasTotales);
	}
	
	@Test
	public void calcularTotalAPagarHorasCarroTest() {
		VehiculoEntity carroEntity = vehiculoConverter.model2Entity(carro);
		int horasApagar=7;
		int totalApagar =vigilanteService.calcularTotalApagar(carroEntity,horasApagar);
		assertEquals(horasApagar*Constantes.VALORHORACARRO, totalApagar);
	}
	
	@Test
	public void calcularTotalAPagarHorasMotoBajoCilindrajeTest() {
		VehiculoTestDataBuilder vehiculoFactory = new VehiculoTestDataBuilder();
		vehiculoFactory.conCilindraje(100);
		Moto motoBajoCilindraje=vehiculoFactory.buildMoto();
		VehiculoEntity motoEntity = vehiculoConverter.model2Entity(motoBajoCilindraje);
		int horasApagar=8;
		int totalApagar =vigilanteService.calcularTotalApagar(motoEntity,horasApagar);
		assertEquals(horasApagar*Constantes.VALORHORAMOTO, totalApagar);
	}
	
	@Test
	public void calcularTotalAPagarHorasMotoAltoCilindrajeTest() {
		VehiculoTestDataBuilder vehiculoFactory = new VehiculoTestDataBuilder();
		vehiculoFactory.conCilindraje(600);
		Moto motoBajoCilindraje=vehiculoFactory.buildMoto();
		VehiculoEntity motoEntity = vehiculoConverter.model2Entity(motoBajoCilindraje);
		int horasApagar=8;
		int totalApagar =vigilanteService.calcularTotalApagar(motoEntity,horasApagar);
		int esperado= horasApagar*Constantes.VALORHORAMOTO+Constantes.AUMENTOCILINDRAJE;
		assertEquals(esperado, totalApagar);
	}
	
	@Test
	public void calcularTotalAPagarDiasMotoTest() {
		VehiculoEntity carroEntity = vehiculoConverter.model2Entity(moto);
		int horasApagar=10;
		int totalApagar =vigilanteService.calcularTotalApagar(carroEntity,horasApagar);
		assertEquals(6000, totalApagar);
	}
	
	@Test
	public void calcularTotalAPagarDiasCarroTest() {
		VehiculoEntity carroEntity = vehiculoConverter.model2Entity(carro);
		int horasApagar=27;
		int totalApagar =vigilanteService.calcularTotalApagar(carroEntity,horasApagar);
		assertEquals(11000, totalApagar);
	}

}
