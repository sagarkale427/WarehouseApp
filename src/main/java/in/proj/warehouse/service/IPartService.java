package in.proj.warehouse.service;

import java.util.List;

import in.proj.warehouse.model.Part;

public interface IPartService {

	Integer savePart(Part part);
	void updatePart(Part part);
	void deletePart(Integer id);
	
	Part getOnePart(Integer id);
	List<Part> getAllParts();
	/*
	 * boolean isPartModelExist(String partModel); boolean
	 * isPartModelExistForEdit(String partModel,Integer id); List<Object[]>
	 * getPartTypeAndCount();
	 */
	
}
