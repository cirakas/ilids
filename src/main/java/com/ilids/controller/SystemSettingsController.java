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
import javax.validation.Valid;
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

    Long currentUserId = 0l;

    @RequestMapping(value = "/systemsettings", method = RequestMethod.GET)
    public String show() {
        System.out.println("Inside the show");
        return "/systemsettings/systemsettings";
    }

    @RequestMapping(value = "/saveSystemSettings", method = RequestMethod.POST)
    public String addSystemSettings(SystemSettings systemSettings, BindingResult errors, RedirectAttributes flash) {
        if (errors.hasErrors()) {
            return "/systemsettings/systemsettings";
        } else {
            if (systemSettingsService.addSystemSettings(systemSettings)) {
                flash.addFlashAttribute("success", "System settings has been successfully added.");
            } else {
                flash.addFlashAttribute("error", "Could not add System settings.");
            }
            return "redirect:/systemsettings";
        }
    }

    //      List<ObjectError> errorList=errors.getAllErrors();    To Print the  errors
    //      for(ObjectError objError:errorList){
    //          System.out.println("---------"+objError.getDefaultMessage());
    //      }
    @RequestMapping(value = "/saveSystemSettings/{id}", method = RequestMethod.POST)
    public String updateSystemsettings(@PathVariable("id") String id, @Valid SystemSettings systemSettings, BindingResult errors, Model model, RedirectAttributes flash) {
        long systemsettingsid = Long.valueOf(id);
        systemSettings.setId(systemsettingsid);
        if (errors.hasErrors()) {
            return "/systemsettings/systemsettings";
        } else {
            if (systemSettingsService.updateSystemSettings(systemSettings)) {
                flash.addFlashAttribute("success", "User has been successfully created.");
            } else {
                flash.addFlashAttribute("error", "User cannot be created.");
            }
            return "redirect:/systemsettings";
        }

    }

    @RequestMapping(value = "/editSystemsettings", method = RequestMethod.POST)
    @ResponseBody
    public SystemSettings editSystemsettings(@RequestParam("id") String id) {
        SystemSettings systemSettings = new SystemSettings();
        if (id != null) {
            currentUserId = Long.valueOf(id);
        }
        systemSettings = systemSettingsService.findById(currentUserId);
        return systemSettings;
    }

    @RequestMapping(value = "/deleteSystemsettings", method = RequestMethod.POST)
    public String deleteSystemsettings(@RequestParam Long systemSettingsId, Model model) {
        systemSettingsService.remove(systemSettingsId);
        return "redirect:/systemsettings";
    }

}
