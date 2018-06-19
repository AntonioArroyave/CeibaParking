package com.ceiba.ceibaparking.entity;

import java.util.Date;
import javax.persistence.*;

@Entity
@Table(name="facturas")
public class FacturaEntity {

	@Id
	@GeneratedValue
	@Column(name="id_factura")
	private Integer idFactura;
	
	@Column(name="fecha_entrada", columnDefinition="DATETIME")
	@Temporal(TemporalType.TIMESTAMP)
	private Date fechaEntrada;
	
	@Column(name="fecha_salida", columnDefinition="DATETIME")
	@Temporal(TemporalType.TIMESTAMP)
	private Date fechaSalida;
	
	@Column(name="total_horas")
	private int totalHoras;
	
	@Column(name="total_pagar")
	private int totalPagar;
	
	@Column(name="placa")
	private String placa;

	public Date getFechaEntrada() {
		return fechaEntrada;
	}
	
	public Date getFechaSalida() {
		return fechaSalida;
	}

	public int getTotalHoras() {
		return totalHoras;
	}

	public int getTotalPagar() {
		return totalPagar;
	}

	public String getPlaca() {
		return placa;
	}

	public void setFechaSalida(Date fechaSalida) {
		this.fechaSalida = fechaSalida;
	}

	public void setTotalHoras(int totalHoras) {
		this.totalHoras = totalHoras;
	}

	public void setTotalPagar(int totalPagar) {
		this.totalPagar = totalPagar;
	}


	public FacturaEntity(Date fechaEntrada, Date fechaSalida, int totalHoras, int totalPagar, String placa) {
		super();
		this.fechaEntrada = fechaEntrada;
		this.fechaSalida = fechaSalida;
		this.totalHoras = totalHoras;
		this.totalPagar = totalPagar;
		this.placa = placa;
	}
	
	public void setFechaEntrada(Date fechaEntrada) {
		this.fechaEntrada = fechaEntrada;
	}

	public void setPlacaFk(String placa) {
		this.placa = placa;
	}

	public FacturaEntity() {
		super();
	}	
}
	
