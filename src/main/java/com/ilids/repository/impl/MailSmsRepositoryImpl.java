/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ilids.repository.impl;

import com.ilids.IRepository.MailSmsRepository;
import com.ilids.domain.MailSms;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 *
 * @author cirakas
 */
@Component
public class MailSmsRepositoryImpl extends GenericRepositoryImpl<MailSms> implements MailSmsRepository{
    
    public List<Object[]> getAllMailData(String mailId,long id) {
       // Object[] getMailAdd = null;
        //try{
        String selectMailIdQuery = "select * from mail_sms where mail= '"+mailId+"' and id!='"+id+"'";
        return (List<Object[]>) entityManager.createNativeQuery(selectMailIdQuery).getResultList();
    }

    /**
     *
     */
    public MailSmsRepositoryImpl() {
        super(MailSms.class);
    }

}
