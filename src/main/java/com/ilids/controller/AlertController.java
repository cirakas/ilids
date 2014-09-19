/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ilids.controller;

import com.ilids.domain.Alerts;
import com.ilids.domain.Devices;
import com.ilids.domain.User;
import javax.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 *
 * @author cirakas
 */
@Controller
public class AlertController {
    
    @ModelAttribute("alerts")
    public Alerts getAlerts(){
	return new Alerts();
    }
    
    @ModelAttribute("devices")
    public Devices getDevices(){
	return new Devices();
    }
    
    @RequestMapping(value = "/alerts", method = RequestMethod.GET)
    public String showAlertsForm() {
        return "alerts/add";
    }
    
    @RequestMapping(value = "/alerts", method = RequestMethod.POST)
    public String create(@Valid Alerts alerts, BindingResult errors, Model model, RedirectAttributes flash) {
	return "redirect:/alerts";
    }
    
     @RequestMapping(value = "/deviceUrl", method = RequestMethod.POST)
    public String devices(@Valid Devices devices, BindingResult errors, Model model, RedirectAttributes flash) {
	return "redirect:/alerts";
    }
    
}
