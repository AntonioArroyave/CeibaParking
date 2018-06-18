package com.ceiba.ceibaparking.repository;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.ceiba.ceibaparking.entity.VehiculoEntity;


@Repository("vehiculoRepository")
public interface VehiculoRepository extends JpaRepository<VehiculoEntity, Serializable> {

	long countByTipoVehiculo(@Param("tipoVehiculo") String tipo);
}