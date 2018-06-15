package com.ceiba.ceibaparking.entity;

import javax.persistence.*;

@Entity
@Table(name = "vehiculos")
public class VehiculoEntity {

	@Id 
	@Column(name = "placa")
	private String placa;
	
	@Column(name = "tipo")  
	private String tipoVehiculo;
	
	@Column(name = "cilindraje")
	private int cilindraje;
	
	public String getPlaca() {
		return placa;
	}

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
                "Vheiculo[placa=%s, tipoVheiculo='%s']",
                placa, tipoVehiculo);
    }
	public VehiculoEntity() {
		super();
	}
}