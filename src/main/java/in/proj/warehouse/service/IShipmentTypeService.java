package in.proj.warehouse.service;

import java.util.List;

import in.proj.warehouse.model.ShipmentType;

public interface IShipmentTypeService {
 
	 Integer saveShipmentType(ShipmentType st);
	 void updateShipmentType(ShipmentType st);
	 void deleteShipmentType(Integer id);
	 
	 
	 List<ShipmentType> getAllShipmentTypes();
	 ShipmentType getShipmentType(Integer id);
	 
	 boolean isShipmentTypeCodeExist(String code);
	 boolean isShipmentTypeCodeExistForEdit(String code,Integer id);
	 
	 List<Object[]> getShipmentTypeModeAndCount();
}
