/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ilids.IService;

import com.ilids.domain.Devices;
import java.util.List;

/**
 *
 * @author cirakas
 */
public interface DeviceService {

    /**
     *
     * @return
     */
    public List<Devices> getAllDevice();
    public List<Devices> getAllDevice(String col,String order);
    /**
     *
     * @param id
     * @return
     */
    public Devices findById(Long id);

    /**
     *
     * @param id
     * @return
     * @throws Exception
     */
    public Devices remove(Long id) throws Exception;

    /**
     *
     * @param device
     * @return
     * @throws Exception
     */
    public boolean addDevice(Devices device) throws Exception;

    /**
     *
     * @param device
     * @return
     * @throws Exception
     */
    public boolean updateDevice(Devices device) throws Exception;
}
