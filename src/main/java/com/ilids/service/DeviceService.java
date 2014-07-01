package com.ilids.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.ilids.dao.DeviceRepository;
import com.ilids.domain.Devices;
import com.ilids.domain.User;
import java.util.Date;
import javax.annotation.Resource;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.session.SessionRegistryImpl;


@Component
@Transactional
public class DeviceService {

    @Autowired
    private UserService userService;
    @Autowired
    private DeviceRepository deviceRepository;

    public List<Devices> getAllDevice() {
        return deviceRepository.getAll();
    }

    public Devices findById(Long id) {
        return deviceRepository.findById(id);
    }

    public Devices remove(Long id) {
        Devices device = deviceRepository.findById(id);
        if (device == null) {
            throw new IllegalArgumentException();
        }
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
    
     public boolean addDevice(Devices device){
        device.setCreatedDate(new Date());
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        String userName=auth.getName();
//        User user=userService.findByCustomField("username", userName);
//        device.setUser(user);
        deviceRepository.persist(device);
        return true;
    }
    

//    private Devices createDevice(String name) {
//        return new Devices(name);
//    }

}
