package com.ceiba.ceibaparking.service.impl;


import java.util.Calendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.ceiba.ceibaparking.entity.VehiculoEntity;
import com.ceiba.ceibaparking.model.Parqueadero;
import com.ceiba.ceibaparking.model.Vehiculo;
import com.ceiba.ceibaparking.repository.VehiculoRepository;
import com.ceiba.ceibaparking.repository.converter.VehiculoConverter;
import com.ceiba.ceibaparking.service.VigilanteService;

@Service("vigilanteServiceImpl")
public class VigilanteServiceImpl implements VigilanteService{

	@Autowired
	VehiculoRepository vehiculoRepoitory;
	
	@Autowired
	@Qualifier("vehiculoConverter")
	private VehiculoConverter vehiculoConverter;
	
	public boolean esPlacaComienzaEnA(String placa){
		Calendar hoy = Calendar.getInstance();
		return (placa.charAt(0) == 'A' && ((hoy.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY)
									   || (hoy.get(Calendar.DAY_OF_WEEK) != Calendar.MONDAY)));
	}

	public boolean esTipoVehiculoValido(Vehiculo vehiculo){
		//El parqueadero recibe carros y motos
		return (vehiculo.getTipoVehiculo() == "Moto") || (vehiculo.getTipoVehiculo() == "Carro");
	}
	
	public void registrarIngreso(Vehiculo vehiculo){
		if()
		vehiculoRepoitory.save(vehiculoConverter.model2Entity(vehiculo));
	}
	
	public boolean verificarCupo(Vehiculo vehiculo) {
		return ((vehiculo.getTipoVehiculo() == "Carro" && vehiculoRepoitory.countByTipoVehiculo("Carro") < Parqueadero.LIMITECARROS) 
				|| (vehiculo.getTipoVehiculo() == "Moto" && vehiculoRepoitory.countByTipoVehiculo("Moto") < Parqueadero.CILINDRAJEMOTO));
	}


	public void registrarEgreso(Vehiculo vehiculo) {
		
	}
}

