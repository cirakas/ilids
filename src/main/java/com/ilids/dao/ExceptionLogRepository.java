/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ilids.dao;

import com.ilids.domain.ExceptionLog;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import org.springframework.stereotype.Component;


@Component
public class ExceptionLogRepository extends AbstractGenericDao<ExceptionLog>{

    public ExceptionLogRepository() {
        super(ExceptionLog.class);
    }
    
    public List<Object[]> getAllLatestException(){
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        Date today=new Date();
        final Calendar calendar = Calendar.getInstance();
        calendar.setTime(today);
        calendar.add(Calendar.DAY_OF_YEAR, 1);
        Date nextDay=calendar.getTime();
        String query="select * from exception_log where issue_occured_time BETWEEN '"+sdf.format(today)+"' and '"+sdf.format(nextDay)+" '";
        return (List<Object[]>) entityManager.createNativeQuery(query).getResultList();
    }
}
