package com.ilids.service.impl;

import com.ilids.IRepository.DeviceZoneRepository;
import com.ilids.IService.DeviceZoneService;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.ilids.domain.DeviceZone;

/**
 *
 * @author cirakas
 */
@Component
@Transactional
public class DeviceZoneServiceImpl implements DeviceZoneService{

   
    @Autowired
    private DeviceZoneRepository deviceZoneRepository;

    @Override
    public List<DeviceZone> getAllDeviceZone() {
        List<DeviceZone> allZone = deviceZoneRepository.getAll();
        deviceZoneRepository.close();
        return allZone;
    }

    @Override
    public DeviceZone findById(Long id) {
        DeviceZone zoneId = deviceZoneRepository.findById(id);
        deviceZoneRepository.close();
        return zoneId;
    }

    @Override
    public boolean addDeviceZone(DeviceZone deviceZone)throws Exception {
        deviceZoneRepository.persist(deviceZone);
        deviceZoneRepository.close();
        return true;
    }

    @Override
    public boolean updateDeviceZone(DeviceZone deviceZone)throws Exception {
        deviceZoneRepository.merge(deviceZone);
        deviceZoneRepository.close();
        return true;
    }

    @Override
    public DeviceZone remove(Long id) throws Exception{
        DeviceZone deviceZone = deviceZoneRepository.findById(id);
        if (deviceZone == null) {
            throw new IllegalArgumentException();
        }
        deviceZoneRepository.delete(deviceZone);
        deviceZoneRepository.close();
        return deviceZone;
    }

}
