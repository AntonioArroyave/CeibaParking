package com.ceiba.ceibaparking.service.impl;

import java.time.LocalDate;
import java.time.Month;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.ceiba.ceibaparking.controller.Vigilante;
import com.ceiba.ceibaparking.entity.FacturaEntity;
import com.ceiba.ceibaparking.entity.VehiculoEntity;
import com.ceiba.ceibaparking.model.Constantes;
import com.ceiba.ceibaparking.model.Vehiculo;
import com.ceiba.ceibaparking.repository.FacturaRepository;
import com.ceiba.ceibaparking.repository.VehiculoRepository;
import com.ceiba.ceibaparking.repository.converter.VehiculoConverter;
import com.ceiba.ceibaparking.service.VigilanteService;
import com.ceiba.ceibaparking.validation.ingreso.ValidarIngresoVehiculo;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

@Service("vigilanteServiceImpl")
public class VigilanteServiceImpl implements VigilanteService{
	
	private static final Log LOG = LogFactory.getLog(VigilanteServiceImpl.class);

	@Autowired
	VehiculoRepository vehiculoRepoitory;
	
	@Autowired
	FacturaRepository facturaRepoitory;
	
	@Autowired
	@Qualifier("vehiculoConverter")
	private VehiculoConverter vehiculoConverter;
	
	List<ValidarIngresoVehiculo> validacionesIngreso;
	
	@Autowired
	public VigilanteServiceImpl(List<ValidarIngresoVehiculo> validacionesIngreso){
		this.validacionesIngreso = validacionesIngreso;
	}

	
	public void registrarIngreso(Vehiculo vehiculo){
		validacionesIngreso.stream().forEach(validacion -> validacion.validar(vehiculo));
		vehiculoRepoitory.save(vehiculoConverter.model2Entity(vehiculo));
		agregarFactura(vehiculoConverter.model2Entity(vehiculo));
	}
	
	public FacturaEntity registrarEgreso(VehiculoEntity vehiculo) {
		FacturaEntity factura = facturaRepoitory.findFirstByPlacaOrderByFechaEntradaDesc(vehiculo.getPlaca());
		Date fechaSalida =  Constantes.getFechaActual().getTime();
		factura.setFechaSalida(fechaSalida);
		int horasTotales = (int) calcularHorasTotales(factura, fechaSalida);
		factura.setTotalHoras(horasTotales);
		factura.setTotalPagar(calcularTotalApagar(vehiculo, horasTotales));
		facturaRepoitory.save(factura);
		return factura;		
	}
		
	public int calcularTotalApagar(VehiculoEntity vehiculo, int horasTotales) {
		long diasAPagar= horasTotales / Constantes.HORASDELDIA;
		LOG.info("diasApagar: "+ diasAPagar);
		int horasApagar=0;
		int aumentoCilindraje=0;
		long totalApagar = 0;
		if ((horasTotales % Constantes.HORASDELDIA) >= Constantes.HORASMINIMASDELDIA
				&& (horasTotales % Constantes.HORASDELDIA) <= Constantes.HORASDELDIA-1) {
			diasAPagar++; 
		} else {
			horasApagar = horasTotales % Constantes.HORASDELDIA;
		}
		LOG.info("horasApagar: "+ horasApagar);	
		if(vehiculo.getTipoVehiculo().equals("Moto")) {
			if(vehiculo.getCilindraje()>= Constantes.CILINDRAJEMOTO) aumentoCilindraje=Constantes.AUMENTOCILINDRAJE;
			totalApagar=aumentoCilindraje+diasAPagar*Constantes.VALORDIAMOTO+horasApagar*Constantes.VALORHORAMOTO;
		}else if (vehiculo.getTipoVehiculo().equals("Carro")){
			totalApagar=diasAPagar*Constantes.VALORDIACARRO+horasApagar*Constantes.VALORHORACARRO;		
		}
		LOG.info("totalAPagar: "+ totalApagar);	
		return  (int) totalApagar;
	}

	public void agregarFactura(VehiculoEntity vehiculo) {
		Date fechaEntrada = Constantes.getFechaActual().getTime();
		LOG.info("****fechaEntrada: "+ fechaEntrada.toString());	
		FacturaEntity factura = new FacturaEntity(fechaEntrada, fechaEntrada, 0, 0, vehiculo.getPlaca());
		facturaRepoitory.save(factura);
	}
	
	public  long calcularHorasTotales(FacturaEntity factura, Date fechaSalida) {
		long horasTotales = (fechaSalida.getTime() - factura.getFechaEntrada().getTime())
				/ (Constantes.FACTORMILISEG2HORAS);
		if (horasTotales % Constantes.FACTORMILISEG2HORAS != 0){
			horasTotales++;}
		LOG.info("Horas totales calculadas: "+ horasTotales);
		return horasTotales;
	}	

}

