package com.ilids.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;


import com.ilids.domain.Role;
import com.ilids.domain.User;
import com.ilids.service.RoleService;
import com.ilids.service.UserService;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.codehaus.jackson.map.util.JSONPObject;
import org.json.JSONObject;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class AdminController {

    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;
    @Resource(name = "sessionRegistry")
    private SessionRegistryImpl sessionRegistry;

    @ModelAttribute("users")
    public List<User> getUsers() {
        return userService.getAllUsersExceptAdmin();
    }
    
    Long currentUserId=0l;

//    @ModelAttribute("userModel")
//    public User getUser() {
//        return new User();
//    }

    @ModelAttribute("roles")
    public List<Role> getRoles() {
        return roleService.getAllRoles();
    }

    @ModelAttribute("rolesWithoutRestrictedOnes")
    public List<Role> getRoles2() {
        return roleService.getAllRolesExceptRestrictedOnes();
    }

    @RequestMapping(value = "/admin", method = RequestMethod.GET)
    public String adminwelcome(Model model) {
        return "/admin/users";
    }

    @RequestMapping(value = "/admin/enabled", method = RequestMethod.POST)
    public String enabledchange(@RequestParam Boolean enabled, @RequestParam Long userid, Model model) {
        userService.changeEnabled(userid, enabled);
        return "redirect:/admin";
    }

    @RequestMapping(value = "/admin/addrole", method = RequestMethod.POST)
    public String adminchange(@RequestParam Long roleid, @RequestParam Long userid, Model model) {
        userService.addRoleToUser(roleid, userid);
        return "redirect:/admin";
    }

    @RequestMapping(value = "/admin/removerole", method = RequestMethod.POST)
    public String adminchange2(@RequestParam Long roleid, @RequestParam Long userid, Model model) {
        userService.takeRoleFromUser(roleid, userid);
        return "redirect:/admin";
    }

    @RequestMapping(value = "/admin/removeuser", method = RequestMethod.POST)
    public String removeuser(@RequestParam Long userid, Model model) {
        userService.removeUserFromDatabase(userid);
        return "redirect:/admin";
    }

    @RequestMapping(value = "/admin/createorremoverole/", method = RequestMethod.POST)
    public String createOrRemoveRole(@RequestParam("newrole") String newRole, @RequestParam("action") String action,
            @RequestParam("rolename") String roleName) {
        if (action.equals("add")) {
            roleService.createNewRole(newRole);
        } else {
            roleService.delete(roleName);
        }
        return "redirect:/admin";
    }

    @RequestMapping(value = "/admin/invalidate/{name}", method = RequestMethod.GET)
    public String invalidate(@PathVariable String name) {
        List<Object> allPrincipals = sessionRegistry.getAllPrincipals();
        for (Object o : allPrincipals) {
            if (((org.springframework.security.core.userdetails.User) o).getUsername().equals(name)) {
                List<SessionInformation> allSessions = sessionRegistry.getAllSessions(o, false);
                for (SessionInformation si : allSessions) {
                    si.expireNow();
                }
                break;

            }
        }
        return "redirect:/home";
    }
    
     @RequestMapping(value = "/user", method = RequestMethod.GET)
    public String userManagement(Model model) {
	model.addAttribute("userModel", new User());
        return "/user/users";
    }
    
      @RequestMapping(value = "/saveUser", method = RequestMethod.POST)
    public String saveUser(@Valid User user, BindingResult errors, Model model, RedirectAttributes flash) {
	if (errors.hasErrors()) {
           return "/user/users";
        } else {
            if (userService.addNewUserToDatabase(user))
                flash.addFlashAttribute("success", "User has been successfully created.");
            else
                flash.addFlashAttribute("error", "User cannot be created.");
            return "redirect:/user";
        }
    }
    
    
      @RequestMapping(value = "/saveUser/{id}", method = RequestMethod.POST)
    public String editUser(@PathVariable("id") String id,@Valid User user, BindingResult errors, Model model, RedirectAttributes flash) {
        System.out.println("Inside the edit id id------"+id);
        long userid=Long.valueOf(id);
        user.setId(userid);
       if (errors.hasErrors()) {
           return "/user/users";
        } else {
            if (userService.updateNewUserToDatabase(user))
                flash.addFlashAttribute("success", "User has been successfully created.");
            else
                flash.addFlashAttribute("error", "User cannot be created.");
            return "redirect:/user";
        }
    }
    
    
//     @RequestMapping(value = "/editUser", method = RequestMethod.POST)
//    public void editUser(Model model, HttpServletRequest request) {
//	//System.out.println("Inside the editUser------");
//	String userId=request.getParameter("userId");
//	if(userId!=null)
//	    currentUserId=Long.valueOf(userId);
//	User editUser=userService.findById(currentUserId);
//	model.addAttribute("userModel",editUser);
    
  //  }
    
    @RequestMapping(value = "/editUser", method = RequestMethod.POST)
     @ResponseBody
	public  User editUser(@RequestParam("id") String id) {
        User user=new User();
        if(id!=null)
        currentUserId=Long.valueOf(id);
        user=userService.findById(currentUserId);
        user.setPassword(null);
	user.setRoleId(String.valueOf(user.getRole().getId()));
        return user; 
	}
        
        @RequestMapping(value = "/getData", method = RequestMethod.GET)
        public @ResponseBody  JSONPObject getData(Model model) {
        JSONPObject jsn=new JSONPObject("hello","hello");
        ModelAndView mav = new ModelAndView("/user");
        mav.addObject("data", "12222");
        return jsn; 
       }
        
        
        
	
    @RequestMapping(value = "/deleteUser", method = RequestMethod.POST)
    public String deleteUser(@RequestParam Long userId, Model model) {
        userService.removeUserFromDatabase(userId);
        return "redirect:/user";
    }
}
