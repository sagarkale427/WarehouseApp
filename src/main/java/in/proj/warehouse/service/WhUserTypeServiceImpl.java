  package in.proj.warehouse.service;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.proj.warehouse.exception.WhUserTypeNotFoundException;
import in.proj.warehouse.model.WhUserType;
import in.proj.warehouse.repo.WhUserTypeRepository;

@Service
public class WhUserTypeServiceImpl implements IWhUserTypeService{

	@Autowired
	private WhUserTypeRepository repo;  //Has-A

	@Override
	public Integer saveWhUserType(WhUserType user) {
		repo.save(user);
		return user.getId(); //return PK(ID)
	}

	@Override
	public List<WhUserType> getAllWhUserTypes() {
		List<WhUserType> list=repo.findAll();
		return list;
	}

	/*@Override
	public void deleteWhUserType(Integer id) {
		 repo.deleteById(id);

	}
	 @Override
	public WhUserType getWhUserType(Integer id) {
		Optional<WhUserType> opt=repo.findById(id);
		if(opt.isPresent()) {
		return	opt.get();
		}else {
			//throw exception WhUserType not found
		}
		return null;
	}*/

	@Override
	public void deleteWhUserType(Integer id) {
		repo.delete(getWhUserType(id));

	}

	@Override
	public WhUserType getWhUserType(Integer id) {
		return repo.findById(id).orElseThrow(
				()->new WhUserTypeNotFoundException(
						" WhUserType '"+id+"' Not Exist"));

	}
	 /* @Override
    public WhUserType getWhUserType(Integer id) {
    	Optional<WhUserType> opt=repo.findById(id);
    	if(opt.isEmpty()) {
    		throw new WhUserTypeNotFoundException("WhUserType '"+id +"' Not Exist");
    	}else {
    	return opt.get();
    	}
  }*/




	@Override
	public void updateWhUserType(WhUserType st) {
		// is given object present 
		//then update
		//else throw exception
		repo.save(st);
	}

	@Override
	public boolean isWhUserTypeCodeExist(String code) {
		/*	Integer count=repo.getWhUserTypeCountCode(code);
		boolean isExist = count > 0 ? true:false;
		return isExist;*/
		//return repo.getWhUserTypeCountCode(code)> 0 ? true:false;
		return repo.getWhUserTypeCountCode(code) > 0 ;
	}
	@Override
	public boolean isWhUserTypeCodeExistForEdit(String code, Integer id) {
		return repo.getWhUserTypeCountCodeForEdit(code, id) > 0;
	}


	public List<Object[]> getWhUserTypeModeAndCount() {
		return repo.getWhUserTypeVendorAndCustomerCount();
	}
}
