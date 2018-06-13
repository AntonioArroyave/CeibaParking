package com.ceiba.ceibaparking;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Vehiculo {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	private String placa;
	private String tipoVehiculo;

	public String getFirstName() {
		return placa;
	}

	public void setplaca(String placa) {
		this.placa = placa;
	}

	public String gettipoVehiculo() {
		return tipoVehiculo;
	}

	public void settipoVehiculo(String tipoVehiculo) {
		this.tipoVehiculo = tipoVehiculo;
	}
}