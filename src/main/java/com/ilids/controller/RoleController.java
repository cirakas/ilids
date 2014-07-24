/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ilids.controller;

import com.ilids.domain.Menu;
import com.ilids.domain.Role;
import com.ilids.service.RoleService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 *
 * @author cirakas
 */
@Controller
public class RoleController {

    @Autowired
    public RoleService roleService;

    @RequestMapping(value = "role", method = RequestMethod.GET)
    public String show() {
        return "/role/role";
    }

    @ModelAttribute("roleModel")
    public Role getRole() {
        return new Role();
    }

    @ModelAttribute("roleList")
    public List<Role> getRoleList() {
        return roleService.getAllRoles();
    }
    
    @ModelAttribute("menuList")
    public List<Menu> getMenuList(){
       List<Menu> menuList=roleService.getAllMenu();
        return menuList;
      }
            Long currentUserId = 0l;

    @RequestMapping(value = "/editRoles", method = RequestMethod.POST)
    @ResponseBody
    public Role editRoles(@RequestParam("id") String id) {
        Role role = new Role();
        if (id != null) {
            currentUserId = Long.valueOf(id);
        }
        return role;
    }
    
     @RequestMapping(value = "/saveRole", method = RequestMethod.POST)
    public String addRole(Role role, RedirectAttributes flash) {
        role=roleService.saveNewRole(role);
        roleService.saveMenuItems(role);
        return "redirect:/role";
    }
}