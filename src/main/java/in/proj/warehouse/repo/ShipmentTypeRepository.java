package in.proj.warehouse.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import in.proj.warehouse.model.ShipmentType;

public interface ShipmentTypeRepository extends JpaRepository<ShipmentType,Integer> {

	//Register check
	@Query("SELECT count(shipCode) from ShipmentType where shipCode=:code")
	Integer getShipmentTypeCountCode(String code); 
	

	//Edit check
	@Query("SELECT count(shipCode) from ShipmentType where shipCode=:code and id!=:id")
	Integer getShipmentTypeCountCodeForEdit(String code,Integer id); 
	
	//For Charts Data
	@Query("SELECT shipMode, COUNT(shipMode) FROM ShipmentType GROUP BY shipMode")
	List<Object[]> getShipmentTypeModeAndCount();
	
}
 