package com.ilids.controller;

import com.ilids.conf.ServerConfig;
import com.ilids.domain.Data;
import com.ilids.domain.SystemSettings;
import com.ilids.service.DataService;
import com.ilids.service.SystemSettingsService;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class LoginController {

    @Resource(name = "sessionRegistry")
    private SessionRegistryImpl sessionRegistry;
    
    @Autowired
    private SystemSettingsService systemSettingsService;
    
     @Autowired
    private DataService dataService;

    @RequestMapping("/home")
    public String welcome(Model model) {
	ServerConfig.latestAlertsScheduleCheckTime=System.currentTimeMillis();
        model.addAttribute("users", sessionRegistry.getAllPrincipals());
	SystemSettings systemSettings=systemSettingsService.getAllSystemSettings().get(0);
        model.addAttribute("SystemSettings", systemSettings);
	List<Data> alertData=dataService.getAllAlertData(systemSettings.getMdv());
	Data phase1PowerFactor=dataService.getLatestPowerFactorValues(30);
	Data phase2PowerFactor=dataService.getLatestPowerFactorValues(32);
	Data phase3PowerFactor=dataService.getLatestPowerFactorValues(34);
	model.addAttribute("phase1PowerFactor", phase1PowerFactor.getData());
	model.addAttribute("phase2PowerFactor", phase2PowerFactor.getData());
	model.addAttribute("phase3PowerFactor", phase3PowerFactor.getData());
	model.addAttribute("alertCount", alertData.size());
	model.addAttribute("alertData", alertData);
      // model.addAttribute("dataList", dataService.getAllData()); 
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
