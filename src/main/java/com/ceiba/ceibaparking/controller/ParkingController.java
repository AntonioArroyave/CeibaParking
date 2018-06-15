package com.ceiba.ceibaparking.controller;

import com.ceiba.ceibaparking.exception.ResourceNotFoundException;
import com.ceiba.ceibaparking.model.Carro;
import com.ceiba.ceibaparking.model.Vehiculo;
import com.ceiba.ceibaparking.repository.VehiculoRepository;
import com.ceiba.ceibaparking.service.VigilanteService;
import com.ceiba.ceibaparking.entity.VehiculoEntity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api")
public class ParkingController {
	
	@Autowired
	VehiculoRepository vehiculoRepoitory;
	
	@Autowired
	private VigilanteService vigilanteService;
	
	
	
	@GetMapping("/vehiculo")
	public List<VehiculoEntity> getAllNotes() {
	    return vehiculoRepoitory.findAll();
	}

	@PostMapping("/vehiculo")
	public ResponseEntity<Vehiculo> registrarVehiculo(@RequestBody Vehiculo vehiculo) {
		HttpHeaders headers = new HttpHeaders();
		if(vigilanteService.verificarCupo(vehiculo)){
			vigilanteService.registrarIngreso(vehiculo);
	        headers.add("Registro", "vehiculo registrado");
			return  ResponseEntity.accepted().headers(headers).body(vehiculo);
		}else{
			headers.add("Registro", "no se puedo registrar porque no hay cupos disponibles");
			return ResponseEntity.accepted().headers(headers).body(vehiculo);
		}
	}
	
	@GetMapping("/vehiculo/{placa}") //cambiar el id por la placa
	public VehiculoEntity getVehiculoById(@PathVariable(value = "placa") Long vehiculoId) {
	    return vehiculoRepoitory.findById(vehiculoId).orElseThrow(() -> new ResourceNotFoundException("Vehiculo", "placa", vehiculoId));
	}

	@PutMapping("/vehiculo/{placa}")
	public VehiculoEntity updateVehiculo(@PathVariable(value = "placa") Long noteId,
	                                        @Valid @RequestBody VehiculoEntity vehiculoDetails) {

	    VehiculoEntity vehiculo = vehiculoRepoitory.findById(noteId)
	            .orElseThrow(() -> new ResourceNotFoundException("Vehiculo", "placa", vehiculoDetails));

	    vehiculo.setPlaca(vehiculoDetails.getPlaca());
	    vehiculo.setTipoVehiculo(vehiculoDetails.getTipoVehiculo());

	    return vehiculoRepoitory.save(vehiculo);
	}

	@DeleteMapping("/vehiculo/{id}")
	public ResponseEntity<Object> deleteVehiculo(@PathVariable(value = "id") Long vehiculoId) {
	    VehiculoEntity vehiculo = vehiculoRepoitory.findById(vehiculoId)
	            .orElseThrow(() -> new ResourceNotFoundException("Note", "id", vehiculoId));

	    vehiculoRepoitory.delete(vehiculo);
	    
	    return ResponseEntity.ok().build();
	}
}
