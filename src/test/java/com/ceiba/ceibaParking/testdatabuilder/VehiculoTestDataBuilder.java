package com.ceiba.ceibaParking.testdatabuilder;

import com.ceiba.ceibaparking.model.Carro;
import com.ceiba.ceibaparking.model.Moto;

public class VehiculoTestDataBuilder {

	private String placa = "BCD-123";
	
	private String tipoVehiculo = "Moto";
	
	private Integer cilindraje = 300;
	
	public VehiculoTestDataBuilder(){
		
	}
	
	public Carro buildCarro(){
		return new Carro(this.placa);
	}

	public Moto buildMoto(){
		return new Moto(this.placa, this.cilindraje);
	}
	
	public VehiculoTestDataBuilder conPlaca(String placa){
		this.placa=placa;
		return this;
		
	}
	
	public VehiculoTestDataBuilder conTipo(String tipoVehiculo){
		this.tipoVehiculo=tipoVehiculo;
		return this;
		
	}
	
	public VehiculoTestDataBuilder conCilindraje(Integer cilindraje){
		this.cilindraje=cilindraje;
		return this;
		
	}
	
	public String getPlaca() {
		return placa;
	}

	public void setPlaca(String placa) {
		this.placa = placa;
	}

	public String getTipoVehiculo() {
		return tipoVehiculo;
	}

	public void setTipoVehiculo(String tipoVehiculo) {
		this.tipoVehiculo = tipoVehiculo;
	}

	public Integer getCilindraje() {
		return cilindraje;
	}

	public void setCilindraje(Integer cilindraje) {
		this.cilindraje = cilindraje;
	}
			
}


