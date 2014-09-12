package com.ilids.controller;

import com.ilids.conf.ServerConfig;
import com.ilids.domain.Data;
import com.ilids.domain.Devices;
import com.ilids.domain.SystemSettings;
import com.ilids.service.DataService;
import com.ilids.service.DeviceService;
import com.ilids.service.RoleService;
import com.ilids.service.SystemSettingsService;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;

import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

@Controller
@SessionAttributes("menuIdList")
public class LoginController {

    @Resource(name = "sessionRegistry")
    private SessionRegistryImpl sessionRegistry;
    
    @Autowired
    private SystemSettingsService systemSettingsService;
    
    @Autowired
    private RoleService roleService;
    
    @Autowired
    private DataService dataService;
    
    @Autowired 
    private DeviceService deviceService;
    
     @ModelAttribute("deviceModel")
    public Devices getDevices() {
        return new Devices();
    }

    //Same method is used for login and home redirection
    @RequestMapping("/home")
    public String welcome(Model model) {
	ServerConfig.latestAlertsScheduleCheckTime=System.currentTimeMillis();
	User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	List<Object> menuIdList=roleService.getAllMenuIds(user.getUsername());
        List<Devices> deviceIdList = deviceService.getAllDevice();
        model.addAttribute("deviceIdList", deviceIdList);
	model.addAttribute("menuIdList", menuIdList);
        model.addAttribute("users", sessionRegistry.getAllPrincipals());
	SystemSettings systemSettings=systemSettingsService.getAllSystemSettings().get(0);
        model.addAttribute("SystemSettings", systemSettings);
	Data phase1PowerFactor=dataService.getLatestPowerFactorValues(30);
	Data phase2PowerFactor=dataService.getLatestPowerFactorValues(32);
	Data phase3PowerFactor=dataService.getLatestPowerFactorValues(34);
	model.addAttribute("phase1PowerFactor", phase1PowerFactor.getData());
	model.addAttribute("phase2PowerFactor", phase2PowerFactor.getData());
	model.addAttribute("phase3PowerFactor", phase3PowerFactor.getData());
        return "home";
    }
     @RequestMapping(value = "/getDataFromDB", method = RequestMethod.GET)
    public String getDataFromDB() {
        return "home";
    }
    
    
    
    
    
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String showLoginForm() {
        return "login/login";
    }
    
    @RequestMapping("/denied")
    public String denied() {
        return "denied";
    }

    @RequestMapping("/error")
    public String error() {
        return "error";
    }

   
}
