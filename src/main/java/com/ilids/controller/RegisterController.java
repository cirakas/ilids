package com.ilids.controller;

import com.ilids.IService.ExceptionLogService;
import com.ilids.IService.UserService;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ilids.domain.User;
import javax.annotation.Resource;
import org.springframework.security.core.session.SessionRegistryImpl;

/**
 *
 * @author cirakas
 */
@Controller
public class RegisterController {

    @Resource(name = "sessionRegistry")
    private SessionRegistryImpl sessionRegistry;

    @Autowired
    private UserService userService;
    @Autowired
    ExceptionLogService exceptionLogService;

    /**
     *
     * @return
     */
    @ModelAttribute("user")
    public User getUser() {
	return new User();
    }

    /**
     *
     * @return
     */
    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public String showRegisterForm() {
	return "register/register";
    }

    /**
     *
     * @param user
     * @param errors
     * @param model
     * @param flash
     * @return
     */
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String create(@Valid User user, BindingResult errors, Model model, RedirectAttributes flash) {
	if (errors.hasErrors()) {
	    return "/register/register";
	} else {
	    Boolean status = false;
	    try {
		status = userService.addNewUserToDatabase(user);
	    } catch (Exception ex) {
		/*String module="";
		 Properties prop = new Properties();
		 String propFileName = "messages_en.properties";
		 InputStream inputStream = getClass().getClassLoader().getResourceAsStream(propFileName);
		 try {
		 prop.load(inputStream);
		 module = prop.getProperty("label.userManagement");
		 } catch (IOException ex1) {
                
		 }*/
		// exceptionLogService.createLog((User)sessionRegistry.getSessionInformation("loginUser").getPrincipal(), ex ,module,"User creation is failed");
	    }
	    if (status) {
		flash.addFlashAttribute("success", "User has been successfully created.");
	    } else {
		flash.addFlashAttribute("error", "User cannot be created.");
	    }
	    return "redirect:/register";
	}
    }

}
