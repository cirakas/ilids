/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ilids.controller;

import com.ilids.IService.DeviceService;
import com.ilids.IService.ExceptionLogService;
import com.ilids.IService.MailSmsService;
import com.ilids.domain.Devices;
import com.ilids.domain.MailSms;
import com.ilids.domain.User;
import com.ilids.service.impl.ExceptionLogServiceImpl;
import com.ilids.service.impl.MailSmsServiceImpl;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
/**
 *
 * @author cirakaspvt
 */

@Controller
public class ChartController {
    
    
    
    @Autowired
    ExceptionLogService exceptionLogService;
    @Autowired
    private DeviceService deviceService;
    
    @ModelAttribute("deviceModel")
    public Devices getDevices() {
        return new Devices();
    }
    
    @RequestMapping(value = "charts", method = RequestMethod.GET)
    public String show(Model model) {
    List<Devices> deviceIdList = deviceService.getAllUsedDevices(); 
    model.addAttribute("deviceIdList", deviceIdList);
	return "/charts/charts";
    }
    
}
