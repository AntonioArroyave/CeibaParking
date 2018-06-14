package com.ceiba.ceibaparking;

import com.ceiba.ceibaparking.exception.ResourceNotFoundException;
import com.ceiba.ceibaparking.domain.Vehiculo;
import com.ceiba.ceibaparking.domain.VehiculoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api")
public class ParkingController {
	
	@Autowired
	VehiculoRepository vehiculoRepoitory;
	
	
	@GetMapping("/vehiculo")
	public List<Vehiculo> getAllNotes() {
	    return vehiculoRepoitory.findAll();
	}

	@PostMapping("/vehiculo")
	public Vehiculo crearVehiculo(@Valid @RequestBody Vehiculo vehiculo) {
	    return vehiculoRepoitory.save(vehiculo);
	}
	
	@GetMapping("/vehiculo/{placa}") //cambiar el id por la placa
	public Vehiculo getNoteById(@PathVariable(value = "placa") Long vehiculoId) {
	    return vehiculoRepoitory.findById(vehiculoId).orElseThrow(() -> new ResourceNotFoundException("Vehiculo", "placa", vehiculoId));
	}

	@PutMapping("/vehiculo/{placa}")
	public Vehiculo updateVehiculo(@PathVariable(value = "placa") Long noteId,
	                                        @Valid @RequestBody Vehiculo vehiculoDetails) {

	    Vehiculo vehiculo = vehiculoRepoitory.findById(noteId)
	            .orElseThrow(() -> new ResourceNotFoundException("Vehiculo", "placa", vehiculoDetails));

	    vehiculo.setPlaca(vehiculoDetails.getPlaca());
	    vehiculo.setTipoVehiculo(vehiculoDetails.getTipoVehiculo());

	    return vehiculoRepoitory.save(vehiculo);
	}

	@DeleteMapping("/vehiculo/{placa}")
	public ResponseEntity<Object> deleteVehiculo(@PathVariable(value = "id") Long vehiculoId) {
	    Vehiculo vehiculo = vehiculoRepoitory.findById(vehiculoId)
	            .orElseThrow(() -> new ResourceNotFoundException("Note", "id", vehiculoId));

	    vehiculoRepoitory.delete(vehiculo);
	    
	    return ResponseEntity.ok().build();
	}
}
