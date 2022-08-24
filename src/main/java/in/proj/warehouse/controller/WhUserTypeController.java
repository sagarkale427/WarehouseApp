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

import in.proj.warehouse.exception.WhUserTypeNotFoundException;
import in.proj.warehouse.model.WhUserType;
import in.proj.warehouse.service.IWhUserTypeService;
import in.proj.warehouse.util.WhUserTypeUtil;
import in.proj.warehouse.view.WhUserTypeExcelView;




@Controller
@RequestMapping("/wh")
public class WhUserTypeController {
	
	private static final Logger LOG=LoggerFactory.getLogger(WhUserTypeController.class);

	@Autowired
	private IWhUserTypeService service;   //HAS-A
	@Autowired
	private WhUserTypeUtil util;
	@Autowired
	private ServletContext context;

	// 1. show Register Page
	@GetMapping("/register")
	public String showRegister() {
		return "WhUserTypeRegister";
	}

	// 2.on click submit
	@PostMapping("/save")
	public String saveWhUserType(
			// Read Form Data
			@ModelAttribute WhUserType WhUserType, Model model) {

		LOG.info("ENTERED INTO SAVE METHOD");
		try {
		// call service
		Integer id = service.saveWhUserType(WhUserType);
		LOG.debug("RECORD IS CREATED WITH ID {}",id);
		// success msg
		String msg = "Order Method '" + id + "' created";
		// send to UI(key,val)
		model.addAttribute("message", msg);
		
		}catch(WhUserTypeNotFoundException e) {
			LOG.error("UNABLE TO PROCESS REQUEST DUE TO {}",e.getMessage());
			e.printStackTrace();
		} 
		LOG.info("ABOUT TO GO TO UI PAGE");
		// go to UI Page
		return "WhUserTypeRegister";
	}
	
	/** This method is used to fetch data from DB
	 * This logic is required before going to  __DataPage
	 * after delete,if edit not exist and for all request.
	 * Multiple times repeated. So, we created one private method. 
	 */
	private void commonFetchAll(Model model) {
		List<WhUserType> list=service.getAllWhUserTypes();
		model.addAttribute("list",list);
	}

	// 3. fetch Data to UI
	@GetMapping("/all")
	public String fetchWhUserTypes(Model model) {
		
		LOG.info("ENTERED INTO FETCH ALL ROWS");
		try {
			List<WhUserType> list = service.getAllWhUserTypes();
			LOG.debug("ORDER DATA FOUND WITH SIZE {}",list!=null?list.size():"NO DATA");
			// send data to UI
			model.addAttribute("list", list);
			
			
		}catch(WhUserTypeNotFoundException e) {
			LOG.error("UNABLE TO FETCH DATA{}",e.getMessage());
			e.printStackTrace();
		}
		LOG.info("MOVNG TO DATA PAGE TO DISPLAY");
		// go to UI page
		return "WhUserTypeData";
	}

	// 4. delete by id
	@GetMapping("/delete")
	public String deleteWhUserType(@RequestParam Integer id, Model model) {

		LOG.info("ENTERED INTO DELETE METHOD");
		try {
		// call service
		service.deleteWhUserType(id);
		// create message
		String msg = "Order Method '" + id + "' Deleted!";
		LOG.debug(msg);
		model.addAttribute("message", msg);
					
		}catch(WhUserTypeNotFoundException e) {
			LOG.error("UNABLE TO PROCESS DELETE REQUEST{}",e.getMessage());
			e.printStackTrace();
			model.addAttribute("message",e.getMessage()); 	
		}
		
		commonFetchAll(model);
		
		// go to UI Page
		return "WhUserTypeData";
	}

	// 5.show edit page
	@GetMapping("/edit")
	public String showWhUserTypeEdit(@RequestParam Integer id, Model model) {
		
		LOG.info("ENTERED INTO EDIT METHOD");
		String page=null;
          try {
        	// fetch data from DB using service
      		WhUserType om = service.getWhUserType(id);

      		// send object to UI using model memory
      		model.addAttribute("WhUserType", om);
      		
      		//show edit if record exist
      		page="WhUserTypeEdit";
      		
          } catch(WhUserTypeNotFoundException e) {
        	  LOG.error("UNABLE TO PROCESS EDIT REQUEST{}",e.getMessage());
  			  e.printStackTrace();
  			  commonFetchAll(model);
        	  //if row not exist
        	  page="WhUserTypeData";
        	  model.addAttribute("message",e.getMessage());
          }
		

		// Go to UI
          LOG.info("ABOUT TO GO TO PAGE {}",page);
  		  return page;
	}

	// 6.Read form data and submit
	@PostMapping("/update")
	public String updateWhUserType(@ModelAttribute WhUserType WhUserType,Model model) {
 
		LOG.info("ENTERED INTO UPDATE METHOD");
		try {
		// call service for update
		service.updateWhUserType(WhUserType);
		model.addAttribute("message","Order method '"+WhUserType.getId()+"' Updated!");
		}catch(WhUserTypeNotFoundException e) {
			 LOG.error("UNABLE TO PERFORM UPDATE REQUEST{}",e.getMessage());
			e.printStackTrace();
		}
		
		// redirect to all
		LOG.info("REDIRECTING TO FETCH ALL ROWS");
		return "redirect:all";
	}
	
	//7.AJAX Validation
	@GetMapping("/validate")
	@ResponseBody
    public String validateWhUserTypeCode(@RequestParam String code,
    		                               @RequestParam Integer id) {
    	String message="";
    	//for register check
		if(id==0 && service.isWhUserTypeCodeExist(code)) {
			message=code +", already exist";
		} else if(id!=0 && service.isWhUserTypeCodeExistForEdit(code,id)) {
			//for edit check
			message=code +", already exist";
		}
    	return message;
    }	
	
	//8. Export to Excel
	@GetMapping("/excel")
	public ModelAndView exportData() {
		ModelAndView m=new ModelAndView();
		m.setView(new WhUserTypeExcelView());  //view class object
		m.addObject("list", service.getAllWhUserTypes());
		//fetch data from DB
		return m;
	}
	
	//9.Show charts
		@GetMapping("/charts")
		public String generateCharts() {
			
			List<Object[]> list=service.getWhUserTypeModeAndCount();
			String path=context.getRealPath("/");
			util.generatePieChart(path, list);
			util.generateBarChart(path, list);
			return "WhUserTypeCharts";
		}

}
