package com.ceiba.ceibaparking;

import java.util.List;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "vehiculo", path = "vehiculo")
public interface VehiculoRepository extends PagingAndSortingRepository<Vehiculo, Long> {

	List<VehiculoRepository> findByplaca(@Param("placa") String placa);

}