package com.ilids.controller;

import com.ilids.IService.DeviceService;
import com.ilids.IService.DeviceZoneService;
import com.ilids.IService.ExceptionLogService;
import com.ilids.domain.DeviceZone;
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
import com.ilids.service.impl.DeviceServiceImpl;
import com.ilids.service.impl.DeviceZoneServiceImpl;
import com.ilids.service.impl.ExceptionLogServiceImpl;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import javax.annotation.Resource;
import javax.validation.Valid;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *
 * @author cirakas
 */
@Controller
public class DeviceController {

    @Autowired
    private DeviceService deviceService;
    
     @Resource(name = "sessionRegistry")
    private SessionRegistryImpl sessionRegistry;

    @Autowired
    private DeviceZoneService deviceZoneService;
    
    @Autowired
    private ExceptionLogService exceptionLogService;
    

    String module="";

    /**
     *
     * @return
     */
    @ModelAttribute("deviceModel")
    public Devices getDevices() {
        return new Devices();
    }

    /**
     *
     * @return
     */
    @ModelAttribute("deviceList")
    public List<Devices> getDeviceList() {
        
        /*if(sessionRegistry.getSessionInformation("loginUser")!=null)
        System.out.println("@@@@@@@@@@@@@@ ** ^^^ "+((User)sessionRegistry.getSessionInformation("loginUser").getPrincipal()).getName());*/
        
        return deviceService.getAllDevice();
    }

    //list of zones

    /**
     *
     * @return
     */
        @ModelAttribute("deviceZones")
    public List<DeviceZone> getDeviceZone() {
        return deviceZoneService.getAllDeviceZone();
    }

    Long currentUserId = 0l;

    /**
     *
     * @return
     */
    @RequestMapping(value = "devices", method = RequestMethod.GET)
    public String show() {
        getModuleName();
        return "/device/devices";
    }
    
    /**
     *
     */
    public void getModuleName(){
                Properties prop = new Properties();
                String propFileName = "messages_en.properties";
                InputStream inputStream = getClass().getClassLoader().getResourceAsStream(propFileName);
                try {
                    prop.load(inputStream);
                    module = prop.getProperty("label.deviceManagement");
                } catch (IOException ex1) {

                }
    }

//Add device

    /**
     *
     * @param device
     * @param errors
     * @param model
     * @param flash
     * @return
     */
        @RequestMapping(value = "saveDevice", method = RequestMethod.POST)
    public String addDevice(@Valid Devices device, BindingResult errors, Model model, RedirectAttributes flash) {
        try{
           //if (errors.hasErrors()) {
               // return "/device/devices";
           // } else {

                boolean status = false;
                status = deviceService.addDevice(device);
                if (status) {
                    flash.addFlashAttribute("success", "Device has been successfully added.");
                } else {
                    flash.addFlashAttribute("error", "Could not add device.");
                }

                return "redirect:/devices";
           // }
            }catch(Exception ex){
                 exceptionLogService.createLog((User) sessionRegistry.getSessionInformation("loginUser").getPrincipal(), ex, module,
                                              "Device creation failed [ Controller : ' "+this.getClass().toString()+" '@@ Method Name : 'addDevice' ]");
            }
        return "/device/devices";
    }

//Update device

    /**
     *
     * @param device
     * @param flash
     * @return
     */
        @RequestMapping(value = "saveDevice/{id}", method = RequestMethod.POST)
    public String updateDevice(Devices device, RedirectAttributes flash) {
        try{
            boolean status=false;
        status=deviceService.updateDevice(device);
        if (status) {
            flash.addFlashAttribute("success", "Device has been successfully added.");
        } else {
            flash.addFlashAttribute("error", "Could not add device.");
        }
        }catch(Exception ex){
             exceptionLogService.createLog((User) sessionRegistry.getSessionInformation("loginUser").getPrincipal(), ex, module,
                                              "Device details updation failed [ Controller : ' "+this.getClass().toString()+" '@@ Method Name : 'updateDevice' ]");
        }
        return "redirect:/devices";
    }

//Edit device

    /**
     *
     * @param id
     * @return
     */
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

//Delete device

    /**
     *
     * @param deviceId
     * @return
     */
        @RequestMapping(value = "/deleteDevice", method = RequestMethod.POST)
    public String delete(@RequestParam("deviceId") Long deviceId) {
        try {
            deviceService.remove(deviceId);
            
        } catch (Exception ex) {
           exceptionLogService.createLog((User) sessionRegistry.getSessionInformation("loginUser").getPrincipal(), ex, module,
                                              "Device details deletion failed [ Controller : ' "+this.getClass().toString()+" '@@ Method Name : 'delete' ]");
        }
        return "redirect:/devices";
    }

}
