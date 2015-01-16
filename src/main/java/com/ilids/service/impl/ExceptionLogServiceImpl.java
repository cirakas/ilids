/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ilids.service.impl;

import com.ilids.IRepository.ExceptionLogRepository;
import com.ilids.IService.ExceptionLogService;
import com.ilids.domain.ExceptionLog;
import com.ilids.domain.User;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author user
 */
@Component
@Transactional
public class ExceptionLogServiceImpl implements ExceptionLogService {

    @Autowired
    ExceptionLogRepository exceptionLogRepository;

    @Override
    public void createLog(User loginUser, Exception exception, String module, String remark) {
        ExceptionLog exceptionLog = new ExceptionLog();

        exceptionLog.setExceptionRemark(remark);
        exceptionLog.setIssueCausedModule(module);
        exceptionLog.setOccuringTime(new Date());
        exceptionLog.setUserName(loginUser.getUsername());
        exceptionLog.setExceptionType(exception.getClass().toString());
        exceptionLogRepository.persist(exceptionLog);
        exceptionLogRepository.close();
    }

    @Override
    public List<ExceptionLog> getAllLatestException() throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
        List<Object[]> valueList = exceptionLogRepository.getAllLatestException();
        List<ExceptionLog> logList = new ArrayList<ExceptionLog>();
        for (Object[] object : valueList) {
            System.err.println("Inside For loop");
            ExceptionLog log = new ExceptionLog();
            log.setId(Long.parseLong(object[0].toString()));
            log.setExceptionRemark(object[1].toString());
            log.setExceptionType(object[2].toString());
            log.setIssueCausedModule(object[3].toString());
            log.setOccuringTime(sdf.parse(object[4].toString()));
            log.setUserName(object[1].toString());

            logList.add(log);
        }
        exceptionLogRepository.close();
        return logList;
    }

}