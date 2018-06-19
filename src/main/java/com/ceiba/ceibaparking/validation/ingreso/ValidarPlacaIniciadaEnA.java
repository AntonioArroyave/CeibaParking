package com.ceiba.ceibaparking.validation.ingreso;

import java.util.Calendar;

import com.ceiba.ceibaparking.exception.ParqueaderoExcepcion;
import com.ceiba.ceibaparking.model.Carro;
import com.ceiba.ceibaparking.model.Vehiculo;
import com.ceiba.ceibaparking.service.impl.VigilanteServiceImpl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public class ValidarPlacaIniciadaEnA implements ValidarIngresoVehiculo {
	
	private static final Log LOG = LogFactory.getLog(ValidarPlacaIniciadaEnA.class);

	@Override
	public void validar(Vehiculo vehiculo) {
		if (placaInicidaEnA(vehiculo.getPlaca()) && (vehiculo instanceof Carro)
				&& diaInvalido()) {
			throw new ParqueaderoExcepcion("Hoy no es un dia habilitado para tu placa");
		}
	}
	
	public boolean placaInicidaEnA(String placa){
	return (placa.charAt(0) == 'A');
	}
	
	public boolean diaInvalido(){
	
	int diaSemana = getDia();
	LOG.info("*************Dia de la semana : "+ diaSemana);
	return ((diaSemana == Calendar.SATURDAY)
			|| diaSemana == Calendar.THURSDAY
			|| diaSemana == Calendar.TUESDAY
			|| diaSemana == Calendar.WEDNESDAY
			|| diaSemana == Calendar.FRIDAY);
	}
	
	public int getDia(){
		return Calendar.getInstance().get(Calendar.DAY_OF_WEEK);
	}
}
