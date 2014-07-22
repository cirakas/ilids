/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ilids.service;

import com.ilids.conf.ServerConfig;
import com.ilids.dao.DataRepository;
import com.ilids.domain.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;

/**
 *
 * @author cirakas
 */
public class PowerFactorSchedulerService {
    
    private static final Logger logger = LoggerFactory.getLogger(PowerFactorSchedulerService.class);
    
     @Autowired
    private DataRepository dataRepository;
     
    @Autowired
    private MailSender mailSender;
     
    public void checkAndUpdatePowerFactor()
    {
	    logger.info("Scheduler started to run");
	    Data powerFactorData=dataRepository.getLatestDataList(30);
	    ServerConfig.pollData.phase1Value=powerFactorData.getData();
	    powerFactorData=dataRepository.getLatestDataList(32);
	    ServerConfig.pollData.phase2Value=powerFactorData.getData();
	    powerFactorData=dataRepository.getLatestDataList(34);
	    ServerConfig.pollData.phase3Value=powerFactorData.getData();
	    logger.info("Scheduler Finished");
		SimpleMailMessage message = new SimpleMailMessage();
		message.setTo("shiny@cirakas.com");
		message.setSubject("Hello subject");
		message.setText("Hello welcome");
		message.setFrom("joby@cirakas.com");
		//mailSender.send(message);
	    
    }
}
