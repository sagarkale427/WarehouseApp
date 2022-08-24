package in.proj.warehouse.service;

import java.util.List;

import in.proj.warehouse.model.Uom;

public interface IUomService {

	Integer saveUom(Uom uom);
	void updateUom(Uom uom);
	void deleteUom(Integer id);
	
	Uom getOneUom(Integer id);
	List<Uom> getAllUoms();
	boolean isUomModelExist(String uomModel);
	boolean isUomModelExistForEdit(String uomModel,Integer id);
	List<Object[]> getUomTypeAndCount();
	
	
}
