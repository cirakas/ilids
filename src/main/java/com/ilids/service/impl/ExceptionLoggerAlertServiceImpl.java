/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ilids.service.impl;

import com.ilids.IRepository.DataRepository;
import com.ilids.IRepository.SystemSettingsRepository;
import com.ilids.IService.ExceptionLogService;
import com.ilids.IService.ExceptionLoggerAlertService;
import com.ilids.IService.MailSmsService;
import com.ilids.conf.ServerConfig;
import com.ilids.domain.ExceptionLog;
import com.ilids.domain.MailSms;
import com.ilids.domain.SystemSettings;
import java.io.File;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Jeena
 */
@Component
@Transactional
public class ExceptionLoggerAlertServiceImpl implements ExceptionLoggerAlertService {

    @Autowired
    DataRepository dataRepository;

    @Autowired
    MailSmsService mailSmsService;

    @Autowired
    JavaMailSenderImpl mailSender;

    @Autowired
    SystemSettingsRepository systemSettingsRepository;

    @Autowired
    ExceptionLogService exceptionLogService;

    private static final Logger logger = LoggerFactory.getLogger(AlertSchedulerServiceImpl.class);

    @Override
    public void sendDailyAlertForException() throws MessagingException {

	SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
	String htmlMsg = "";
	String lastScheduleValue = "";
	String currentDateValue = "";
	MimeMessage mimeMessage = null;
	MimeMessageHelper helper = null;
	DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	if (ServerConfig.latestAlertsScheduleCheckTime == 1405535461) {
	    ServerConfig.latestAlertsScheduleCheckTime = System.currentTimeMillis();
	}
	Date scheduleDate = new Date(ServerConfig.latestAlertsScheduleCheckTime);
	long currentDateTime = System.currentTimeMillis();
	Date currentDate = new Date(currentDateTime);
	currentDateValue = df.format(currentDate);
	lastScheduleValue = df.format(scheduleDate);

	logger.info("lastScheduleValue RUN :: " + lastScheduleValue);
	logger.info("currentDateValue RUN :: " + currentDateValue);

	System.out.println("lastScheduleValue RUN :: " + lastScheduleValue);
	System.out.println("currentDateValue RUN :: " + currentDateValue);

	List<MailSms> mailSmsList = null;
	FileSystemResource res = new FileSystemResource(new File("C:\\wallPapers\\logo.jpg"));

	try {
	    List<ExceptionLog> exceptionLogList = exceptionLogService.getAllLatestException();
	    System.out.println("exceptionLogList Size *** " + exceptionLogList.size());
	    if (exceptionLogList != null && exceptionLogList.size() > 0) {
		System.out.println("************************************** *** ");
		mailSmsList = mailSmsService.getAllMailSmsList();
		for (ExceptionLog log : exceptionLogList) {
		    System.out.println("Inside mail setting for loop");
		    mimeMessage = mailSender.createMimeMessage();
		    helper = new MimeMessageHelper(mimeMessage, true, "utf-8");
		    helper.addInline("identifier1234", res);

		    htmlMsg += "<html><body>Hi Admin, <br/><br/><h1>iLids system has captured exceptions on " + sdf.format(new Date())
			    + "<br/><br/><br/> <li>Exception Type : " + log.getExceptionType() + "</li>"
			    + "<br/><br/> <li>Exception remark : " + log.getExceptionRemark() + "</li>"
			    + "<br/><br/> <li>Exception in module : " + log.getIssueCausedModule() + "</li>"
			    + "<br/><br/> <li>Exception generated time : " + log.getOccuringTime() + "</li>"
			    + "<br/><br/> <li>Exception oocured while the login of : " + log.getUserName() + "</li>"
			    + "<br/><br/>Thanks,"
			    + "<br/>iLids Admin.<br/><br/><br/>"
			    + "<div style=\"width:100%;border-top:1px solid black;font-size:13px;\">"
			    + "<br/>This is a system generated mail. Please do not reply to this email ID.</div></body></html>";
		}
		String exp = "<html><body>Hi Admin</body></html>";
		helper.setText(exp);
		helper.setSubject("- Exceptions tracked on " + sdf.format(new Date()));
		helper.setFrom("joby@cirakas.com");
		/* for (MailSms mailsms : mailSmsList) {
		 helper.setTo(mailsms.getMail());
		 logger.info("sending mail to" + mailsms.getMail());
		 mailSender.send(mimeMessage);
		 } */

		helper.setTo("jeena@cirakas.com");
		mailSender.send(mimeMessage);
	    }
	} catch (ParseException ex) {
	    ex.printStackTrace();
	} catch (Exception ex) {
	    ex.printStackTrace();
	}
	System.err.println("Reached at end statement");
	ServerConfig.latestAlertsScheduleCheckTime = currentDateTime;
    }

}
