package com.ceiba.ceibaparking.service.impl;



import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.ceiba.ceibaparking.entity.FacturaEntity;
import com.ceiba.ceibaparking.entity.VehiculoEntity;
import com.ceiba.ceibaparking.model.Constantes;
import com.ceiba.ceibaparking.model.Vehiculo;
import com.ceiba.ceibaparking.repository.FacturaRepository;
import com.ceiba.ceibaparking.repository.VehiculoRepository;
import com.ceiba.ceibaparking.repository.converter.VehiculoConverter;
import com.ceiba.ceibaparking.service.VigilanteService;
import com.ceiba.ceibaparking.validation.ingreso.ValidarIngresoVehiculo;

@Service("vigilanteServiceImpl")
public class VigilanteServiceImpl implements VigilanteService{

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
	
	public FacturaEntity registrarEgreso(String placa) {
		FacturaEntity factura = facturaRepoitory.findByPlaca(placa);
		Calendar fechaSalida =  Calendar.getInstance();
		factura.setFechaSalida(fechaSalida);
		factura.setTotalHoras(calcularHorasTotales(factura));
		factura.setTotalPagar(calcularTotalApagar(factura));
		return factura;		
	}
		
	private int calcularTotalApagar(FacturaEntity factura) {
		VehiculoEntity vehiculo = vehiculoRepoitory.findById(factura.getPlaca()).get();
		long diasAPagar=(long) Math.floor( factura.getTotalHoras() / Constantes.HORASMINIMASDELDIA);
		long horasApagar=factura.getTotalHoras() % Constantes.HORASMINIMASDELDIA;
		long aumentoCilindraje=0;
		long totalApagar = 0;
		
		if(diasAPagar < 1){ 
			diasAPagar=0;
		} 	
			
		if(vehiculo.getTipoVehiculo() == "Moto"){
			if(vehiculo.getCilindraje()>= Constantes.CILINDRAJEMOTO){ 
				aumentoCilindraje=Constantes.AUMENTOCILINDRAJE;
			}
			totalApagar=aumentoCilindraje+diasAPagar*Constantes.VALORDIAMOTO+horasApagar*Constantes.VALORHORAMOTO;
		}else if (vehiculo.getTipoVehiculo() == "Carro"){
			totalApagar=diasAPagar*Constantes.VALORDIACARRO+horasApagar*Constantes.VALORDIACARRO;
				
		}	
		
		return (int) totalApagar;
	}

	public void agregarFactura(VehiculoEntity vehiculo) {
		Calendar fechaEntrada = Calendar.getInstance();
		FacturaEntity factura = new FacturaEntity(fechaEntrada, fechaEntrada, 0, 0, vehiculo);
		facturaRepoitory.save(factura);
	}
	
	public  int calcularHorasTotales(FacturaEntity factura) {
		Calendar totalTiempo = new GregorianCalendar();
		totalTiempo.setTimeInMillis(factura.getFechaEntrada().getTime().getTime()-factura.getFechaSalida().getTime().getTime());
		return totalTiempo.get(Calendar.HOUR_OF_DAY);

	}

}

