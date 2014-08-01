/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ilids.controller;

import com.ilids.domain.MailSms;
import com.ilids.service.MailSmsService;
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
public class MailSmsController{
    
    @Autowired
    private MailSmsService mailSmsService;
    public MailSmsController() {
    }
    
    @ModelAttribute("mailSmsModel")
    public MailSms getDevices() {
        return new MailSms();
    }

    @ModelAttribute("mailSmsList")
    public List<MailSms> getDeviceList() {
        return mailSmsService.getAllMailSmsList();
    }

    Long currentMailSmsId = 0l;

    @RequestMapping(value = "mailsms", method = RequestMethod.GET)
    public String show() {
        return "/mailsms/mailsms";
    }

    @RequestMapping(value = "saveMailSms", method = RequestMethod.POST)
    public String addMailSms(MailSms mailSms, RedirectAttributes flash) {
        mailSmsService.saveMailSms(mailSms);
        return "redirect:/mailsms";
    }

    @RequestMapping(value = "saveMailSms/{id}", method = RequestMethod.POST)
    public String updateMailSms(MailSms mailSms, RedirectAttributes flash) {
	mailSmsService.updateMailSms(mailSms);
        return "redirect:/mailsms";
    }

    @RequestMapping(value = "/editMailSms", method = RequestMethod.POST)
    @ResponseBody
    public MailSms editMailSms(@RequestParam("id") String id) {
        MailSms mailSms = new MailSms();
        if (id != null) {
            currentMailSmsId = Long.valueOf(id);
        }
        mailSms = mailSmsService.findById(currentMailSmsId);
        return mailSms;
    }

    @RequestMapping(value = "/deleteMailSms", method = RequestMethod.POST)
    public String delete(@RequestParam("mailsmsId") Long mailSmsId) {
        mailSmsService.remove(mailSmsId);
        return "redirect:/mailsms";
    }
    
  
  
}
