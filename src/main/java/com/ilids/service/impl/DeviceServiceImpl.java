package com.ilids.service.impl;

import com.ilids.IRepository.DeviceRepository;
import com.ilids.IService.DeviceService;
import com.ilids.IService.DeviceZoneService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import com.ilids.domain.DeviceZone;
import com.ilids.domain.Devices;
import java.util.Date;

/**
 *
 * @author cirakas
 */
@Component
@Transactional
public class DeviceServiceImpl implements DeviceService{

    @Autowired
    DeviceRepository deviceRepository;
    @Autowired
    private DeviceZoneService deviceZoneService;

    @Override
    public List<Devices> getAllDevice() {
	return deviceRepository.getAll();
    }

    @Override
    public Devices findById(Long id) {
	return deviceRepository.findById(id);
    }

    @Override
    public Devices remove(Long id) throws Exception {
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
    @Override
    public boolean addDevice(Devices device) throws Exception {
	Long zoneId = Long.valueOf(device.getDeviceZoneId());
	DeviceZone devicezone = deviceZoneService.findById(zoneId);
	device.setDeviceZone(devicezone);
	device.setCreatedDate(new Date());
	deviceRepository.persist(device);
	return true;
    }

    @Override
    public boolean updateDevice(Devices device) throws Exception {
	Long zoneId = Long.valueOf(device.getDeviceZoneId());
	DeviceZone devicezone = deviceZoneService.findById(zoneId);
	device.setDeviceZone(devicezone);
	device.setCreatedDate(new Date());
	deviceRepository.merge(device);
	return true;
    }
}
