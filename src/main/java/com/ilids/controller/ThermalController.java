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
@SessionAttributes("menuIdLists")
public class ThermalController {

//    @Autowired
//    private DeviceService deviceService;
    @Autowired
    private RoleService roleService;

    @Resource(name = "sessionRegistry")
    private SessionRegistryImpl sessionRegistry;

//    @ModelAttribute("deviceModels")
//    public Devices getDevices() {
//        return new Devices();
//    }
    @RequestMapping(value = "thermal", method = RequestMethod.GET)
    public String show(Model model) {
        org.springframework.security.core.userdetails.User user = (org.springframework.security.core.userdetails.User) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();
        List<Object> menuIdLists = roleService.getAllMenuIds(user.getUsername());
//        List<Devices> deviceIdLists = deviceService.getAllUsedDevices();
//        model.addAttribute("deviceIdLists", deviceIdLists);
        model.addAttribute("menuIdLists", menuIdLists);
        model.addAttribute("users", sessionRegistry.getAllPrincipals());
        return "/thermal/thermal";
    }

}
