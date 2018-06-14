package com.ceiba.ceibaParking.testdatabuilder;

import com.ceiba.ceibaparking.domain.*;
import com.ceiba.ceibaParking.testdatabuilder.*;;

public class VehiculoTestDataBuilder {

	private Long id;
	private String placa;
	private String tipoVehiculo;
	
	public VehiculoTestDataBuilder() {
		this.id=(long) 1;
		this.placa = "AAA";
		this.tipoVehiculo = "CARRO";
	}
	
	public VehiculoTestDataBuilder whitPlaca(String placa) {
		this.placa = placa;
		return this;
	}
	
	public VehiculoTestDataBuilder whittipoVehiculo(String tipoVehiculo) {
		this.tipoVehiculo = tipoVehiculo;
		return this;
	}
	
	public Vehiculo build(){ // setiar esto para corregir las preubas
		return new Vehiculo();
	}
}
