/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ilids.IService;

import com.ilids.domain.SystemSettings;
import java.util.List;

/**
 *
 * @author cirakas
 */
public interface SystemSettingsService {

    /**
     *
     * @return
     */
    public List<SystemSettings> getAllSystemSettings();

    /**
     *
     * @param id
     * @return
     * @throws Exception
     */
    public SystemSettings findById(Long id) throws Exception;

    /**
     * 
     * @param id
     * @return
     * @throws Exception
     */
    public SystemSettings remove(Long id) throws Exception;

    /**
     *
     * @param systemSettings
     * @return
     * @throws Exception
     */
    public boolean addSystemSettings(SystemSettings systemSettings) throws Exception;

    /**
     *
     * @param systemSettings
     * @return
     * @throws Exception
     */
    public boolean updateSystemSettings(SystemSettings systemSettings) throws Exception;
}
