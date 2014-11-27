/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ilids.service.impl;

import com.ilids.IRepository.DataRepository;
import com.ilids.IRepository.SystemSettingsRepository;
import com.ilids.IService.AlertSchedulerService;
import com.ilids.IService.DeviceService;
import com.ilids.IService.MailSmsService;
import com.ilids.conf.ServerConfig;
import com.ilids.repository.impl.DataRepositoryImpl;
import com.ilids.repository.impl.SystemSettingsRepositoryImpl;
import com.ilids.domain.Alerts;
import com.ilids.domain.Devices;
import com.ilids.domain.MailSms;
import com.ilids.domain.SystemSettings;
import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
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
 * @author cirakas
 */
@Component
@Transactional
public class AlertSchedulerServiceImpl implements AlertSchedulerService{

    private static final Logger logger = LoggerFactory.getLogger(AlertSchedulerServiceImpl.class);

    @Autowired
    private DataRepository dataRepository;

    @Autowired
    private DeviceService deviceService;

    @Autowired
    private MailSmsService mailSmsService;

    @Autowired
    JavaMailSenderImpl mailSender = new JavaMailSenderImpl();

    @Autowired
    private SystemSettingsRepository systemSettingsRepository;
    

    
    SimpleDateFormat sdf=new SimpleDateFormat("dd-MMM-yyyy hh:ss:mm");

//    @Autowired
//    private MailSender mailSender;

    /**
     *
     * @throws MessagingException
     */
    @Override
        public void checkForAlerts() throws MessagingException {

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

        int i = 0;
        List<MailSms> mailSmsList = null;
        SystemSettings systemSettings = systemSettingsRepository.getAll().get(0);
        List<Object[]> alertDataList = dataRepository.getAllAlertDataSchedule(lastScheduleValue, currentDateValue, systemSettings.getMdv());

        logger.info("currentDateValue RUN Alert list::" + alertDataList.size());

        // let's include the infamous windows Sample file (this time copied to c:/)
        FileSystemResource res = new FileSystemResource(new File("D:\\logo.jpg"));
        if (alertDataList.size() > 0) {
            mailSmsList = mailSmsService.getAllMailSmsList();
        }

        for (Object[] alertDate : alertDataList) {
//	    if (i > 3) {//Remove this code to send the mail to more than three
//		break;
//	    }
            mimeMessage = mailSender.createMimeMessage();
            helper = new MimeMessageHelper(mimeMessage, true, "utf-8");
            helper.addInline("identifier1234", res);
            String htmlMsg = "<html><body>Hi, <br/><br/>iLids system has captured an alert on " + alertDate[2] + " for " + alertDate[5] + ". <br/><br/> The measured value is " + alertDate[1] + "."
                    + "<br/> The ideal value should be less than  " + systemSettings.getMdv() + "."
                    + "<br/><br/> Please follow up."
                    + "<br/><br/>Thanks,"
                    + "<br/>iLids Admin.<br/><br/><br/><div style=\"width:100%;border-top:1px solid black;font-size:13px;\"><br/>This is a system generated mail. Please do not reply to this email ID.</div></body></html>";
            ServerConfig.pollData.getAlertList().add(alertDate[2].toString());
            helper.setText(htmlMsg, true);
            helper.setSubject("-" + alertDate[5] + " at " + alertDate[2]);
            helper.setFrom("admin@ilids.com");
            for (MailSms mailsms : mailSmsList) {
//		if (i > 3) {//Remove this code to send the mail to more than three
//		    break;
//		}
                helper.setTo(mailsms.getMail());
                logger.info("sending mail to" + mailsms.getMail());
                mailSender.send(mimeMessage);
            }
            i++;
        }
        ServerConfig.latestAlertsScheduleCheckTime = currentDateTime;
        dataRepository.close();
        systemSettingsRepository.close();
    }

    /**
     *
     * @throws MessagingException
     */
    @Override
    public void dbUpdateCheck() throws MessagingException {
        List<Devices> deviceList = deviceService.getAllDevice();
        List<MailSms> mailSmsList = null;
        List<Object[]> currentDataList = null;
        MimeMessage mimeMessage = null;
        MimeMessageHelper helper = null;
        String currentTime = "";
        String beforeTime = "";
        String device = "";

        for (Devices devices : deviceList) {
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Calendar cal = Calendar.getInstance();
            Date dateTime = cal.getTime();
            currentTime = dateFormat.format(dateTime);
            cal.add(Calendar.MINUTE, -2);
            Date twoMinuteBack = cal.getTime();
            beforeTime = dateFormat.format(twoMinuteBack);
            currentDataList = dataRepository.getAllDataAtTheMoment(currentTime, beforeTime, devices.getSlaveId());

            if (currentDataList.isEmpty()) {
                if (device.isEmpty()) {
                    device = device + devices.getName();
                } else {
                    device = device + "," + devices.getName();
                }
            }
        }

        FileSystemResource res = new FileSystemResource(new File("D:\\logo.jpg"));

        if (!device.isEmpty()) {
            
            logger.info("There is a breakage in the connection. Sending mail to the mail");
            
            mailSmsList = mailSmsService.getAllMailSmsList();
            mimeMessage = mailSender.createMimeMessage();
            helper = new MimeMessageHelper(mimeMessage, true, "utf-8");
            helper.addInline("identifier1234", res);
            String htmlMsg = "<html><body>Hi, <br/><br/>Data fetching from " + device + " has stopped unexpectedly on " + beforeTime + ". <br/><br/>  ."
                    + "<br/><br/> Please follow up."
                    + "<br/><br/>Thanks,"
                    + "<br/>iLids Admin.<br/><br/><br/><div style=\"width:100%;border-top:1px solid black;font-size:13px;\"><br/>This is a system generated mail. Please do not reply to this email ID.</div></body></html>";
            helper.setText(htmlMsg, true);
            helper.setSubject("Connection Lost");
            helper.setFrom("cirakas@cirakas.com");

            for (MailSms mailsms : mailSmsList) {
                helper.setTo(mailsms.getMail());
                mailSender.send(mimeMessage);
            }
        }
            dataRepository.close();
    }
    
}
