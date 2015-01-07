/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ilids.controller;

import com.ilids.domain.Options;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.support.SessionStatus;

/**
 *
 * @author user
 */
@Controller
public class OptionsController {

    public String opt;

    @RequestMapping(value = "/energy/{EMeter}", method = RequestMethod.GET)
    public String selectedEnergy(@PathVariable("EMeter") String selOpt, HttpSession session,SessionStatus status) {
        System.out.println("----");
        opt = selOpt;
        status.setComplete();
        session.setAttribute("selection", opt);
        System.out.println("---" + opt);
        return "redirect:/energy";

    }

    @RequestMapping(value = "/thermo/{TMeter}", method = RequestMethod.GET)
    public String selectedTherm(@PathVariable("TMeter") String selThem, HttpSession session,SessionStatus status) {
        System.out.println("----");
        opt = selThem;
         status.setComplete();
        session.setAttribute("selection", opt);
        System.out.println("---" + opt);
        return "redirect:/thermal";

    }
    

}
