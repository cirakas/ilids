/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ilids.service;

import com.ilids.dao.MailSmsRepository;
import com.ilids.domain.MailSms;
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

    public MailSms remove(Long id) {
        MailSms mailSms = mailSmsRepository.findById(id);
        if (mailSms == null) {
            throw new IllegalArgumentException();
        }
        //note.getUser().getNotes().remove(note); //pre remove
        mailSmsRepository.delete(mailSms);
        return mailSms;
    }
    
    public void saveMailSms(MailSms mailSms){
	mailSmsRepository.persist(mailSms);
    }
    
     public void updateMailSms(MailSms mailSms){
	mailSmsRepository.merge(mailSms);
    }

}
