/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ilids.IRepository;

import com.ilids.domain.ExceptionLog;
import java.util.List;

/**
 *
 * @author cirakas
 */
public interface ExceptionLogRepository extends GenericRepository<ExceptionLog>{

    /**
     *
     * @return
     */
    public List<Object[]> getAllLatestException();
}
