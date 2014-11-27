/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ilids.service.impl;

import com.ilids.IRepository.DataRepository;
import com.ilids.IService.PowerFactorSchedulerService;
import com.ilids.conf.ServerConfig;
import com.ilids.repository.impl.DataRepositoryImpl;
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
public class PowerFactorSchedulerServiceImpl implements PowerFactorSchedulerService{

    private static final Logger logger = LoggerFactory.getLogger(PowerFactorSchedulerServiceImpl.class);

    @Autowired
    private DataRepository dataRepository;

    @Autowired
    private MailSender mailSender;

    @Override
    public void checkAndUpdatePowerFactor() {
        logger.info("Scheduler started to run");
        Data powerFactorData = dataRepository.getLatestDataList(30);
        if (powerFactorData != null) {
            ServerConfig.pollData.phase1Value = powerFactorData.getData();
            powerFactorData = dataRepository.getLatestDataList(32);
            ServerConfig.pollData.phase2Value = powerFactorData.getData();
            powerFactorData = dataRepository.getLatestDataList(34);
            ServerConfig.pollData.phase3Value = powerFactorData.getData();
        }
            dataRepository.close();
       
		//mailSender.send(message);

    }
}
