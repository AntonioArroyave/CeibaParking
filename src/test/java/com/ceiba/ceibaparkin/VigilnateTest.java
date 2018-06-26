package com.ceiba.ceibaparkin;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.*;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.nio.charset.Charset;
import java.util.Arrays;
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
import com.ceiba.ceibaparking.model.Vehiculo;
import com.ceiba.ceibaparking.model.Constantes;
import com.ceiba.ceibaparking.repository.FacturaRepository;
import com.ceiba.ceibaparking.repository.VehiculoRepository;
import com.ceiba.ceibaparking.repository.converter.VehiculoConverter;
import com.ceiba.ceibaparking.service.impl.VigilanteServiceImpl;
import com.ceiba.ceibaparking.validation.ingreso.ValidarPlacaIniciadaEnA;

import co.com.sc.nexura.superfinanciera.action.generic.services.trm.action.*;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

@Transactional
@RunWith(SpringRunner.class)
@SpringBootTest(classes=CeibaParkingApplication.class)
@WebAppConfiguration
public class VigilnateTest {
	
	private static final Log LOG = LogFactory.getLog(VigilnateTest.class);
	
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
	
	//desde aqui
	
    private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(),
            Charset.forName("utf8"));

    private MockMvc mockMvc;
    private HttpMessageConverter mappingJackson2HttpMessageConverter;
    
    @Autowired
    private WebApplicationContext webApplicationContext;
    
    @Autowired
    void setConverters(HttpMessageConverter<?>[] converters) {

        this.mappingJackson2HttpMessageConverter = Arrays.asList(converters).stream()
            .filter(hmc -> hmc instanceof MappingJackson2HttpMessageConverter)
            .findAny()
            .orElse(null);

        assertNotNull("the JSON message converter must not be null",
                this.mappingJackson2HttpMessageConverter);
    }
    
   
	@Before
	public void arrange() {
		carro = new Carro("BBC-123");
		moto = new Moto("CCC-456",600);
		VehiculoEntity carroEntity = vehiculoConverter.model2Entity(carro);
		VehiculoEntity motoEntity = vehiculoConverter.model2Entity(moto);
		this.mockMvc = webAppContextSetup(webApplicationContext).build(); 
		facturaRepository.deleteAllInBatch();
		vehiculoRepository.deleteAllInBatch();
		vehiculoRepository.save(carroEntity);
		vehiculoRepository.save(motoEntity);
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
	
//No funciona el mock, creo que es un problema de contextos y el configure de la aplicacion.	
//	@Test
//	public void verificarPlacaIniciadaPorADiaValido() {
//		VehiculoTestDataBuilder vehiculoTestDataBuilder = new VehiculoTestDataBuilder();
//		vehiculoTestDataBuilder.conPlaca("AAA-111");
//		Carro carroPlacaA =vehiculoTestDataBuilder.buildCarro();
//		ValidarPlacaIniciadaEnA hoy = mock(ValidarPlacaIniciadaEnA.class);
//		when(hoy.getDia()).thenReturn(2);
//		vigilanteService.registrarIngreso(carroPlacaA);
//		assertTrue(vehiculoRepository.existsById(carroPlacaA.getPlaca()));
//	}
	
	@Test
	public void facturaCarroValido() {
		vigilanteService.registrarIngreso(carro);
		assertNotNull(facturaRepository.findFirstByPlacaOrderByFechaEntradaDesc(carro.getPlaca()));
	}
	
	@Test
	public void facturaMotoValido() {
		vigilanteService.registrarIngreso(moto);
		assertNotNull(facturaRepository.findFirstByPlacaOrderByFechaEntradaDesc(moto.getPlaca()));
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
	
	  @Test
	  public void webServiceTRMTest() {
		TrmClient trmClient = new TrmClient(Constantes.ENDPOINT);
		Float trm=trmClient.getTrm();
	    assertNotNull(trm);
	  }
	  
	  @Test
	  public void obtenerVehiculosTest() throws Exception {
		  mockMvc.perform(get("/vehiculo").content("")
	                .contentType(contentType))
	                .andExpect(status().isOk());
	  }
	  
	  @Test
	  public void getVehiculoByIdTest() throws Exception {
			vigilanteService.registrarIngreso(carro);
			mockMvc.perform(get("/vehiculo/"+carro.getPlaca()).content("")
	                .contentType(contentType))
	                .andExpect(status().isOk());
	  }
	  
	  @Test
	  public void getVehiculoByIdVehiculoNotFoundTest() throws Exception {
		  mockMvc.perform(get("/vehiculo/***").content("")
	                .contentType(contentType))
	                .andExpect(status().isNotFound());
	  }
}
