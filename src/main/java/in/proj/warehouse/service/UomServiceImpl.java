package in.proj.warehouse.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.proj.warehouse.exception.UomNotFoundException;
import in.proj.warehouse.model.Uom;
import in.proj.warehouse.repo.UomRepository;

@Service
public class UomServiceImpl implements IUomService{

	@Autowired
	private UomRepository repo;    //Has-A
	

	public Integer saveUom(Uom uom) {
		repo.save(uom);
		return uom.getId();
	}

	
	public void updateUom(Uom uom) {
		repo.save(uom);
		
	}

	
	public void deleteUom(Integer id) {
		repo.delete(getOneUom(id));
		
	}

	
	public Uom getOneUom(Integer id) {
		return repo.findById(id).orElseThrow(()->new UomNotFoundException("Uom '"+id +"' Not exist"));
	}

	
	public List<Uom> getAllUoms() {
		return repo.findAll();
		 
	}


	
	public boolean isUomModelExist(String uomModel) {
		return repo.getUomModelCount(uomModel)>0;
		
	}


	
	public boolean isUomModelExistForEdit(String uomModel, Integer id) {
		
		return repo.getUomModelCountForEdit(uomModel, id)>0;
	}


	
	public List<Object[]> getUomTypeAndCount() {
		
		return repo.getUomTypeAndCount();
	}

	
}
