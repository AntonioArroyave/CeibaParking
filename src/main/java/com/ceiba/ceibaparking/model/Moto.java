package com.ceiba.ceibaparking.model;

public class Moto extends Vehiculo {
	private static final String TIPOVEHICULO = "Moto";
	private int cilindraje;
	
	public Moto(String placa, int cilindraje) {
		super(placa);
		this.cilindraje = cilindraje;
	}
	public int getCilindraje() {
		return cilindraje;
	}
	
	@Override
	public String getTipoVehiculo() {
		return TIPOVEHICULO;
	}
}
