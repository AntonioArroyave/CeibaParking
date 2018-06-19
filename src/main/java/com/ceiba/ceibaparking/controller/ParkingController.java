package com.ceiba.ceibaparking.controller;

import com.ceiba.ceibaparking.exception.ResourceNotFoundException;
import com.ceiba.ceibaparking.model.Vehiculo;
import com.ceiba.ceibaparking.repository.VehiculoRepository;
import com.ceiba.ceibaparking.service.VigilanteService;
import com.ceiba.ceibaparking.validation.ingreso.ValidarCapacidadDeCarros;
import com.ceiba.ceibaparking.entity.VehiculoEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
@RestController
@RequestMapping("/")
public class ParkingController {
	
	@Autowired
	VehiculoRepository vehiculoRepoitory;
	
	@Autowired
	@Qualifier("vigilanteServiceImpl")
	VigilanteService vigilanteService;
	
	private static final Log LOG = LogFactory.getLog(ParkingController.class);
	
	@GetMapping("/vehiculo")
	public List<VehiculoEntity> obtenerVehiculos() {
		LOG.info("Vehiculo Repository:   "+ vehiculoRepoitory.findAll().toString());
	    return vehiculoRepoitory.findAll();
	}

	@PostMapping("/vehiculo")
	public Optional<VehiculoEntity> registrarVehiculo(@RequestBody Vehiculo vehiculo) {
			vigilanteService.registrarIngreso(vehiculo);
			return vehiculoRepoitory.findById(vehiculo.getPlaca());
	}
	
	@GetMapping("/vehiculo/{placa}") //cambiar el id por la placa
	public VehiculoEntity getVehiculoById(@PathVariable(value = "placa") String placa) {
	    return vehiculoRepoitory.findById(placa).orElseThrow(() -> new ResourceNotFoundException("Vehiculo", "placa", placa));
	}

	@PutMapping("/vehiculo/{placa}")
	public VehiculoEntity updateVehiculo(@PathVariable(value = "placa") String placa,
	                                        @Valid @RequestBody VehiculoEntity vehiculoDetails) {

	    VehiculoEntity vehiculo = vehiculoRepoitory.findById(placa)
	            .orElseThrow(() -> new ResourceNotFoundException("Vehiculo", "placa", vehiculoDetails));

	    vehiculo.setPlaca(vehiculoDetails.getPlaca());
	    vehiculo.setTipoVehiculo(vehiculoDetails.getTipoVehiculo());

	    return vehiculoRepoitory.save(vehiculo);
	}

	@DeleteMapping("/vehiculo/{placa}")
	public ResponseEntity<Object> deleteVehiculo(@PathVariable(value = "placa") String placa) {
	    VehiculoEntity vehiculo = vehiculoRepoitory.findById(placa).orElseThrow(() -> new ResourceNotFoundException("Veichulo", "placa", placa));

	    vehiculoRepoitory.delete(vehiculo);
	    
	    return ResponseEntity.ok().build();
	}
	
	@GetMapping("/facturar/{placa}")
	public Optional<VehiculoEntity> facturar(@PathVariable(value = "placa") String placa) {
		VehiculoEntity vehiculo = vehiculoRepoitory.findById(placa).orElseThrow(() -> new ResourceNotFoundException("Veichulo", "placa", placa));
		vigilanteService.registrarEgreso(vehiculo);
		return null;
	}
	
}

