package in.proj.warehouse.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import in.proj.warehouse.model.OrderMethod;

public interface OrderMethodRepository extends JpaRepository<OrderMethod, Integer> {

	//for Register check
	@Query("SELECT count(orderCode) FROM OrderMethod WHERE orderCode=:code")
	Integer isOrderMethodCodeExist(String code);

	@Query("SELECT count(orderCode) FROM OrderMethod WHERE orderCode=:code AND id!=:id")
	Integer isOrderMethodCodeExistForEdit(String code,Integer id);

	//For Charts Data
	//	@Query("SELECT orderMode, COUNT(orderMode) FROM ShipmentType GROUP BY orderMode")
	//	List<Object[]> getOrderMethodModeAndCount();
}
