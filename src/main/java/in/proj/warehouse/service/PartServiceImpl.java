package in.proj.warehouse.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.proj.warehouse.exception.PartNotFoundException;
import in.proj.warehouse.model.Part;
import in.proj.warehouse.repo.PartRepository;

@Service
public class PartServiceImpl implements IPartService {

	@Autowired
	private PartRepository repo;
	
	public Integer savePart(Part part) {
		
		return repo.save(part).getId();
	}

	public void updatePart(Part part) {
         repo.save(part);
	}

	public void deletePart(Integer id) {
           repo.delete(getOnePart(id));
	}

	public Part getOnePart(Integer id) {
		return repo.findById(id).orElseThrow(()->new PartNotFoundException("not exist"));
	}

	public List<Part> getAllParts() {
		return repo.findAll();
	}

}
