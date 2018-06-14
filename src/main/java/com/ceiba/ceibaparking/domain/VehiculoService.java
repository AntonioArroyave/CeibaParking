package com.ceiba.ceibaparking.domain;

import java.util.Calendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class VehiculoService {
	private int cupoMotos ;
	private int cupoCarros;
	
	@Autowired
	VehiculoRepository vehiculoRepoitory;
	
	public boolean esPlacaComienzaEnA(String placa){
		Calendar hoy = Calendar.getInstance();
//		Las placas que inician por la letra "A" solo pueden ingresar al parqueadero
//		los días lunes y domingos, de lo contrario debe mostrar un mensaje que no puede ingresar porque no está en un dia hábil
		return (placa.charAt(0) == 'A' && ((hoy.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY)
									   || (hoy.get(Calendar.DAY_OF_WEEK) != Calendar.MONDAY)));
	}
//	public boolean verificarCupo(Vehiculo vehiculo){
////		El parqueadero solo puede tener 20 carros y 10 motos simultáneamente
//		return ((vehiculo.getTipoVehiculo() == TipoVehiculo.CARRO && vehiculoRepoitory.numeroDeCarros() < 20) ||
//		   (vehiculo.getTipoVehiculo() == TipoVehiculo.MOTO && vehiculoRepoitory.numeroDeMotos() < 10));
//	}
	
	public boolean esTipoVehiculoValido(Vehiculo vehiculo){
		//El parqueadero recibe carros y motos
		return (vehiculo.getTipoVehiculo() == TipoVehiculo.MOTO) || (vehiculo.getTipoVehiculo() == TipoVehiculo.CARRO);
	}
	
	public void registrarIngreso(TipoVehiculo tipoVehiculo){
		if(tipoVehiculo == TipoVehiculo.MOTO){
			this.cupoMotos=this.cupoMotos+1;
		} else if (tipoVehiculo == TipoVehiculo.CARRO){
			this.cupoCarros=this.cupoCarros+1;	
		}
	}
	
	public void registrEgreso(TipoVehiculo tipoVehiculo){
		if(tipoVehiculo == TipoVehiculo.MOTO){
			this.cupoMotos=this.cupoMotos-1;
		} else if (tipoVehiculo == TipoVehiculo.CARRO){
			this.cupoCarros=this.cupoCarros-1;	
		}
	}
}

