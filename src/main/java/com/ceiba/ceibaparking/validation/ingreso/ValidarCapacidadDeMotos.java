package com.ceiba.ceibaparking.validation.ingreso;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.ceiba.ceibaparking.exception.ParqueaderoExcepcion;
import com.ceiba.ceibaparking.model.Moto;
import com.ceiba.ceibaparking.model.Constantes;
import com.ceiba.ceibaparking.model.Vehiculo;
import com.ceiba.ceibaparking.repository.VehiculoRepository;


public class ValidarCapacidadDeMotos implements ValidarIngresoVehiculo {
	@Autowired
	@Qualifier("vehiculoRepository")
	VehiculoRepository vehiculoRepository;
	
	private static final Log LOG = LogFactory.getLog(ValidarCapacidadDeCarros.class);
	
	public ValidarCapacidadDeMotos(VehiculoRepository vehiculoRepository) {
		this.vehiculoRepository = vehiculoRepository;
	}

	@Override
	public void validar(Vehiculo vehiculo) {
		if(validarCantidadDeMotos() && (vehiculo instanceof Moto)){
			throw new ParqueaderoExcepcion("Sin parqueaderos disponibles para motos");
		}
	}
	
	public boolean validarCantidadDeMotos(){
		LOG.info("CALL: validarEspacioMotos()");
		LOG.info("Numero de motos en el parqueadero: " + vehiculoRepository.countByTipoVehiculo("Moto"));
		return  vehiculoRepository.countByTipoVehiculo("Moto") > Constantes.LIMITEMOTOS;	
	}

}
