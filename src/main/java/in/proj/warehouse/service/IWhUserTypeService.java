package in.proj.warehouse.service;

import java.util.List;

import in.proj.warehouse.model.WhUserType;

public interface IWhUserTypeService {

	Integer saveWhUserType(WhUserType st);
	void updateWhUserType(WhUserType st);
	void deleteWhUserType(Integer id);


	List<WhUserType> getAllWhUserTypes();
	WhUserType getWhUserType(Integer id);

	boolean isWhUserTypeCodeExist(String code);
	boolean isWhUserTypeCodeExistForEdit(String code,Integer id);

	List<Object[]> getWhUserTypeModeAndCount();
}
