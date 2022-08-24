package in.proj.warehouse.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import in.proj.warehouse.model.Part;
import in.proj.warehouse.service.IPartService;

@Controller
@RequestMapping("/part")
public class PartController {
	
	@Autowired
	private IPartService service;
	
	@GetMapping("/register")
	public String showReg() {
		return "PartRegister";
	}
	
	@PostMapping("/save")
	public String savePart(@ModelAttribute Part part,Model model) {
	
		Integer id=service.savePart(part);
		model.addAttribute("message", "Part '"+id+"' "
				+"Created!");
		return "PartRegister";
	}
	
	
	

}
