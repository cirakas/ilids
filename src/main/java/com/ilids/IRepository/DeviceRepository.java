/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ilids.IRepository;

import com.ilids.domain.Devices;
import java.util.List;

/**
 *
 * @author cirakas
 */
public interface DeviceRepository extends GenericRepository<Devices>{
    
    public List<Devices> getAllUsedDevices();
    
}
