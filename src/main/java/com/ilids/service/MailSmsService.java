/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ilids.service;

import com.ilids.dao.MailSmsRepository;
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
public class MailSmsService {
    
    @Autowired
    private MailSmsRepository mailSmsRepository;
    
   

    public List<MailSms> getAllMailSmsList() {
        return mailSmsRepository.getAll();
    }

    public MailSms findById(Long id) {
        return mailSmsRepository.findById(id);
    }

    public MailSms remove(Long id)throws Exception {
        MailSms mailSms = mailSmsRepository.findById(id);
        if (mailSms == null) {
            throw new IllegalArgumentException();
        }
        //note.getUser().getNotes().remove(note); //pre remove
        mailSmsRepository.delete(mailSms);
        return mailSms;
    }
    public boolean getAllMailData(String mailId) throws ParseException {
       List<Object[]> mailData=mailSmsRepository.getAllMailData(mailId); 
       boolean result=false;
       int size=mailData.size();
        if(!mailData.isEmpty()){
            result=true;
        }
        return result;
    }   
    public void saveMailSms(MailSms mailSms)throws Exception{
	mailSmsRepository.persist(mailSms);
    }
    
     public void updateMailSms(MailSms mailSms)throws Exception{
	mailSmsRepository.merge(mailSms);
        }

    }
