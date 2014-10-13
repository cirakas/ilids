package com.ilids.controller;

import com.ilids.IService.DeviceZoneService;
import com.ilids.IService.ExceptionLogService;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.ilids.domain.DeviceZone;
import com.ilids.domain.User;
import com.ilids.service.impl.DeviceZoneServiceImpl;
import com.ilids.service.impl.ExceptionLogServiceImpl;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author cirakas
 */
@Controller
public class DeviceZoneController {

    @Autowired
    private DeviceZoneService deviceZoneService;

    @Resource(name = "sessionRegistry")
    private SessionRegistryImpl sessionRegistry;
    @Autowired
    ExceptionLogService exceptionLogService;

    String module = "";

    Long currentUserId = 0l;

    /**
     *
     * @return
     */
    @ModelAttribute("deviceZoneModel")
    public DeviceZone getDeviceZone() {
	return new DeviceZone();
    }

    /**
     *
     * @return
     */
    @ModelAttribute("deviceZoneList")
    public List<DeviceZone> getDeviceZoneList() {
	return deviceZoneService.getAllDeviceZone();
    }

    /**
     *
     * @return
     */
    @RequestMapping(value = "devicezones", method = RequestMethod.GET)
    public String show() {
	getModuleName();
	return "/devicezone/devicezones";
    }

    /**
     *
     */
    public void getModuleName() {
	Properties prop = new Properties();
	String propFileName = "messages_en.properties";
	InputStream inputStream = getClass().getClassLoader().getResourceAsStream(propFileName);
	try {
	    prop.load(inputStream);
	    module = prop.getProperty("label.zoneManagement");
	} catch (IOException ex1) {

	}
    }
//Add Device Zone

    /**
     *
     * @param deviceZone
     * @param flash
     * @return
     */
    @RequestMapping(value = "saveDeviceZone", method = RequestMethod.POST)
    public String addDeviceZone(DeviceZone deviceZone, RedirectAttributes flash) {
	boolean status = false;
	try {
	    status = deviceZoneService.addDeviceZone(deviceZone);
	} catch (Exception ex) {
	    exceptionLogService.createLog((User) sessionRegistry.getSessionInformation("loginUser").getPrincipal(), ex, module,
		    "Device Zone creation is failed [ Controller : ' " + this.getClass().toString() + " '@@ Method Name : 'addDeviceZone' ]");
	}
	if (status) {
	    flash.addFlashAttribute("success", "Device Zone has been successfully added.");
	} else {
	    flash.addFlashAttribute("error", "Could not add device Zone.");
	}
	return "redirect:/devicezones";
    }

    /**
     *
     * @param deviceZone
     * @param flash
     * @return
     */
    @RequestMapping(value = "saveDeviceZone/{id}", method = RequestMethod.POST)
    public String updateDevice(DeviceZone deviceZone, RedirectAttributes flash) {
	boolean status = false;
	try {
	    status = deviceZoneService.updateDeviceZone(deviceZone);
	} catch (Exception ex) {
	    exceptionLogService.createLog((User) sessionRegistry.getSessionInformation("loginUser").getPrincipal(), ex, module,
		    "Device Zone updation is failed [ Controller : ' " + this.getClass().toString() + " '@@ Method Name : 'updateDevice' ]");
	}

	if (status) {
	    flash.addFlashAttribute("success", "Device zone has been successfully added.");
	} else {
	    flash.addFlashAttribute("error", "Could not add device zone.");
	}
	return "redirect:/devicezones";
    }

//Edit Device Zone

    /**
     *
     * @param id
     * @return
     */
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

    /**
     *
     * @param deviceZoneId
     * @return
     */
        @RequestMapping(value = "/deleteDeviceZone", method = RequestMethod.POST)
    public String delete(@RequestParam("deviceZoneId") Long deviceZoneId) {
	try {
	    deviceZoneService.remove(deviceZoneId);
	} catch (Exception ex) {
	    exceptionLogService.createLog((User) sessionRegistry.getSessionInformation("loginUser").getPrincipal(), ex, module,
		    "Device Zone deletion is failed [ Controller : ' " + this.getClass().toString() + " '@@ Method Name : 'delete' ]");
	}
	return "redirect:/devicezones";
    }

}
