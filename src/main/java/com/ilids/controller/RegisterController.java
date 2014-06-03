package com.ilids.controller;

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
import com.ilids.service.UserService;

@Controller
public class RegisterController {

    @Autowired
    private UserService userService;

    @ModelAttribute("user")
    public User getUser() {
        return new User();
    }

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public String showRegisterForm() {
        return "register/register";
    }
    
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String create(@Valid User user, BindingResult errors, Model model, RedirectAttributes flash) {
        if (errors.hasErrors()) {
            return "/register/register";
        } else {
            if (userService.addNewUserToDatabase(user))
                flash.addFlashAttribute("success", "User has been successfully created.");
            else
                flash.addFlashAttribute("error", "User cannot be created.");
            return "redirect:/register";
        }
    }

}
