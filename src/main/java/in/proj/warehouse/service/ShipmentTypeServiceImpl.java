package in.proj.warehouse.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.proj.warehouse.exception.ShipmentTypeNotFoundException;
import in.proj.warehouse.model.ShipmentType;
import in.proj.warehouse.repo.ShipmentTypeRepository;

@Service
public class ShipmentTypeServiceImpl implements IShipmentTypeService{
 
	@Autowired
	private ShipmentTypeRepository repo;  //Has-A
	
	@Override
	public Integer saveShipmentType(ShipmentType st) {
		repo.save(st);
		return st.getId(); //return PK(ID)
	}

	@Override
	public List<ShipmentType> getAllShipmentTypes() {
		List<ShipmentType> list=repo.findAll();
		return list;
	}
	
	/*@Override
	public void deleteShipmentType(Integer id) {
		 repo.deleteById(id);
		
	}
	 @Override
	public ShipmentType getShipmentType(Integer id) {
		Optional<ShipmentType> opt=repo.findById(id);
		if(opt.isPresent()) {
		return	opt.get();
		}else {
			//throw exception ShipmentType not found
		}
		return null;
	}*/
	
	@Override
	public void deleteShipmentType(Integer id) {
		repo.delete(getShipmentType(id));
		
	}
	
	@Override
	public ShipmentType getShipmentType(Integer id) {
		return repo.findById(id).orElseThrow(
				()->new ShipmentTypeNotFoundException(
				" ShipmentType '"+id+"' Not Exist"));
		
	}
  /*  @Override
    public ShipmentType getShipmentType(Integer id) {
    	Optional<ShipmentType> opt=repo.findById(id);
    	if(opt.isEmpty()) {
    		throw new ShipmentNotFoundException("ShipmentType '"+id +"' Not Exist");
    	}else {
    	return opt.get();
    	}
  }*/
	
	
	
	
	 @Override
	public void updateShipmentType(ShipmentType st) {
		// is given object present 
		 //then update
		 //else throw exception
		repo.save(st);
	}
	 
	@Override
	public boolean isShipmentTypeCodeExist(String code) {
	/*	Integer count=repo.getShipmentTypeCountCode(code);
		boolean isExist = count > 0 ? true:false;
		return isExist;*/
		//return repo.getShipmentTypeCountCode(code)> 0 ? true:false;
		return repo.getShipmentTypeCountCode(code) > 0 ;
	}
	@Override
	public boolean isShipmentTypeCodeExistForEdit(String code, Integer id) {
		return repo.getShipmentTypeCountCodeForEdit(code, id) > 0;
	}

	
	public List<Object[]> getShipmentTypeModeAndCount() {
		return repo.getShipmentTypeModeAndCount();
	}
}
