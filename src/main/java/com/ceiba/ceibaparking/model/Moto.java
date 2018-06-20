package com.ceiba.ceibaparking.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
@JsonIgnoreProperties({"TIPOVEHICULO"})
public class Moto extends Vehiculo {
	private static final String TIPOVEHICULO = "Moto";
	private int cilindraje;
	
	@JsonCreator
	public Moto(@JsonProperty("placa") String placa, @JsonProperty("cilindraje") int cilindraje) {
		super(placa);
		this.cilindraje = cilindraje;
	}
	public int getCilindraje() {
		return this.cilindraje;
	}
	
	@Override
	public String getTipoVehiculo() {
		return TIPOVEHICULO;
	}
}
