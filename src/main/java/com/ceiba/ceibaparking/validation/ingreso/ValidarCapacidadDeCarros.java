package com.ceiba.ceibaparking.validation.ingreso;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.ceiba.ceibaparking.exception.ParqueaderoExcepcion;
import com.ceiba.ceibaparking.model.Carro;
import com.ceiba.ceibaparking.model.Constantes;
import com.ceiba.ceibaparking.model.Vehiculo;
import com.ceiba.ceibaparking.repository.VehiculoRepository;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class ValidarCapacidadDeCarros implements ValidarIngresoVehiculo{

	@Autowired
	@Qualifier("vehiculoRepository")
	VehiculoRepository vehiculoRepository;
	
	private static final Log LOG = LogFactory.getLog(ValidarCapacidadDeCarros.class);
	
	
	public ValidarCapacidadDeCarros(VehiculoRepository vehiculoRepository) {
		this.vehiculoRepository = vehiculoRepository;
	}

	@Override
	public void validar(Vehiculo vehiculo) {
		if(validarCantidadDeCarros() && (vehiculo instanceof Carro)){
			throw new ParqueaderoExcepcion("Sin parqueaderos disponibles para carros");
		}
	}
	
	public boolean validarCantidadDeCarros(){
		LOG.info("CALL: validarEspacioMotos()");
		return  vehiculoRepository.countByTipoVehiculo("Carro") > Constantes.LIMITECARROS;	
	}

}
