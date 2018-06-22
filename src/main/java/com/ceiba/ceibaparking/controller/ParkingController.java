package com.ceiba.ceibaparking.controller;

import com.ceiba.ceibaparking.exception.ResourceNotFoundException;
import com.ceiba.ceibaparking.model.Vehiculo;
import com.ceiba.ceibaparking.repository.FacturaRepository;
import com.ceiba.ceibaparking.repository.VehiculoRepository;
import com.ceiba.ceibaparking.repository.converter.VehiculoConverter;
import com.ceiba.ceibaparking.service.VigilanteService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.ceiba.ceibaparking.entity.FacturaEntity;
import com.ceiba.ceibaparking.entity.VehiculoEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.tomcat.util.http.fileupload.ByteArrayOutputStream;
@RestController
@RequestMapping("/")
public class ParkingController {
	
	@Autowired
	VehiculoRepository vehiculoRepoitory;
	
	@Autowired
	FacturaRepository facturaRepoitory;
	
	@Autowired
	VehiculoConverter vehiculoConverter;
	
	@Autowired
	@Qualifier("vigilanteServiceImpl")
	VigilanteService vigilanteService;
	
	ObjectMapper mapper = new ObjectMapper();
	
	private static final Log LOG = LogFactory.getLog(ParkingController.class);
	
	@GetMapping("/vehiculo") @CrossOrigin(origins = "http://localhost:4200")
	public List<VehiculoEntity> obtenerVehiculos() {
		LOG.info("Vehiculo Repository:   "+ vehiculoRepoitory.findAll().toString());
	    return vehiculoRepoitory.findAll();
	}

	@PostMapping("/vehiculo") @CrossOrigin(origins = "http://localhost:4200")
	public Optional<VehiculoEntity> registrarVehiculo(@RequestBody Vehiculo vehiculo) {
		    LOG.info("*******Post /vehiculo whit data :   "+ vehiculo);
			vigilanteService.registrarIngreso(vehiculo);
			return vehiculoRepoitory.findById(vehiculo.getPlaca());
	}
	
	@GetMapping("/vehiculos") @CrossOrigin(origins = "http://localhost:4200")
	public ResponseEntity<String> getVehiculos() throws IOException {
		List<VehiculoEntity> vehiculosEntity =vehiculoRepoitory.findAll();
		List<String> jsonString = new ArrayList<>();
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		for (VehiculoEntity vehiculoEntity : vehiculosEntity) {
			Vehiculo vehiculo = vehiculoConverter.entity2Model(vehiculoEntity);
			Date fechaIngreso = facturaRepoitory.findTopByPlaca(vehiculoEntity.getPlaca()).getFechaEntrada();
			ObjectWriter writer = mapper.writerFor(Vehiculo.class).withAttribute("fechaIngreso", fechaIngreso.toString());
			jsonString.add(writer.writeValueAsString(vehiculo));
		}
		final ObjectMapper mapperList = new ObjectMapper();
		mapperList.writeValue(out, jsonString);
		return new ResponseEntity<>(jsonString.toString(), HttpStatus.OK);
		
	}
	
	@GetMapping("/vehiculo/{placa}") @CrossOrigin(origins = "http://localhost:4200")
	public ResponseEntity<String> getVehiculoById(@PathVariable(value = "placa") String placa) throws JsonProcessingException {
		VehiculoEntity vehiculoEntity = vehiculoRepoitory.findById(placa).orElseThrow(() -> new ResourceNotFoundException("Vehiculo", "placa", placa));
		Vehiculo vehiculo = vehiculoConverter.entity2Model(vehiculoEntity);
		Date fechaIngreso = facturaRepoitory.findTopByPlaca(placa).getFechaEntrada();
		ObjectWriter writer = mapper.writerFor(Vehiculo.class).withAttribute("fechaIngreso", fechaIngreso.toString());
		String jsonString = writer.writeValueAsString(vehiculo);
		return new ResponseEntity<>(jsonString, HttpStatus.OK);
		
	}

	@PutMapping("/vehiculo/{placa}") @CrossOrigin(origins = "http://localhost:4200")
	public VehiculoEntity updateVehiculo(@PathVariable(value = "placa") String placa,
	                                        @Valid @RequestBody VehiculoEntity vehiculoDetails) {

	    VehiculoEntity vehiculo = vehiculoRepoitory.findById(placa)
	            .orElseThrow(() -> new ResourceNotFoundException("Vehiculo", "placa", vehiculoDetails));

	    vehiculo.setPlaca(vehiculoDetails.getPlaca());
	    vehiculo.setTipoVehiculo(vehiculoDetails.getTipoVehiculo());

	    return vehiculoRepoitory.save(vehiculo);
	}

	@DeleteMapping("/vehiculo/{placa}") @CrossOrigin(origins = "http://localhost:4200")
	public FacturaEntity deleteVehiculo(@PathVariable(value = "placa") String placa) {
	    VehiculoEntity vehiculo = vehiculoRepoitory.findById(placa).orElseThrow(() -> new ResourceNotFoundException("Veichulo", "placa", placa));
	    FacturaEntity facturaFinal = vigilanteService.registrarEgreso(vehiculo);	
	    vehiculoRepoitory.delete(vehiculo);
	    return facturaFinal;
	}
	
	@GetMapping("/facturar/{placa}") @CrossOrigin(origins = "http://localhost:4200")
	public FacturaEntity facturar(@PathVariable(value = "placa") String placa) {
		VehiculoEntity vehiculo = vehiculoRepoitory.findById(placa).orElseThrow(() -> new ResourceNotFoundException("Veichulo", "placa", placa));
		return vigilanteService.registrarEgreso(vehiculo);	
	}
	
}

