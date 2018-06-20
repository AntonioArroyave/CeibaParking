package com.ceiba.ceibaparking.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
@JsonIgnoreProperties({"TIPOVEHICULO"})
public class Carro extends Vehiculo {
	
	private static final String TIPOVEHICULO = "Carro";
	
	@JsonCreator
	public Carro(@JsonProperty("placa") String placa) {
		super(placa);
	}
	
	@Override
	public String getTipoVehiculo() {
		return TIPOVEHICULO;
	}	
}
