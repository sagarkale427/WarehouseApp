package in.proj.warehouse.controller;

import java.util.List;

import javax.servlet.ServletContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import in.proj.warehouse.exception.UomNotFoundException;
import in.proj.warehouse.model.Uom;
import in.proj.warehouse.service.IUomService;
import in.proj.warehouse.util.UomUtil;
import in.proj.warehouse.view.UomExcelView;



@Controller
@RequestMapping("/uom"	)
public class UomController {

	private static final Logger LOG=LoggerFactory.getLogger(UomController.class);
	@Autowired
	private IUomService service;
	@Autowired
	private UomUtil util;
	@Autowired
	private ServletContext context;
	
	
	
	//1.show register page
	@GetMapping("/register")
	 public String showReg() {
		 return "UomRegister";
	 }
	
	//2.on click submit
	@PostMapping("/save")
	public String saveUom(@ModelAttribute Uom uom, Model model) {
		
		LOG.info("ENTERED INTO SAVE METHOD");
		try {
		Integer id=service.saveUom(uom);
		LOG.debug("SAVED WITH ID {}",id);
		model.addAttribute("message","Uom '"+id+"' saved");
		}catch(UomNotFoundException e) {
			LOG.error("UNABLE TO SAVE UOM : {}",e.getMessage());
			e.printStackTrace();
		}
		LOG.info("ABOUT TO LEAVE SAVE METHOD");
		return "UomRegister";
	}
	
	//3.display data
	@GetMapping("/all")
	public String dispalyUoms(Model model) {
		commonFetchAll(model);
		return "UomData";
	}
	
	/** This method is used to fetch data from DB
	 * This logic is required before going to  __DataPage
	 * after delete,if edit not exist and for all request.
	 * Multiple times repeated. So, we created one private method. 
	 */
	private void commonFetchAll(Model model) {
		List<Uom> list=service.getAllUoms();
		model.addAttribute("list",list);
	}
	
  
	
	//4.delete by id
	@GetMapping("/delete")
	public String deleteUom(@RequestParam Integer id, Model model) {
		
		LOG.info("ENTERED INTO DELETE METHOD");
		try {
			service.deleteUom(id);
			model.addAttribute("message","Uom ' "+id+"' deleted");
			LOG.debug("DELETE WITH ID {}",id);
		}catch(UomNotFoundException e) {
			LOG.error("UNABLE TO DELETE UOM : {}",e.getMessage());
			model.addAttribute("message",e.getMessage());
			e.printStackTrace();		
		}	
		//get latest data and send to UI
		commonFetchAll(model);
		LOG.info("ABOUT TO LEAVE DELETE METHOD");
		return "UomData";
	}
	
	//5.show edit
	@GetMapping("/edit")
	public String showEdit(@RequestParam Integer id,Model model) {
		LOG.info("ENTERED INTO EDIT METHOD");
		String page=null;
		try {
		Uom uom=service.getOneUom(id);
		LOG.debug("OBJECT FOUND WITH ID {} FOR EDIT",id);
		model.addAttribute("uom", uom);
		page="UomEdit";
		}catch(UomNotFoundException e) {
			LOG.error("UNABLE TO FETCH UOM : {}",e.getMessage());
			e.printStackTrace();
			commonFetchAll(model);
			model.addAttribute("message", e.getMessage());
			page="UomData";
		}
		LOG.info("ABOUT TO LEAVE EDIT METHOD");
		return page;
	}
	
	//6.update
	@PostMapping("/update")
	public String updateUom(@ModelAttribute Uom uom, Model model) {
		LOG.info("ENTERED INTO UPDATE METHOD");
		try {
		service.updateUom(uom);
		LOG.debug("OBJECT WITH ID {} UPDATED",uom.getId());
		model.addAttribute("message", "Uom ' "+uom.getId()+"' Updated");
		}catch(UomNotFoundException e) {
			LOG.error("UNABLE TO UPDATE UOM : {}",e.getMessage());
			e.printStackTrace();
		}
		//get latest data and send to UI
		commonFetchAll(model);
		LOG.info("ABOUT TO LEAVE UPDATE METHOD");
		return "UomData";
	}
	
	//7.Validate uom model using Ajax
	@GetMapping("/validate")
	@ResponseBody
	public String validateUomModel(@RequestParam Integer id,
			@RequestParam String model) {
		
		String message="";
		if(id==0 && service.isUomModelExist(model)) {
			message=model +", already exist!";
		}else if (id!=0 && service.isUomModelExistForEdit(model,id)) {
			message=model +", already exist!";
		}
		
		return message;
	}
	
	//8.Excel export
	@GetMapping("/excel")
	public ModelAndView showExcel() {
		
		ModelAndView m=new ModelAndView();
		m.setView(new UomExcelView());
		m.addObject("list",service.getAllUoms());
		return m;
	}
	
	//9.Show charts
	@GetMapping("/charts")
	public String generateCharts() {
		
		List<Object[]> list=service.getUomTypeAndCount();
		String path=context.getRealPath("/");
		util.generatePieChart(path, list);
		util.generateBarChart(path, list);
		return "UomCharts";
	}
	
}





