package com.ceiba.ceibaparking.entity;


import java.util.Calendar;

import javax.persistence.*;

@Entity
@Table(name="facturas")
public class FacturaEntity {

	@Id
	@GeneratedValue
	@Column(name="id_comprobante_pago")
	private int idComprobantePago;
	
	@Column(name="fecha_entrada", columnDefinition="DATETIME")
	@Temporal(TemporalType.TIMESTAMP)
	private Calendar fechaEntrada;
	
	@Column(name="fecha_salida", columnDefinition="DATETIME")
	@Temporal(TemporalType.TIMESTAMP)
	private Calendar fechaSalida;
	
	@Column(name="total_horas")
	private int totalHoras;
	
	@Column(name="total_pagar")
	private int totalPagar;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "placa_fk", referencedColumnName = "placa")
	private VehiculoEntity placa;

	public Calendar getFechaEntrada() {
		return fechaEntrada;
	}
	

	public Calendar getFechaSalida() {
		return fechaSalida;
	}

	public int getTotalHoras() {
		return totalHoras;
	}

	public int getTotalPagar() {
		return totalPagar;
	}

	public String getPlaca() {
		return placa.getPlaca();
	}

	public void setFechaSalida(Calendar fechaSalida) {
		this.fechaSalida = fechaSalida;
	}

	public void setTotalHoras(int totalHoras) {
		this.totalHoras = totalHoras;
	}

	public void setTotalPagar(int totalPagar) {
		this.totalPagar = totalPagar;
	}


	public FacturaEntity(Calendar fechaEntrada, Calendar fechaSalida, int totalHoras, int totalPagar, VehiculoEntity placa) {
		super();
		this.fechaEntrada = fechaEntrada;
		this.fechaSalida = fechaSalida;
		this.totalHoras = totalHoras;
		this.totalPagar = totalPagar;
		this.placa = placa;
	}
	
	public void setFechaEntrada(Calendar fechaEntrada) {
		this.fechaEntrada = fechaEntrada;
	}

	public void setPlacaFk(VehiculoEntity placa) {
		this.placa = placa;
	}

	public FacturaEntity() {
		super();
	}	
}
	
