package com.ceiba.ceibaparking.configuration;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.ceiba.ceibaparking.repository.VehiculoRepository;
import com.ceiba.ceibaparking.validation.ingreso.ValidarCapacidadDeCarros;
import com.ceiba.ceibaparking.validation.ingreso.ValidarCapacidadDeMotos;
import com.ceiba.ceibaparking.validation.ingreso.ValidarIngresoVehiculo;
import com.ceiba.ceibaparking.validation.ingreso.ValidarPlacaIniciadaEnA;

@Configuration
public class Configuracion {
	
	@Bean
	public List<ValidarIngresoVehiculo> configurarValidacionesIngresoVigilante(VehiculoRepository vehiculoRepository){
		List<ValidarIngresoVehiculo> validacionIngresoVehiculos = new ArrayList<>();
		validacionIngresoVehiculos.add(new ValidarCapacidadDeMotos(vehiculoRepository));
		validacionIngresoVehiculos.add(new ValidarCapacidadDeCarros(vehiculoRepository));
		validacionIngresoVehiculos.add(new ValidarPlacaIniciadaEnA());
		return validacionIngresoVehiculos;
	}
}
