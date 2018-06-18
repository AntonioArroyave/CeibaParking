package com.ceiba.ceibaparking.model;

import java.sql.Date;

public class Factura {
	
	private String placa;
	private Date fechaEntrada;
	private Date fechaSalida;
	private int totalHoras;
	private int totalPagar;
	
	public Factura(String placa, Date fechaEntrada, Date fechaSalida, int totalHoras, int totalPagar) {
		super();
		this.placa = placa;
		this.fechaEntrada = fechaEntrada;
		this.fechaSalida = fechaSalida;
		this.totalHoras = totalHoras;
		this.totalPagar = totalPagar;
	}
	
	public Factura(){
		super();
	}
	public String getPlaca() {
		return placa;
	}
	public void setPlaca(String placa) {
		this.placa = placa;
	}
	public Date getFechaEntrada() {
		return fechaEntrada;
	}
	public void setFechaEntrada(Date fechaEntrada) {
		this.fechaEntrada = fechaEntrada;
	}
	public Date getFechaSalida() {
		return fechaSalida;
	}
	public void setFechaSalida(Date fechaSalida) {
		this.fechaSalida = fechaSalida;
	}
	public int getTotalHoras() {
		return totalHoras;
	}
	public void setTotalHoras(int totalHoras) {
		this.totalHoras = totalHoras;
	}
	public int getTotalPagar() {
		return totalPagar;
	}
	public void setTotalPagar(int totalPagar) {
		this.totalPagar = totalPagar;
	}
}
