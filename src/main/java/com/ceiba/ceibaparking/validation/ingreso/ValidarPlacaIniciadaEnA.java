package com.ceiba.ceibaparking.validation.ingreso;

import java.util.Calendar;

import com.ceiba.ceibaparking.exception.ParqueaderoExcepcion;
import com.ceiba.ceibaparking.model.Carro;
import com.ceiba.ceibaparking.model.Vehiculo;

public class ValidarPlacaIniciadaEnA implements ValidarIngresoVehiculo {
	
	@Override
	public void validar(Vehiculo vehiculo) {
		if (placaInicidaEnA(vehiculo.getPlaca()) && (vehiculo instanceof Carro)) {
			throw new ParqueaderoExcepcion("Hoy no es un dia habilitado para tu placa");
		}
	}
	
	public boolean placaInicidaEnA(String placa){
	Calendar hoy = Calendar.getInstance();
	return (placa.charAt(0) == 'A' && ((hoy.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY)
								   || (hoy.get(Calendar.DAY_OF_WEEK) != Calendar.MONDAY)));
	}
}
