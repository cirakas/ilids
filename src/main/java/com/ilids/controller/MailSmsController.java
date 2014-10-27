/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ilids.controller;

import com.ilids.IService.ExceptionLogService;
import com.ilids.IService.MailSmsService;
import com.ilids.domain.MailSms;
import com.ilids.domain.User;
import com.ilids.service.impl.ExceptionLogServiceImpl;
import com.ilids.service.impl.MailSmsServiceImpl;
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
public class MailSmsController {

    @Autowired
    private MailSmsService mailSmsService;
    @Resource(name = "sessionRegistry")
    private SessionRegistryImpl sessionRegistry;
    @Autowired
    ExceptionLogService exceptionLogService;

    /**
     *
     */
    public MailSmsController() {
    }

    /**
     *
     * @return
     */
    @ModelAttribute("mailSmsModel")
    public MailSms getDevices() {
	return new MailSms();
    }

    /**
     *
     * @return
     */
    @ModelAttribute("mailSmsList")
    public List<MailSms> getDeviceList() {
	return mailSmsService.getAllMailSmsList();
    }
    MailSms mailSmsModel=null;

    Long currentMailSmsId = 0l;
    Long smsId=0l;
    String module = "";

    /**
     *
     * @return
     */
    @RequestMapping(value = "mailsms", method = RequestMethod.GET)
    public String show() {
        smsId=0l;
	getModuleName();
	return "/mailsms/mailsms";
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
	    module = prop.getProperty("label.mainAndSmsManagement");
	} catch (IOException ex1) {

	}
    }
    
    @ModelAttribute("mailSmsModel")
    public MailSms getMailSms() {
        return new MailSms();
    }


    /**
     *
     * @param mailSms
     * @param flash
     * @return
     */
    @RequestMapping(value = "saveMailSms", method = RequestMethod.POST)
    public String addMailSms(MailSms mailSms, RedirectAttributes flash) {
	try {
	    mailSmsService.saveMailSms(mailSms);
	} catch (Exception ex) {
	    exceptionLogService.createLog((User) sessionRegistry.getSessionInformation("loginUser").getPrincipal(), ex, module,
		    "Mail/Sms information creation failed [ Controller : ' " + this.getClass().toString() + " '@@ Method Name : 'addMailSms' ]");
	}
	return "redirect:/mailsms";
    }

    /**
     *
     * @param mailSms
     * @param flash
     * @return
     */
    @RequestMapping(value = "saveMailSms/{id}", method = RequestMethod.POST)
    public String updateMailSms(MailSms mailSms, RedirectAttributes flash) {
	try {
	    mailSmsService.updateMailSms(mailSms);
	} catch (Exception ex) {
	    exceptionLogService.createLog((User) sessionRegistry.getSessionInformation("loginUser").getPrincipal(), ex, module,
		    "Mail/Sms information updation failed [ Controller : ' " + this.getClass().toString() + " '@@ Method Name : 'updateMailSms' ]");
	}
	return "redirect:/mailsms";
    }

    /**
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/editMailSms", method = RequestMethod.POST)
    @ResponseBody
    public MailSms editMailSms(@RequestParam("id") String id) {
	MailSms mailSms = new MailSms();
	if (id != null) {
	    currentMailSmsId = Long.valueOf(id);
	}
	mailSms = mailSmsService.findById(currentMailSmsId);
        smsId=mailSms.getId();
	return mailSms;
    }

    /**
     *
     * @param mail
     * @return
     * @throws ParseException
     */
    @RequestMapping(value = "/duplicateMailSms", method = RequestMethod.POST)
    @ResponseBody
    public boolean duplicateMailSms(@RequestParam("mail") String mail) throws ParseException {
	return mailSmsService.getAllMailData(mail,smsId);

    }

    /**
     *
     * @param mailSmsId
     * @return
     */
    @RequestMapping(value = "/deleteMailSms", method = RequestMethod.POST)
    public String delete(@RequestParam("mailsmsId") Long mailSmsId) {
	try {
	    mailSmsService.remove(mailSmsId);
	} catch (Exception ex) {
	    exceptionLogService.createLog((User) sessionRegistry.getSessionInformation("loginUser").getPrincipal(), ex, module,
		    "Mail/Sms information deletion failed [ Controller : ' " + this.getClass().toString() + " '@@ Method Name : 'delete' ]");
	}
	return "redirect:/mailsms";
    }

}
