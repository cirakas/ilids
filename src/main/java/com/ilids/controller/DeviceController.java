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
import com.ilids.service.DeviceService;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class DeviceController {

    @Autowired
    private DeviceService deviceService;

    @ModelAttribute("deviceModel")
    public Devices getDevices() {
        return new Devices();
    }

    @ModelAttribute("deviceList")
    public List<Devices> getDeviceList() {
        return deviceService.getAllDevice();
    }

    Long currentUserId = 0l;

    @RequestMapping(value = "devices", method = RequestMethod.GET)
    public String show() {
        return "/device/devices";
    }

    @RequestMapping(value = "saveDevice", method = RequestMethod.POST)
    public String addDevice(Devices device, RedirectAttributes flash) {
        if (deviceService.addDevice(device)) {
            flash.addFlashAttribute("success", "Device has been successfully added.");
        } else {
            flash.addFlashAttribute("error", "Could not add device.");
        }
        return "redirect:/devices";
    }

    @RequestMapping(value = "saveDevice/{id}", method = RequestMethod.POST)
    public String updateDevice(Devices device, RedirectAttributes flash) {
        if (deviceService.updateDevice(device)) {
            flash.addFlashAttribute("success", "Device has been successfully added.");
        } else {
            flash.addFlashAttribute("error", "Could not add device.");
        }
        return "redirect:/devices";
    }

    @RequestMapping(value = "/editDevices", method = RequestMethod.POST)
    @ResponseBody
    public Devices editDevices(@RequestParam("id") String id) {
        Devices devices = new Devices();

        if (id != null) {
            currentUserId = Long.valueOf(id);
        }
        devices = deviceService.findById(currentUserId);
        return devices;
    }

    @RequestMapping(value = "/deleteDevice", method = RequestMethod.POST)
    public String delete(@RequestParam("deviceId") Long deviceId) {
        deviceService.remove(deviceId);
        return "redirect:/devices";
    }

}
