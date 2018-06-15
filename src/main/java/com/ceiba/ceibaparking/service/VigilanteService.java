package com.ceiba.ceibaparking.service;

import org.springframework.stereotype.Component;
import com.ceiba.ceibaparking.entity.VehiculoEntity;
import com.ceiba.ceibaparking.model.Vehiculo;


public interface VigilanteService {

	public abstract boolean esPlacaComienzaEnA(String placa);
	
	public abstract boolean verificarCupo(Vehiculo vehiculo);
	
	public abstract boolean esTipoVehiculoValido(Vehiculo vehiculo);
	
	public abstract void registrarIngreso(Vehiculo vehiculo);
	
	public abstract void registrarEgreso(Vehiculo vehiculo);
}

