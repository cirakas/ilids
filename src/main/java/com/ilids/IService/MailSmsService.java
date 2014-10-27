/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ilids.IService;

import com.ilids.domain.MailSms;
import java.text.ParseException;
import java.util.List;

/**
 *
 * @author cirakas
 */
public interface MailSmsService {

    /**
     *
     * @return
     */
    public List<MailSms> getAllMailSmsList();

    /**
     *
     * @param id
     * @return
     */
    public MailSms findById(Long id);

    /**
     *
     * @param id
     * @return
     * @throws Exception
     */
    public MailSms remove(Long id) throws Exception;

    /**
     *
     * @param mailId
     * @return
     * @throws ParseException
     */
    public boolean getAllMailData(String mailId,long id) throws ParseException;

    /**
     *
     * @param mailSms
     * @throws Exception
     */
    public void saveMailSms(MailSms mailSms) throws Exception;

    /**
     *
     * @param mailSms
     * @throws Exception
     */
    public void updateMailSms(MailSms mailSms) throws Exception;
}
