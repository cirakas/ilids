package com.ilids.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.ilids.dao.DeviceZoneRepository;
import com.ilids.domain.DeviceZone;

@Component
@Transactional
public class DeviceZoneService {

   
    @Autowired
    private DeviceZoneRepository deviceZoneRepository;

    public List<DeviceZone> getAllDeviceZone() {
        return deviceZoneRepository.getAll();
    }

    public DeviceZone findById(Long id) {
        return deviceZoneRepository.findById(id);
    }

    public boolean addDeviceZone(DeviceZone deviceZone)throws Exception {
        deviceZoneRepository.persist(deviceZone);
        return true;
    }

    public boolean updateDeviceZone(DeviceZone deviceZone)throws Exception {
        deviceZoneRepository.merge(deviceZone);
        return true;
    }

    public DeviceZone remove(Long id) throws Exception{
        DeviceZone deviceZone = deviceZoneRepository.findById(id);
        if (deviceZone == null) {
            throw new IllegalArgumentException();
        }
        deviceZoneRepository.delete(deviceZone);
        return deviceZone;
    }

}
