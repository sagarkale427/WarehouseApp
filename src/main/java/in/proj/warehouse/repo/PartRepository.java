package in.proj.warehouse.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import in.proj.warehouse.model.Part;

public interface PartRepository  extends JpaRepository<Part, Integer>{
	
	

}
