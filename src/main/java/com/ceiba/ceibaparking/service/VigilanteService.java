package com.ceiba.ceibaparking.service;

import com.ceiba.ceibaparking.entity.FacturaEntity;
import com.ceiba.ceibaparking.entity.VehiculoEntity;
import com.ceiba.ceibaparking.model.Vehiculo;

public interface VigilanteService {
	
	public abstract void registrarIngreso(Vehiculo vehiculo);
	
	public abstract FacturaEntity registrarEgreso(VehiculoEntity vehiculo);
}

