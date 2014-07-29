/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ilids.service;

import com.ilids.conf.ServerConfig;
import com.ilids.dao.DataRepository;
import com.ilids.dao.SystemSettingsRepository;
import com.ilids.dao.UserRepository;
import com.ilids.domain.Data;
import com.ilids.domain.MailSms;
import com.ilids.domain.SystemSettings;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;

/**
 *
 * @author cirakas
 */
public class AlertSchedulerService {

    private static final Logger logger = LoggerFactory.getLogger(AlertSchedulerService.class);

    @Autowired
    private DataRepository dataRepository;

    @Autowired
    private MailSmsService mailSmsService;

    @Autowired
    private SystemSettingsRepository systemSettingsRepository;

    @Autowired
    private MailSender mailSender;

    public void checkForAlerts() {

	String lastScheduleValue = "";
	String currentDateValue = "";
	SimpleMailMessage message = null;
	DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	if (ServerConfig.latestAlertsScheduleCheckTime == 1405535461) {
	    ServerConfig.latestAlertsScheduleCheckTime = System.currentTimeMillis();
	}
	Date scheduleDate = new Date(ServerConfig.latestAlertsScheduleCheckTime);
	long currentDateTime = System.currentTimeMillis();
	Date currentDate = new Date(currentDateTime);
	currentDateValue = df.format(currentDate);
	lastScheduleValue = df.format(scheduleDate);

	logger.info("lastScheduleValue RUN" + lastScheduleValue);

	logger.info("currentDateValue RUN" + currentDateValue);

	int i = 0;
	List<MailSms> mailSmsList=null;
	SystemSettings systemSettings = systemSettingsRepository.getAll().get(0);
	List<Data> alertDataList = dataRepository.getAllAlertDataForSchedule(lastScheduleValue, currentDateValue, systemSettings.getMdv());
	if(alertDataList.size()>0){
	    mailSmsList=mailSmsService.getAllMailSmsList();
	}
	
	
	for (Data alertDate : alertDataList) {
	    if (i > 3) {//Remove this code to send the mail to more than three
		break;
	    }
	  for(MailSms mailsms:mailSmsList){
	      if (i > 3) {//Remove this code to send the mail to more than three
		break;
	    }
	    message = new SimpleMailMessage();
	    message.setTo(mailsms.getMail());
	    message.setSubject("Attention!!!");
	    message.setText("There is an alert on " + alertDate.getTime() + " for " + alertDate.getAddressMap().getParamName() + " value ::" + alertDate.getData());
	    message.setFrom("joby@cirakas.com");
	    mailSender.send(message);
	    }
	    i++;
	}

	ServerConfig.latestAlertsScheduleCheckTime = currentDateTime;
	//List<User> userList=userRepository.getAll();
	logger.info("Scheduler started to run");

    }
}
