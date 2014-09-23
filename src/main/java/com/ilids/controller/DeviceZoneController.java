package com.ilids.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.ilids.domain.DeviceZone;
import com.ilids.service.DeviceZoneService;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class DeviceZoneController {

    @Autowired
    private DeviceZoneService deviceZoneService;

    @ModelAttribute("deviceZoneModel")
    public DeviceZone getDeviceZone() {
        return new DeviceZone();
    }

    @ModelAttribute("deviceZoneList")
    public List<DeviceZone> getDeviceZoneList() {
        return deviceZoneService.getAllDeviceZone();
    }

    Long currentUserId = 0l;

    @RequestMapping(value = "devicezones", method = RequestMethod.GET)
    public String show() {
        return "/devicezone/devicezones";
    }
    
//Add Device Zone

    @RequestMapping(value = "saveDeviceZone", method = RequestMethod.POST)
    public String addDeviceZone(DeviceZone deviceZone, RedirectAttributes flash) {
        if (deviceZoneService.addDeviceZone(deviceZone)) {
            flash.addFlashAttribute("success", "Device Zone has been successfully added.");
        } else {
            flash.addFlashAttribute("error", "Could not add device Zone.");
        }
        return "redirect:/devicezones";
    }


    @RequestMapping(value = "saveDeviceZone/{id}", method = RequestMethod.POST)
    public String updateDevice(DeviceZone deviceZone, RedirectAttributes flash) {
        if (deviceZoneService.updateDeviceZone(deviceZone)) {
            flash.addFlashAttribute("success", "Device zone has been successfully added.");
        } else {
            flash.addFlashAttribute("error", "Could not add device zone.");
        }
        return "redirect:/devicezones";
    }

//Edit Device Zone

    @RequestMapping(value = "/editDeviceZone", method = RequestMethod.POST)
    @ResponseBody
    public DeviceZone editDeviceZone(@RequestParam("id") String id) {
        DeviceZone devicezones = new DeviceZone();

        if (id != null) {
            currentUserId = Long.valueOf(id);
        }
        devicezones = deviceZoneService.findById(currentUserId);
        return devicezones;
    }
    
//Delete Device Zone

    @RequestMapping(value = "/deleteDeviceZone", method = RequestMethod.POST)
    public String delete(@RequestParam("deviceZoneId") Long deviceZoneId) {
        deviceZoneService.remove(deviceZoneId);
        return "redirect:/devicezones";
    }

}
