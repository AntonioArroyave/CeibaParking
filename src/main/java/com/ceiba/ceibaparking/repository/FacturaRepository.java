package com.ceiba.ceibaparking.repository;

import java.io.Serializable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.ceiba.ceibaparking.entity.FacturaEntity;



@Repository("facturaRepository")
public interface FacturaRepository extends JpaRepository<FacturaEntity, Serializable> {

	FacturaEntity findByPlaca(@Param("placa_fk") String vehiculo);
	FacturaEntity findFirstByPlacaOrderByFechaEntradaDesc(@Param("placa_fk") String vehiculo);
}

