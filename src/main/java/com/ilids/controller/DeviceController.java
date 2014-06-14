package com.ilids.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.ilids.domain.Devices;
import com.ilids.domain.User;
import com.ilids.service.DeviceService;


@Controller
public class DeviceController {

   
    @Autowired
    private DeviceService deviceService;

//    @ModelAttribute("users")
//    public List<User> getUsers() {
//        return userService.getAllUsersExceptAdmin();
//    }
//    
//    @ModelAttribute("users")
//    public Users getUser() {
//        return new Users();
//    }

    @ModelAttribute("deviceModel")
    public Devices getDevices() {
        return new Devices();
    }
    
     @ModelAttribute("deviceList")
    public List<Devices> getDeviceList() {
        return deviceService.getAllDevice();
    }
    
    @RequestMapping(value = "devices", method = RequestMethod.GET)
    public String show() {
        System.out.println("Inside the show");
        return "/device/devices";
    }

    @RequestMapping(value = "saveDevice", method = RequestMethod.POST)
    public String add(Devices device, RedirectAttributes flash) {
        if (deviceService.addDevice(device)) {
            flash.addFlashAttribute("success", "Device has been successfully added.");
        } else {
            flash.addFlashAttribute("error", "Could not add device.");
        }
        return "redirect:/devices";
    }

    @RequestMapping(value = "/deleteDevice", method = RequestMethod.POST)
    public String delete(@RequestParam("deviceId") Long deviceId) {
        deviceService.remove(deviceId);
        return "redirect:/devices";
    }

}
