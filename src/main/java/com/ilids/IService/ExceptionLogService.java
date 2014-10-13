/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ilids.IService;

import com.ilids.domain.ExceptionLog;
import com.ilids.domain.User;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author cirakas
 */
public interface ExceptionLogService {
    
    /**
     *
     * @param loginUser
     * @param exception
     * @param module
     * @param remark
     */
    public void createLog(User loginUser, Exception exception,String module, String remark);
    
    /**
     *
     * @return
     * @throws Exception
     */
    public List<ExceptionLog> getAllLatestException()throws Exception;
}
