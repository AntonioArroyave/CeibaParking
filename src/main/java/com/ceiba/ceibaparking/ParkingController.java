package com.ceiba.ceibaparking;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import com.ceiba.ceibaparking.domain.Vehiculo;

@RestController
public class ParkingController {
	@GetMapping("registar/{placa}")
	private Vehiculo registarVheiculo(@PathVariable String placa){
		return null;
	} 
}
