/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ilids.service.impl;

import com.ilids.IRepository.MailSmsRepository;
import com.ilids.IService.MailSmsService;
import com.ilids.domain.MailSms;
import java.text.ParseException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author cirakas
 */
@Component
@Transactional
public class MailSmsServiceImpl implements MailSmsService{
    
    @Autowired
    private MailSmsRepository mailSmsRepository;
    
   

    @Override
    public List<MailSms> getAllMailSmsList() {
        List<MailSms> allMailSms = mailSmsRepository.getAll();
        mailSmsRepository.close();
        return allMailSms;
    }

    @Override
    public MailSms findById(Long id) {
        MailSms mailSmsId = mailSmsRepository.findById(id);
        mailSmsRepository.close();
        return mailSmsId;
    }

    @Override
    public MailSms remove(Long id)throws Exception {
        MailSms mailSms = mailSmsRepository.findById(id);
        if (mailSms == null) {
            throw new IllegalArgumentException();
        }
        //note.getUser().getNotes().remove(note); //pre remove
        mailSmsRepository.delete(mailSms);
        mailSmsRepository.close();
        return mailSms;
    }
    @Override
    public boolean getAllMailData(String mailId,long id) throws ParseException {
       List<Object[]> mailData=mailSmsRepository.getAllMailData(mailId,id); 
       boolean result=true;
        if(!mailData.isEmpty()){
            result = false;
        }
        mailSmsRepository.close();
        return result;
    }   
    @Override
    public void saveMailSms(MailSms mailSms)throws Exception{
	mailSmsRepository.persist(mailSms);
        mailSmsRepository.close();
    }
    
    @Override
     public void updateMailSms(MailSms mailSms)throws Exception{
	mailSmsRepository.merge(mailSms);
        mailSmsRepository.close();
        }

    }
