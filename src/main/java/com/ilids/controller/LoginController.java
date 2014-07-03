package com.ilids.controller;

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
        model.addAttribute("users", sessionRegistry.getAllPrincipals());
        model.addAttribute("SystemSettings", systemSettingsService.getAllSystemSettings().get(0));
      // model.addAttribute("dataList", dataService.getAllData()); 
        
        return "home";
    }
     @RequestMapping(value = "/getDataFromDB", method = RequestMethod.GET)
    public String getDataFromDB() {
         System.out.println("Iside the controller-----------------------");
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
