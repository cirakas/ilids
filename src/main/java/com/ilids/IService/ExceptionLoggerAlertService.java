/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ilids.IService;

import javax.mail.MessagingException;

/**
 *
 * @author cirakas
 */
public interface ExceptionLoggerAlertService {

    /**
     *
     * @throws MessagingException
     */
    public void sendDailyAlertForException() throws MessagingException;
}