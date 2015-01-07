/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ilids.controller;

import com.ilids.IService.DeviceService;
import com.ilids.IService.RoleService;
import com.ilids.domain.Devices;
import java.util.List;
import javax.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

/**
 *
 * @author user
 */
@Controller
@SessionAttributes("menuIdList")
public class EnergyController {

    @Autowired
    private DeviceService deviceService;

    @Autowired
    private RoleService roleService;

    @Resource(name = "sessionRegistry")
    private SessionRegistryImpl sessionRegistry;

    @ModelAttribute("deviceModel")
    public Devices getDevices() {
        return new Devices();
    }

    @RequestMapping(value = "energy", method = RequestMethod.GET)
    public String show(Model model) {
//        smsId=0l;
//	getModuleName();
        org.springframework.security.core.userdetails.User user = (org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();
        List<Object> menuIdList = roleService.getAllMenuIds(user.getUsername());
        List<Devices> deviceIdList = deviceService.getAllUsedDevices();
        model.addAttribute("deviceIdList", deviceIdList);
        model.addAttribute("menuIdList", menuIdList);
        model.addAttribute("users", sessionRegistry.getAllPrincipals());
        return "/energy/energy";
    }

}
