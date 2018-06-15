package com.ceiba.ceibaparking.repository.converter;

import org.springframework.stereotype.Component;

import com.ceiba.ceibaparking.entity.VehiculoEntity;
import com.ceiba.ceibaparking.model.Carro;
import com.ceiba.ceibaparking.model.Moto;
import com.ceiba.ceibaparking.model.Vehiculo;

@Component("vehiculoConverter")
public class VehiculoConverter {
	
	public VehiculoEntity model2Entity(Vehiculo vehiculo) {
		if(vehiculo instanceof Carro) {
			return carroModel2entity((Carro)vehiculo);
		}
		return motoModel2entity((Moto)vehiculo);
	}
		
	private VehiculoEntity motoModel2entity(Moto moto) {
		VehiculoEntity vehiculoEntity = new VehiculoEntity();
		vehiculoEntity.setPlaca(moto.getPlaca());
		vehiculoEntity.setTipoVehiculo(moto.getTipoVehiculo());
		vehiculoEntity.setCilindraje(moto.getCilindraje());
		return vehiculoEntity;
	}

	private VehiculoEntity carroModel2entity(Carro carro) {
		VehiculoEntity vehiculoEntity = new VehiculoEntity();
		vehiculoEntity.setPlaca(carro.getPlaca().toUpperCase());
		vehiculoEntity.setTipoVehiculo(carro.getTipoVehiculo());
		return vehiculoEntity;
	}

}
