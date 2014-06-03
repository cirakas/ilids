package com.ilids.controller;

import javax.annotation.Resource;

import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class LoginController {

    @Resource(name = "sessionRegistry")
    private SessionRegistryImpl sessionRegistry;

    @RequestMapping("/home")
    public String welcome(Model model) {
        model.addAttribute("users", sessionRegistry.getAllPrincipals());
        return "home";
    }
    
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String showLoginForm() {
        return "login/login";
    }
    
    @RequestMapping("/denied")
    public String denied() {
        return "denied";
    }

    @RequestMapping("/error")
    public String error() {
        return "error";
    }
}
