/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ilids.controller;

import com.ilids.domain.Menu;
import com.ilids.domain.Role;
import com.ilids.domain.User;
import com.ilids.service.ExceptionLogService;
import com.ilids.service.RoleService;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
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
    @Resource(name = "sessionRegistry")
    private SessionRegistryImpl sessionRegistry;
    @Autowired
    ExceptionLogService exceptionLogService;

    String module="";
    
    @RequestMapping(value = "role", method = RequestMethod.GET)
    public String show() {
        getModuleName();
	return "/role/role";
    }

    public void getModuleName(){
                Properties prop = new Properties();
                String propFileName = "messages_en.properties";
                InputStream inputStream = getClass().getClassLoader().getResourceAsStream(propFileName);
                try {
                    prop.load(inputStream);
                    module = prop.getProperty("label.roleManagement");
                } catch (IOException ex1) {

                }
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
    public List<Menu> getMenuList() {
	List<Menu> menuList = roleService.getAllMenu();
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
        try {
            role = roleService.editRole(currentUserId);
        } catch (Exception ex) {
             exceptionLogService.createLog((User) sessionRegistry.getSessionInformation("loginUser").getPrincipal(), ex, module,
                                              "Role updation is failed [ Controller : ' "+this.getClass().toString()+" '@@ Method Name : 'editRoles' ]");
        }
	return role;
    }

    @RequestMapping(value = "/saveRole", method = RequestMethod.POST)
    public String updateRole(Role role, RedirectAttributes flash) {
	try{
            role = roleService.saveNewRole(role);
            roleService.saveMenuItems(role);
        }catch(Exception ex){
            exceptionLogService.createLog((User) sessionRegistry.getSessionInformation("loginUser").getPrincipal(), ex, module,
                                              "Role saving is failed [ Controller : ' "+this.getClass().toString()+" '@@ Method Name : 'updateRole' ]");
              
        }
	return "redirect:/role";
    }

    @RequestMapping(value = "/saveRole/{id}", method = RequestMethod.POST)
    public String addRole(Role role, RedirectAttributes flash, @PathVariable("id") String id) {
	Long roleId = Long.valueOf(id);
	role.setId(roleId);
	try{
            role = roleService.updateRole(role);
            roleService.saveMenuItems(role);
        }catch(Exception ex){
            exceptionLogService.createLog((User) sessionRegistry.getSessionInformation("loginUser").getPrincipal(), ex, module,
                                              "Role saving is failed [ Controller : ' "+this.getClass().toString()+" '@@ Method Name : 'addRole' ]");
        }
	return "redirect:/role";
    }

    @RequestMapping(value = "/deleteRole", method = RequestMethod.POST)
    public String delete(@RequestParam("roleId") Long roleId,RedirectAttributes flash) {
         boolean deleteResult=false;
        try{
             deleteResult=roleService.remove(roleId);
        }catch(Exception ex){
            exceptionLogService.createLog((User) sessionRegistry.getSessionInformation("loginUser").getPrincipal(), ex, module,
                                              "Role removal failed [ Controller : ' "+this.getClass().toString()+" '@@ Method Name : 'delete' ]");
              
        }
	if(deleteResult){
	    flash.addFlashAttribute("successMessage","Deleted successfully.");
	}else{
	    flash.addFlashAttribute("deleteMessage","Can't delete the role. Role is used for a user."); 
	}
	return "redirect:/role";
    }
    
    @RequestMapping(value="/duplicateRole", method = RequestMethod.POST)
    @ResponseBody
    public boolean duplicateRole(@RequestParam("name") String name) throws ParseException{
        return roleService.getAllRoleNameData(name);
        
        
        
    }

}
