/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ilids.dao;

import com.ilids.domain.MailSms;
import org.springframework.stereotype.Component;

/**
 *
 * @author cirakas
 */
@Component
public class MailSmsRepository extends AbstractGenericDao<MailSms> {
      public MailSmsRepository() {
        super(MailSms.class);
    }

}
