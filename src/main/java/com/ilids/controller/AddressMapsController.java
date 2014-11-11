/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ilids.controller;

import com.ilids.IService.AddressMapsService;
import com.ilids.IService.ExceptionLogService;
import com.ilids.domain.AddressMaps;
import com.ilids.domain.User;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;
import javax.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 *
 * @author cirakas
 */
@Controller
public class AddressMapsController {
    
    @Autowired
    public AddressMapsService addressMapsService;
    @Resource(name = "sessionRegistry")
    private SessionRegistryImpl sessionRegistry;
    @Autowired
    ExceptionLogService exceptionLogService;
    
    
    
    String module="";
    private Long currentUserId;
    
    @ModelAttribute("addressmapsModel")
    public AddressMaps getAddressMaps(){
        return new AddressMaps();
    }
    
    @ModelAttribute("addressMapsList")
    public List<AddressMaps>getAddressMapsList(){
        return addressMapsService.getAllAddressMaps();
    }
    
    
     @RequestMapping(value = "addressmap", method = RequestMethod.GET)
    public String show() {
        getModuleName();
        return "/addressmaps/addressmaps";
    }

    public void getModuleName() {
                Properties prop = new Properties();
                String propFileName = "messages_en.properties";
                InputStream inputStream = getClass().getClassLoader().getResourceAsStream(propFileName);
                try {
                    prop.load(inputStream);
                    module = prop.getProperty("label.deviceManagement");
                } catch (IOException ex1) {

                }  
    }
    
    @RequestMapping(value = "/saveAddressMaps",method = RequestMethod.POST)
    public String addAddressMaps(AddressMaps addressMaps, RedirectAttributes flash){
        try{
           addressMaps = addressMapsService.addAddressMaps(addressMaps); 
        }
        catch(Exception ex){
            exceptionLogService.createLog((User) sessionRegistry.getSessionInformation("loginUser").getPrincipal(), ex, module,
                                              "Address Map creation failed [ Controller : ' "+this.getClass().toString()+" '@@ Method Name : 'addAddressMaps' ]");
        }
        return "redirect:/addressmap";
    }
    
    /**
     *
     * @param addressMapsId
     * @return
     */
    @RequestMapping(value = "/deleteAddresssMaps",method = RequestMethod.POST)
    public String delete(@RequestParam ("addressMapsId") Long addressMapsId){
        try{
            addressMapsService.remove(addressMapsId);
        }
        catch(Exception ex){
            exceptionLogService.createLog((User) sessionRegistry.getSessionInformation("loginUser").getPrincipal(), ex, module,
                                              "Address Map details deletion failed [ Controller : ' "+this.getClass().toString()+" '@@ Method Name : 'delete' ]");
        }
        return "redirect:/addressmap";
}
    
    @RequestMapping(value = "/EditAddressMap",method = RequestMethod.POST)
    @ResponseBody
    public AddressMaps EditAddressMap(@RequestParam("id") String id){
        AddressMaps addressMaps = new AddressMaps();
        if (id!= null)
            currentUserId = Long.valueOf(id);
            addressMaps = addressMapsService.findById(currentUserId); 
        return addressMaps;
    }
    
    @RequestMapping(value = "/saveAddressMaps/{id}",method = RequestMethod.POST)
    public String updateAddressMaps(AddressMaps addressMaps, RedirectAttributes flash,@PathVariable("id") String id){
        Long addressMapId = Long.valueOf(id);
        addressMaps.setId(addressMapId);
        try{
            addressMaps=addressMapsService.updateAddressMaps(addressMaps);
        }
        catch(Exception ex){}
        return "redirect:/addressmap";
    }
        
    
}
