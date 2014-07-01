package com.ilids.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.ilids.dao.SystemSettingsRepository;

import com.ilids.domain.SystemSettings;
import com.ilids.domain.User;
import java.util.Date;
import javax.annotation.Resource;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.session.SessionRegistryImpl;


@Component
@Transactional
public class SystemSettingsService {

  
    @Autowired
    private SystemSettingsRepository systemSettingsRepository;

    public List<SystemSettings> getAllSystemSettings() {
        
        List<SystemSettings> list=systemSettingsRepository.getAll();
        
        for(SystemSettings sys:list){
            System.out.println("Id---------"+sys.getMdv());
        }
        
        return list;
    }

    public SystemSettings findById(Long id) {
        return systemSettingsRepository.findById(id);
    }

    public SystemSettings remove(Long id) {
        SystemSettings systemSettings = systemSettingsRepository.findById(id);
        if (systemSettings == null) {
            throw new IllegalArgumentException();
        }
      //  device.getUser().getDevices().remove(device); //pre remove
        systemSettingsRepository.delete(systemSettings);
        return systemSettings;
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
    
     public boolean addSystemSettings(SystemSettings systemSettings){
       
        systemSettingsRepository.persist(systemSettings);
        return true;
    }
    

//    private Devices createDevice(String name) {
//        return new Devices(name);
//    }

}
