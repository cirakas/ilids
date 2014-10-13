/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ilids.IService;

import com.ilids.domain.DeviceZone;
import java.util.List;

/**
 *
 * @author cirakas
 */
public interface DeviceZoneService {

    /**
     *
     * @return
     */
    public List<DeviceZone> getAllDeviceZone();

    /**
     *
     * @param id
     * @return
     */
    public DeviceZone findById(Long id);

    /**
     *
     * @param deviceZone
     * @return
     * @throws Exception
     */
    public boolean addDeviceZone(DeviceZone deviceZone) throws Exception;

    /**
     *
     * @param deviceZone
     * @return
     * @throws Exception
     */
    public boolean updateDeviceZone(DeviceZone deviceZone) throws Exception;

    /**
     *
     * @param id
     * @return
     * @throws Exception
     */
    public DeviceZone remove(Long id) throws Exception;

}
