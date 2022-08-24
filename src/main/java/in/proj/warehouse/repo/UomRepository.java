package in.proj.warehouse.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import in.proj.warehouse.model.Uom;

public interface UomRepository extends JpaRepository<Uom, Integer>{


	//for register check
	@Query("SELECT count(uomModel) FROM Uom WHERE uomModel=:uomModel")
	Integer getUomModelCount(String uomModel);

	//for register check
	@Query("SELECT count(uomModel) FROM Uom WHERE uomModel=:uomModel and id!=:id")
	Integer getUomModelCountForEdit(String uomModel,Integer id);

	//For Charts Data
	@Query("SELECT uomType, COUNT(uomType) FROM Uom GROUP BY uomType")
	List<Object[]> getUomTypeAndCount();

}
