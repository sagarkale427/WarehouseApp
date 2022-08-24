package in.proj.warehouse.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import in.proj.warehouse.model.WhUserType;

public interface WhUserTypeRepository extends JpaRepository<WhUserType,Integer> {

	//Register check
	@Query("SELECT count(userCode) from WhUserType where userCode=:code")
	Integer getWhUserTypeCountCode(String code); 


	//Edit check
	@Query("SELECT count(userCode) from WhUserType where userCode=:code and id!=:id")
	Integer getWhUserTypeCountCodeForEdit(String code,Integer id); 

	//For Charts Data
	@Query("SELECT userType, COUNT(userType) FROM WhUserType GROUP BY userType")
	List<Object[]> getWhUserTypeVendorAndCustomerCount();

}
