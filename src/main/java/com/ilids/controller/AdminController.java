package com.ilids.controller;

import com.ilids.IService.ExceptionLogService;
import com.ilids.IService.RoleService;
import com.ilids.IService.UserService;
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
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.util.Properties;
import javax.validation.Valid;
import org.codehaus.jackson.map.util.JSONPObject;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 *
 * @author cirakas
 */
@Controller
public class AdminController {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @Resource(name = "sessionRegistry")
    private SessionRegistryImpl sessionRegistry;

    @Autowired
    ExceptionLogService exceptionLogService;

    /**
     *
     * @return
     */
    @ModelAttribute("users")
    public List<User> getUsers() {
	return userService.getAllUsersExceptAdmin();
    }

    Long currentUserId = 0l;

    /**
     *
     */
    public String module = "";

    /**
     *
     * @return
     */
    @ModelAttribute("roles")
    public List<Role> getRoles() {
	return roleService.getAllRoles();
    }

    /**
     *
     * @return
     */
    @ModelAttribute("rolesWithoutRestrictedOnes")
    public List<Role> getRoles2() {
	return roleService.getAllRolesExceptRestrictedOnes();
    }

    /**
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "/admin", method = RequestMethod.GET)
    public String adminwelcome(Model model) {
	return "/admin/users";
    }

    /**
     *
     * @param enabled
     * @param userid
     * @param model
     * @return
     */
    @RequestMapping(value = "/admin/enabled", method = RequestMethod.POST)
    public String enabledchange(@RequestParam Boolean enabled, @RequestParam Long userid, Model model) {
	userService.changeEnabled(userid, enabled);
	return "redirect:/admin";
    }

    /**
     *
     * @param roleid
     * @param userid
     * @param model
     * @return
     */
    @RequestMapping(value = "/admin/addrole", method = RequestMethod.POST)
    public String adminchange(@RequestParam Long roleid, @RequestParam Long userid, Model model) {
	userService.addRoleToUser(roleid, userid);
	return "redirect:/admin";
    }

    /**
     *
     * @param roleid
     * @param userid
     * @param model
     * @return
     */
    @RequestMapping(value = "/admin/removerole", method = RequestMethod.POST)
    public String adminchange2(@RequestParam Long roleid, @RequestParam Long userid, Model model) {
	userService.takeRoleFromUser(roleid, userid);
	return "redirect:/admin";
    }

    /**
     *
     * @param userid
     * @param model
     * @return
     */
    @RequestMapping(value = "/admin/removeuser", method = RequestMethod.POST)
    public String removeuser(@RequestParam Long userid, Model model) {
	try {
	    userService.removeUserFromDatabase(userid);
	} catch (Exception ex) {
	    exceptionLogService.createLog((User) sessionRegistry.getSessionInformation("loginUser").getPrincipal(), ex, module,
		    "User removal is failed [ Controller : ' " + this.getClass().toString() + " '@@ Method Name : 'removeuser ' ]");
	}
	return "redirect:/admin";
    }

    /**
     *
     * @param newRole
     * @param action
     * @param roleName
     * @return
     */
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

    /**
     *
     * @param name
     * @return
     */
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

    /**
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "/user", method = RequestMethod.GET)
    public String userManagement(Model model) {
	model.addAttribute("userModel", new User());
	getModuleName();
	return "/user/users";
    }

    /**
     *
     */
    public void getModuleName() {
	Properties prop = new Properties();
	String propFileName = "messages_en.properties";
	InputStream inputStream = getClass().getClassLoader().getResourceAsStream(propFileName);
	try {
	    prop.load(inputStream);
	    module = prop.getProperty("label.userManagement");
	} catch (IOException ex1) {

	}
    }

    /**
     *
     * @param user
     * @param errors
     * @param model
     * @param flash
     * @return
     */
    @RequestMapping(value = "/saveUser", method = RequestMethod.POST)
    public String saveUser(@Valid User user, BindingResult errors, Model model, RedirectAttributes flash) {
	if (errors.hasErrors()) {
	    return "/user/users";
	} else {
	    Boolean status = false;
	    try {
		status = userService.addNewUserToDatabase(user);
	    } catch (Exception ex) {
		exceptionLogService.createLog((User) sessionRegistry.getSessionInformation("loginUser").getPrincipal(), ex, module,
			"User creation is failed [ Controller : ' " + this.getClass().toString() + " '@@ Method Name : 'saveUser ' ]");
	    }
	    if (status) {
		flash.addFlashAttribute("success", "User has been successfully created.");
	    } else {
		flash.addFlashAttribute("error", "User cannot be created.");
	    }
	    return "redirect:/user";
	}
    }

    /**
     *
     * @param id
     * @param user
     * @param errors
     * @param model
     * @param flash
     * @return
     */
    @RequestMapping(value = "/saveUser/{id}", method = RequestMethod.POST)
    public String editUser(@PathVariable("id") String id, @Valid User user, BindingResult errors, Model model, RedirectAttributes flash) {
	long userid = Long.valueOf(id);
	user.setId(userid);
	if (errors.hasErrors()) {
	    return "/user/users";
	} else {
	    Boolean status = false;
	    try {
		status = userService.updateNewUserToDatabase(user);
	    } catch (Exception ex) {
		exceptionLogService.createLog((User) sessionRegistry.getSessionInformation("loginUser").getPrincipal(), ex, module,
			"User updation is failed [ Controller : ' " + this.getClass().toString() + " '@@ Method Name : 'editUser ' ]");
	    }

	    if (status) {
		flash.addFlashAttribute("success", "User has been successfully created.");
	    } else {
		flash.addFlashAttribute("error", "User cannot be created.");
	    }
	    return "redirect:/user";
	}
    }

    /**
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/editUser", method = RequestMethod.POST)
    @ResponseBody
    public User editUser(@RequestParam("id") String id) {
	User user = new User();
	if (id != null) {
	    currentUserId = Long.valueOf(id);
	}
	user = userService.findById(currentUserId);
	user.setPassword(null);
	user.setRoleId(String.valueOf(user.getRole().getId()));
	return user;
    }

    /**
     *
     * @param email
     * @return
     * @throws ParseException
     */
    @RequestMapping(value = "/duplicateUser", method = RequestMethod.POST)
    @ResponseBody
    public boolean duplicateUser(@RequestParam("email") String email) throws ParseException {
	return userService.getAllUserMailData(email);
    }

    /**
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "/getData", method = RequestMethod.GET)
    public @ResponseBody
    JSONPObject getData(Model model) {
	JSONPObject jsn = new JSONPObject("hello", "hello");
	ModelAndView mav = new ModelAndView("/user");
	mav.addObject("data", "12222");
	return jsn;
    }

    /**
     *
     * @param userId
     * @param model
     * @return
     */
    @RequestMapping(value = "/deleteUser", method = RequestMethod.POST)
    public String deleteUser(@RequestParam Long userId, Model model) {
	Boolean status = false;
	try {
	    userService.removeUserFromDatabase(userId);
	    status = true;
	} catch (Exception ex) {
	    exceptionLogService.createLog((User) sessionRegistry.getSessionInformation("loginUser").getPrincipal(), ex, module,
		    "User deletion is failed [ Controller : ' " + this.getClass().toString() + " '@@ Method Name : 'deleteUser ' ]");
	    status = true;
	}

	return "redirect:/user";
    }
}
