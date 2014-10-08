package com.ilids.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ilids.domain.SystemSettings;
import com.ilids.domain.User;
import com.ilids.service.ExceptionLogService;
import com.ilids.service.SystemSettingsService;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.validation.Valid;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class SystemSettingsController {

    @Autowired
    private SystemSettingsService systemSettingsService;


    @ModelAttribute("SystemSettingsModel")
    public SystemSettings SystemSettings() {
        return new SystemSettings();
    }

    @ModelAttribute("SystemSettingsList")
    public List<SystemSettings> getSystemSettingsList() {
        return systemSettingsService.getAllSystemSettings();
    }
    
    @Resource(name = "sessionRegistry")
    private SessionRegistryImpl sessionRegistry;
    @Autowired
    ExceptionLogService exceptionLogService;

    Long currentUserId = 0l;
    String module="";
     
    @RequestMapping(value = "/systemsettings", method = RequestMethod.GET)
    public String show() {
        getModuleName();
        return "/systemsettings/systemsettings";
    }
    
     public void getModuleName(){
                Properties prop = new Properties();
                String propFileName = "messages_en.properties";
                InputStream inputStream = getClass().getClassLoader().getResourceAsStream(propFileName);
                try {
                    prop.load(inputStream);
                    module = prop.getProperty("label.systemSettingsManagement");
                } catch (IOException ex1) {

                }
    }

    @RequestMapping(value = "/saveSystemSettings", method = RequestMethod.POST)
    public String addSystemSettings(SystemSettings systemSettings, BindingResult errors, RedirectAttributes flash) {
        if (errors.hasErrors()) {
            return "/systemsettings/systemsettings";
        } else {
            boolean status=false;
           try{
                status=systemSettingsService.addSystemSettings(systemSettings);
           }catch(Exception ex){
             exceptionLogService.createLog((User) sessionRegistry.getSessionInformation("loginUser").getPrincipal(), ex, module,
                                              "Settings creation failed [ Controller : ' "+this.getClass().toString()+" '@@ Method Name : 'addSystemSettings' ]");
           }
            if (status) {
                flash.addFlashAttribute("success", "System settings has been successfully added.");
            } else {
                flash.addFlashAttribute("error", "Could not add System settings.");
            }
            return "redirect:/systemsettings";
        }
    }

    //      List<ObjectError> errorList=errors.getAllErrors();    To Print the  errors
    //      for(ObjectError objError:errorList){
    //      }
    @RequestMapping(value = "/saveSystemSettings/{id}", method = RequestMethod.POST)
    public String updateSystemsettings(@PathVariable("id") String id, @Valid SystemSettings systemSettings, BindingResult errors, Model model, RedirectAttributes flash) {
        try{
            long systemsettingsid = Long.valueOf(id);
            systemSettings.setId(systemsettingsid);
            if (errors.hasErrors()) {
                return "/systemsettings/systemsettings";
            } else {
                boolean status = false;
                status = systemSettingsService.updateSystemSettings(systemSettings);

                if (status) {
                    flash.addFlashAttribute("success", "Settings has been successfully created.");
                } else {
                    flash.addFlashAttribute("error", "Settings cannot be created.");
                }
                return "redirect:/systemsettings";
            }
        }catch(Exception ex){
           exceptionLogService.createLog((User) sessionRegistry.getSessionInformation("loginUser").getPrincipal(), ex, module,
                                              "Settings updation is failed [ Controller : ' "+this.getClass().toString()+" '@@ Method Name : 'updateSystemsettings' ]");
       }
          return "/systemsettings/systemsettings";
    }

    @RequestMapping(value = "/editSystemsettings", method = RequestMethod.POST)
    @ResponseBody
    public SystemSettings editSystemsettings(@RequestParam("id") String id) {
        SystemSettings systemSettings = new SystemSettings();
        if (id != null) {
            currentUserId = Long.valueOf(id);
        }
        try {
            systemSettings = systemSettingsService.findById(currentUserId);
        } catch (Exception ex) {
            exceptionLogService.createLog((User) sessionRegistry.getSessionInformation("loginUser").getPrincipal(), ex, module,
                                              "Settings updation is failed [ Controller : ' "+this.getClass().toString()+" '@@ Method Name : 'editSystemsettings' ]");
       }
        return systemSettings;
    }

    @RequestMapping(value = "/deleteSystemsettings", method = RequestMethod.POST)
    public String deleteSystemsettings(@RequestParam Long systemSettingsId, Model model) {
       try{
            systemSettingsService.remove(systemSettingsId);
       }catch(Exception ex){
           exceptionLogService.createLog((User) sessionRegistry.getSessionInformation("loginUser").getPrincipal(), ex, module,
                                              "Settings deletion is failed [ Controller : ' "+this.getClass().toString()+" '@@ Method Name : 'deleteSystemsettings' ]");
       }
        return "redirect:/systemsettings";
    }

}
