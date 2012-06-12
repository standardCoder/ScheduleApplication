package spring3.controller;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

 
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import spring3.form.Employee;
import spring3.form.FormShift;
import spring3.form.FormSkill;
import spring3.form.ScheduleHandler;

@Controller
@SessionAttributes
public class HomeController {

//	private ScheduleHandler scheduleHandler = new ScheduleHandler();
	
    @RequestMapping(value = "/employees.html", method = RequestMethod.POST)
    public void addContact(@ModelAttribute("employee")
                            Employee employee, BindingResult result, HttpSession session) {
    	if(session.getAttribute("scheduleHandler") == null){
    		setupScheduleHandler(session);
    	}
    	ScheduleHandler scheduleHandler = (ScheduleHandler) session.getAttribute("scheduleHandler");
    	scheduleHandler.addEmployee(employee);
    	
    }
 
    @RequestMapping(value = "/addSkill", method = RequestMethod.POST)
    public void addSkill(@ModelAttribute("skill")
                            FormSkill formSkill, BindingResult result, HttpSession session) {
    	if(session.getAttribute("scheduleHandler") == null){
    		setupScheduleHandler(session);
    	}
    	ScheduleHandler scheduleHandler = (ScheduleHandler) session.getAttribute("scheduleHandler");
    	scheduleHandler.addSkill(formSkill);
    }

    @RequestMapping(value = "/addShift", method = RequestMethod.POST)
    public void addShift(@ModelAttribute("shift")
    						FormShift formShift, BindingResult result, HttpSession session) {
    	if(session.getAttribute("scheduleHandler") == null){
    		setupScheduleHandler(session);
    	}
    	ScheduleHandler scheduleHandler = (ScheduleHandler) session.getAttribute("scheduleHandler");
    	scheduleHandler.addShift(formShift);
    }
    
    @RequestMapping(value = "/solve")
    public void computeSchedule(HttpSession session){
    	 
    	if(session.getAttribute("scheduleHandler") == null){
    		setupScheduleHandler(session);
    	}
    	ScheduleHandler scheduleHandler = (ScheduleHandler) session.getAttribute("scheduleHandler");
    	scheduleHandler.createSchedule();
    	
    }
    
    
	@ModelAttribute("skillsList")
	public List<String> populateSkillsList(HttpSession session) {
    	if(session.getAttribute("scheduleHandler") == null){
    		setupScheduleHandler(session);
    	}
    	ScheduleHandler scheduleHandler = (ScheduleHandler) session.getAttribute("scheduleHandler");
		//Data referencing for web framework checkboxes
		List<String> skillsList = new ArrayList<String>();
		LinkedList<FormSkill> formSkills = scheduleHandler.getSkills();
			
		for(FormSkill formSkill:formSkills){
			skillsList.add(formSkill.getName());
		}
		
		return skillsList;
	}
    
	@ModelAttribute("dayList")
	public Map<String,String> populateDayList() {
		
		Map<String,String> day = new LinkedHashMap<String,String>();
		day.put("1", "Monday");
		day.put("2", "Tuesday");
		day.put("3", "Wednesday");
		day.put("4", "Thursday");
		day.put("5", "Friday");
		day.put("6", "Saturday");
		day.put("7", "Sunday");
		
		return day;
	}
	
	@ModelAttribute("hoursOfDayList")
	public Map<String,String> populateHourList(){
		
		Map<String,String> hours = new LinkedHashMap<String,String>();
		hours.put("00", "0");
		hours.put("01", "1");
		hours.put("02", "2");
		hours.put("03", "3");
		hours.put("04", "4");
		hours.put("05", "5");
		hours.put("06", "6");
		hours.put("07", "7");
		hours.put("08", "8");
		hours.put("09", "9");
		hours.put("10", "10");
		hours.put("11", "11");
		hours.put("12", "12");
		hours.put("13", "13");
		hours.put("14", "14");
		hours.put("15", "15");
		hours.put("16", "16");
		hours.put("17", "17");
		hours.put("18", "18");
		hours.put("19", "19");
		hours.put("20", "20");
		hours.put("21", "21");
		hours.put("22", "22");
		hours.put("23", "23");
		
		return hours;
	}
	
	@ModelAttribute("minutesOfDayList")
	public Map<String,String> populateMinutesList(){
		
		Map<String,String> minutes = new LinkedHashMap<String,String>();
		minutes.put("00", "00");
		minutes.put("30", "30");
		return minutes;
	}
		
    @RequestMapping("/employees")
    public ModelAndView viewEmployees() {
    	ModelAndView modelAndView = new ModelAndView("employees");
    	modelAndView.addObject("test", new Employee());
        return modelAndView;
    }
    
    @RequestMapping("/skills")
    public ModelAndView viewSkills(){
    	ModelAndView modelAndView = new ModelAndView("skills");
    	modelAndView.addObject("command", new FormSkill());
    	return modelAndView;
    }
    
    @RequestMapping("/shifts")
    public ModelAndView viewShifts(){
    	ModelAndView modelAndView = new ModelAndView("shifts");
    	modelAndView.addObject("command", new FormShift());
    	return modelAndView;
    }
    
    @RequestMapping("/home")
    public void home(HttpSession session){
    	if(session.getAttribute("scheduleHandler") == null){
    		setupScheduleHandler(session);
    	}
    }
    
    private void setupScheduleHandler(HttpSession session){
		ScheduleHandler scheduleHandler = new ScheduleHandler();
		session.setAttribute("scheduleHandler", scheduleHandler);
    }
}
