package com.ceiba.ceibaparking;

import com.ceiba.ceibaparking.exception.ResourceNotFoundException;
import com.ceiba.ceibaparking.domain.TipoVehiculo;
import com.ceiba.ceibaparking.domain.Vehiculo;
import com.ceiba.ceibaparking.domain.VehiculoRepository;
import com.ceiba.ceibaparking.domain.VehiculoService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
	private VehiculoService vehiculoService;
	
	private static final Logger logger = LoggerFactory.getLogger(ParkingController.class);
	
	@GetMapping("/vehiculo")
	public List<Vehiculo> getAllNotes() {
		logger.debug("-----------------------------logggggggg---------------------------");
	    return vehiculoRepoitory.findAll();
	}

	@PostMapping("/vehiculo")
	public ResponseEntity<Vehiculo> registrarVehiculo(@Valid @RequestBody Vehiculo vehiculo) {
		HttpHeaders headers = new HttpHeaders();
		List<VehiculoRepository> carros = vehiculoRepoitory.findByTipoVehiculo("CARRO");
		List<VehiculoRepository> motos = vehiculoRepoitory.findByTipoVehiculo("MOTO");
		System.out.println("Cupo de carros: "+carros.size()+"Cupo de motos:"+ motos.size());
		if((carros.size()<20 && vehiculo.getTipoVehiculo() == TipoVehiculo.CARRO) || (motos.size()<10 && vehiculo.getTipoVehiculo() == TipoVehiculo.MOTO)){
			
			vehiculoRepoitory.save(vehiculo);
	        headers.add("Registro", "vehiculo registrado");
			return  ResponseEntity.accepted().headers(headers).body(vehiculo);
		}else{
			headers.add("Registro", "no se puedo registrar porque no hay cupos disponibles");
			return ResponseEntity.accepted().headers(headers).body(vehiculo);
		}
	}
	
	@GetMapping("/vehiculo/{placa}") //cambiar el id por la placa
	public Vehiculo getVehiculoById(@PathVariable(value = "placa") Long vehiculoId) {
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

	@DeleteMapping("/vehiculo/{id}")
	public ResponseEntity<Object> deleteVehiculo(@PathVariable(value = "id") Long vehiculoId) {
	    Vehiculo vehiculo = vehiculoRepoitory.findById(vehiculoId)
	            .orElseThrow(() -> new ResourceNotFoundException("Note", "id", vehiculoId));

	    vehiculoRepoitory.delete(vehiculo);
	    
	    return ResponseEntity.ok().build();
	}
}
