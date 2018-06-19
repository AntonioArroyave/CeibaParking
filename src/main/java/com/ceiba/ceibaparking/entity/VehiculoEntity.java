package com.ceiba.ceibaparking.entity;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@Entity
@Table(name = "vehiculos")

public class VehiculoEntity {

	@Id 
	@Column(name = "placa")
	private String placa;
	
	@Column(name = "tipo")  
	private String tipoVehiculo;
	
	@Column(name = "cilindraje") @JsonInclude(value=Include.NON_EMPTY, content=Include.NON_NULL)
	private int cilindraje;
	
	public String getPlaca() {
		return placa;
	}

	@JsonIgnore
	public int getCilindraje() {
		return cilindraje;
	}

	public void setCilindraje(int cilindraje) {
		this.cilindraje = cilindraje;
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
	
	@Override
    public String toString() {
        return String.format(
                "Vehiculo[placa=%s, tipoVehiculo='%s']",
                placa, tipoVehiculo);
    }
	public VehiculoEntity() {
		super();
	}
}