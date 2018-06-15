package com.ceiba.ceibaparking.model;

public class Carro extends Vehiculo {
	
	private static final String TIPOVEHICULO = "Carro";
	
	public Carro(String placa) {
		super(placa);
	}
	
	@Override
	public String getTipoVehiculo() {
		return TIPOVEHICULO;
	}	
}
