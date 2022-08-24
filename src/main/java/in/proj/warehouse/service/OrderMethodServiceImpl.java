package in.proj.warehouse.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.proj.warehouse.exception.OrderMethodNotFoundException;
import in.proj.warehouse.model.OrderMethod;
import in.proj.warehouse.repo.OrderMethodRepository;

@Service
public class OrderMethodServiceImpl implements IOrderMethodService {

	@Autowired
	private OrderMethodRepository repo;
	
	public Integer saveOrderMethod(OrderMethod om) {
		return repo.save(om).getId();
		
	}

	
	public void updateOrderMethod(OrderMethod om) {
		if(om.getId()==null || !repo.existsById(om.getId()))
			throw new OrderMethodNotFoundException("ORDER METHOD NOT EXIST");
		
		repo.save(om);

	}

	
	public void deleteOrderMethod(Integer id) {
		repo.delete(getOneOrderMethod(id));

	}

	
	public OrderMethod getOneOrderMethod(Integer id) {
		return repo.findById(id).orElseThrow(()->new OrderMethodNotFoundException("ORDER METHOD NOT EXIST"));
		
	}

	
	public List<OrderMethod> getAllOrderMethods() {
		return repo.findAll();
	}

	
	public boolean isOrderMethodCodeExist(String code) {
		return repo.isOrderMethodCodeExist(code)>0;
	}

	
	public boolean isOrderMethodCodeExistForEdit(String code, Integer id) {
		return repo.isOrderMethodCodeExistForEdit(code,id)>0;
	}


	@Override
	public List<Object[]> getOrderMethodModeAndCount() {
		// TODO Auto-generated method stub
		return null;
	}


	//public List<Object[]> getOrderMethodModeAndCount() {
	//	return repo.getOrderMethodModeAndCount();
	//}

}
