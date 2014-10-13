package com.ilids.controller;

import com.ilids.IService.DataService;
import com.ilids.IService.DeviceService;
import com.ilids.IService.RoleService;
import com.ilids.IService.SystemSettingsService;
import com.ilids.IService.UserService;
import com.ilids.conf.ServerConfig;
import com.ilids.domain.Devices;
import com.ilids.domain.SystemSettings;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;

import org.springframework.security.core.session.SessionRegistryImpl;
import com.ilids.domain.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 *
 * @author cirakas
 */
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

    @Autowired
    private UserService userService;

    /**
     *
     * @return
     */
    @ModelAttribute("deviceModel")
    public Devices getDevices() {
        return new Devices();
    }

    //Same method is used for login and home redirection

    /**
     *
     * @param model
     * @return
     */
        @RequestMapping("/home")
    public String welcome(Model model) {
	ServerConfig.latestAlertsScheduleCheckTime=System.currentTimeMillis();
	org.springframework.security.core.userdetails.User user = (org.springframework.security.core.userdetails.User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	List<Object> menuIdList=roleService.getAllMenuIds(user.getUsername());

        List<Devices> deviceIdList = deviceService.getAllDevice();
        model.addAttribute("deviceIdList", deviceIdList);
	model.addAttribute("menuIdList", menuIdList);
        model.addAttribute("users", sessionRegistry.getAllPrincipals());
	List<SystemSettings> systemSettingsList=systemSettingsService.getAllSystemSettings();
	SystemSettings systemSettings= new SystemSettings();
	if(systemSettingsList!=null && systemSettingsList.size()>0){
	   systemSettings =systemSettingsList.get(0);
	}
        model.addAttribute("SystemSettings", systemSettings);

        User loginUser=userService.getUserByUserName(user.getUsername());
        if(loginUser!=null){
            sessionRegistry.registerNewSession("loginUser", loginUser);
        }

//	Data phase1PowerFactor=dataService.getLatestPowerFactorValues(30);
//	Data phase2PowerFactor=dataService.getLatestPowerFactorValues(32);
//	Data phase3PowerFactor=dataService.getLatestPowerFactorValues(34);
//	model.addAttribute("phase1PowerFactor", phase1PowerFactor.getData());
//	model.addAttribute("phase2PowerFactor", phase2PowerFactor.getData());
//	model.addAttribute("phase3PowerFactor", phase3PowerFactor.getData());
        return "home";
    }

    /**
     *
     * @return
     */
    @RequestMapping(value = "/getDataFromDB", method = RequestMethod.GET)
    public String getDataFromDB() {
        return "home";
    }

    /**
     *
     * @return
     */
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String showLoginForm() {
        return "login/login";
    }

//    @RequestMapping("/denied")
//    public String denied() {
//        return "denied";
//    }

    /**
     *
     * @param model
     * @param flash
     * @return
     */
    
     @RequestMapping(value = "/accessdenied", method = RequestMethod.GET)
    public String loginerror(ModelMap model,RedirectAttributes flash) {
        model.addAttribute("error", "true");
        flash.addFlashAttribute("ErrorMessage","User name or Password incorrect!");
        return "redirect:/login";
    }

    /**
     *
     * @return
     */
    @RequestMapping("/error")
    public String error() {
        return "error";
    }


}
