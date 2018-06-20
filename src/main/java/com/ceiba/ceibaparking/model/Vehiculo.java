package com.ceiba.ceibaparking.model;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonSubTypes.Type;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;
import com.fasterxml.jackson.databind.annotation.JsonAppend;

@JsonTypeInfo(use = Id.NAME,
include = JsonTypeInfo.As.PROPERTY,
property = "TipoVehiculo")
@JsonSubTypes({
    @Type(value=Moto.class),
    @Type(value=Carro.class)
})
@JsonAppend(attrs = { 
		  @JsonAppend.Attr(value = "fechaIngreso") 
		})
public abstract class Vehiculo {
	@NotNull
	private String placa;

	public String getPlaca() {
		return placa;
	}

	public Vehiculo(String placa) {
		this.placa = placa;
	}
	
	public Vehiculo() {
	}
	public abstract String getTipoVehiculo();
}
