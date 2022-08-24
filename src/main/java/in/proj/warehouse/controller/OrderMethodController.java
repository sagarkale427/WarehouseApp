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

import in.proj.warehouse.exception.OrderMethodNotFoundException;
import in.proj.warehouse.model.OrderMethod;
import in.proj.warehouse.service.IOrderMethodService;
import in.proj.warehouse.util.OrderMethodUtil;
import in.proj.warehouse.view.OrderMethodExcelView;

@Controller
@RequestMapping("/om")
public class OrderMethodController {
	
	private static final Logger LOG=LoggerFactory.getLogger(OrderMethodController.class);

	@Autowired
	private IOrderMethodService service;   //HAS-A
	@Autowired
	private OrderMethodUtil util;
	@Autowired
	private ServletContext context;

	// 1. show Register Page
	@GetMapping("/register")
	public String showRegister() {
		return "OrderMethodRegister";
	}

	// 2.on click submit
	@PostMapping("/save")
	public String saveOrderMethod(
			// Read Form Data
			@ModelAttribute OrderMethod orderMethod, Model model) {

		LOG.info("ENTERED INTO SAVE METHOD");
		try {
		// call service
		Integer id = service.saveOrderMethod(orderMethod);
		LOG.debug("RECORD IS CREATED WITH ID {}",id);
		// success msg
		String msg = "Order Method '" + id + "' created";
		// send to UI(key,val)
		model.addAttribute("message", msg);
		
		}catch(OrderMethodNotFoundException e) {
			LOG.error("UNABLE TO PROCESS REQUEST DUE TO {}",e.getMessage());
			e.printStackTrace();
		} 
		LOG.info("ABOUT TO GO TO UI PAGE");
		// go to UI Page
		return "OrderMethodRegister";
	}
	
	/** This method is used to fetch data from DB
	 * This logic is required before going to  __DataPage
	 * after delete,if edit not exist and for all request.
	 * Multiple times repeated. So, we created one private method. 
	 */
	private void commonFetchAll(Model model) {
		List<OrderMethod> list=service.getAllOrderMethods();
		model.addAttribute("list",list);
	}

	// 3. fetch Data to UI
	@GetMapping("/all")
	public String fetchOrderMethods(Model model) {
		
		LOG.info("ENTERED INTO FETCH ALL ROWS");
		try {
			List<OrderMethod> list = service.getAllOrderMethods();
			LOG.debug("ORDER DATA FOUND WITH SIZE {}",list!=null?list.size():"NO DATA");
			// send data to UI
			model.addAttribute("list", list);
			
			
		}catch(OrderMethodNotFoundException e) {
			LOG.error("UNABLE TO FETCH DATA{}",e.getMessage());
			e.printStackTrace();
		}
		LOG.info("MOVNG TO DATA PAGE TO DISPLAY");
		// go to UI page
		return "OrderMethodData";
	}

	// 4. delete by id
	@GetMapping("/delete")
	public String deleteOrderMethod(@RequestParam Integer id, Model model) {

		LOG.info("ENTERED INTO DELETE METHOD");
		try {
		// call service
		service.deleteOrderMethod(id);
		// create message
		String msg = "Order Method '" + id + "' Deleted!";
		LOG.debug(msg);
		model.addAttribute("message", msg);
					
		}catch(OrderMethodNotFoundException e) {
			LOG.error("UNABLE TO PROCESS DELETE REQUEST{}",e.getMessage());
			e.printStackTrace();
			model.addAttribute("message",e.getMessage()); 	
		}
		
		commonFetchAll(model);
		
		// go to UI Page
		return "OrderMethodData";
	}

	// 5.show edit page
	@GetMapping("/edit")
	public String showOrderMethodEdit(@RequestParam Integer id, Model model) {
		
		LOG.info("ENTERED INTO EDIT METHOD");
		String page=null;
          try {
        	// fetch data from DB using service
      		OrderMethod om = service.getOneOrderMethod(id);

      		// send object to UI using model memory
      		model.addAttribute("OrderMethod", om);
      		
      		//show edit if record exist
      		page="OrderMethodEdit";
      		
          } catch(OrderMethodNotFoundException e) {
        	  LOG.error("UNABLE TO PROCESS EDIT REQUEST{}",e.getMessage());
  			  e.printStackTrace();
  			  commonFetchAll(model);
        	  //if row not exist
        	  page="OrderMethodData";
        	  model.addAttribute("message",e.getMessage());
          }
		

		// Go to UI
          LOG.info("ABOUT TO GO TO PAGE {}",page);
  		  return page;
	}

	// 6.Read form data and submit
	@PostMapping("/update")
	public String updateOrderMethod(@ModelAttribute OrderMethod orderMethod,Model model) {
 
		LOG.info("ENTERED INTO UPDATE METHOD");
		try {
		// call service for update
		service.updateOrderMethod(orderMethod);
		model.addAttribute("message","Order method '"+orderMethod.getId()+"' Updated!");
		}catch(OrderMethodNotFoundException e) {
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
    public String validateOrderMethodCode(@RequestParam String code,
    		                               @RequestParam Integer id) {
    	String message="";
    	//for register check
		if(id==0 && service.isOrderMethodCodeExist(code)) {
			message=code +", already exist";
		} else if(id!=0 && service.isOrderMethodCodeExistForEdit(code,id)) {
			//for edit check
			message=code +", already exist";
		}
    	return message;
    }	
	
	//8. Export to Excel
	@GetMapping("/excel")
	public ModelAndView exportData() {
		ModelAndView m=new ModelAndView();
		m.setView(new OrderMethodExcelView());  //view class object
		m.addObject("list", service.getAllOrderMethods());
		//fetch data from DB
		return m;
	}
	
	//9.Show charts
		@GetMapping("/charts")
		public String generateCharts() {
			
			List<Object[]> list=service.getOrderMethodModeAndCount();
			String path=context.getRealPath("/");
			util.generatePieChart(path, list);
			util.generateBarChart(path, list);
			return "OrderMethodCharts";
		}

}
