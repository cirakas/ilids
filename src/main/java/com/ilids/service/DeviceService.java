package com.ilids.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import com.ilids.dao.DeviceRepository;
import com.ilids.domain.DeviceZone;
import com.ilids.domain.Devices;
import java.util.Date;

@Component
@Transactional
public class DeviceService {

    @Autowired
    private DeviceRepository deviceRepository;
    @Autowired
    private DeviceZoneService deviceZoneService;

    public List<Devices> getAllDevice() {
        return deviceRepository.getAll();
    }

    public Devices findById(Long id) {
        return deviceRepository.findById(id);
    }

    public Devices remove(Long id)throws Exception {
        Devices device = deviceRepository.findById(id);
        
        //  device.getUser().getDevices().remove(device); //pre remove
        deviceRepository.delete(device);
        return device;
    }

//    @PreAuthorize("hasRole('ROLE_ADMIN')")
//    public boolean addDeviceToUser(String devicename, String username) {
//        //Devices device = createDevice(devicename);
//        User user = userService.findByCustomField("username", username);
//        if (user == null) {
//            throw new IllegalArgumentException();
//        }
//      //  user.addDevice(device);
//        userService.persist(user);
//        return true;
//    }
    public boolean addDevice(Devices device)throws Exception {
        Long zoneId = Long.valueOf(device.getDeviceZoneId());
        DeviceZone devicezone = deviceZoneService.findById(zoneId);
        device.setDeviceZone(devicezone);
        device.setCreatedDate(new Date());
        deviceRepository.persist(device);
        return true;
    }

    public boolean updateDevice(Devices device)throws Exception {
        Long zoneId = Long.valueOf(device.getDeviceZoneId());
        DeviceZone devicezone = deviceZoneService.findById(zoneId);
        device.setDeviceZone(devicezone);
        device.setCreatedDate(new Date());
        deviceRepository.merge(device);
        return true;
    }
}
