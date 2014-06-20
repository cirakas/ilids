package com.ilids.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ilids.domain.SystemSettings;
import com.ilids.service.SystemSettingsService;


@Controller
public class SystemSettingsController {

   
    @Autowired
    private SystemSettingsService systemSettingsService;

//    @ModelAttribute("users")
//    public List<User> getUsers() {
//        return userService.getAllUsersExceptAdmin();
//    }
//    
//    @ModelAttribute("users")
//    public Users getUser() {
//        return new Users();
//    }

    @ModelAttribute("SystemSettingsModel")
    public SystemSettings SystemSettings() {
        return new SystemSettings();
    }
    
     @ModelAttribute("SystemSettingsList")
    public List<SystemSettings> getSystemSettingsList() {
        return systemSettingsService.getAllSystemSettings();
    }
    
    @RequestMapping(value = "systemsettings", method = RequestMethod.GET)
    public String show() {
        System.out.println("Inside the show");
        return "/systemsettings/systemsettings";
    }

    @RequestMapping(value = "saveSystemSettings", method = RequestMethod.POST)
    public String add(SystemSettings systemSettings, RedirectAttributes flash) {
        if (systemSettingsService.addSystemSettings(systemSettings)) {
            flash.addFlashAttribute("success", "System settings has been successfully added.");
        } else {
            flash.addFlashAttribute("error", "Could not add System settings.");
        }
        return "redirect:/systemsettings";
    }

//    @RequestMapping(value = "/devices/delete", method = RequestMethod.POST)
//    public String delete(@RequestParam("deviceid") Long deviceId) {
//        systemSettingsService.remove(deviceId);
//        return "redirect:/device/add";
//    }

}
