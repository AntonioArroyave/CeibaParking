package com.ceiba.ceibaparking.model;

import javax.validation.constraints.NotNull;

public abstract class Vehiculo {
	@NotNull
	private String placa;

	public String getPlaca() {
		return placa;
	}

	public Vehiculo(String placa) {
		this.placa = placa;
	}
		
	public abstract String getTipoVehiculo();
}
