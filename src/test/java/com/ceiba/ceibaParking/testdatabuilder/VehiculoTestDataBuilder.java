package com.ceiba.ceibaParking.testdatabuilder;

import com.ceiba.ceibaparking.domain.*;
import com.ceiba.ceibaParking.testdatabuilder.*;;

public class VehiculoTestDataBuilder {

	private long id;
	private String placa;
	private TipoVehiculo tipoVehiculo;
	
	public VehiculoTestDataBuilder() {
		this.placa = "AAA-555";
		this.tipoVehiculo = TipoVehiculo.CARRO;
	}
	
	public VehiculoTestDataBuilder whitPlaca(String placa) {
		this.placa = placa;
		return this;
	}
	
	public VehiculoTestDataBuilder whittipoVehiculo(TipoVehiculo tipoVehiculo) {
		this.tipoVehiculo = tipoVehiculo;
		return this;
	}
	
	public Vehiculo build(){
		return new Vehiculo(this.placa, this.tipoVehiculo);
	}
}
