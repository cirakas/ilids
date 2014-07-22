package com.ilids.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.ilids.domain.Data;
import com.ilids.service.DataService;
import org.json.JSONArray;


@Controller
public class DataController {

   
    @Autowired
    private DataService dataService;

//    @ModelAttribute("users")
//    public List<User> getUsers() {
//        return userService.getAllUsersExceptAdmin();
//    }
//    
//    @ModelAttribute("users")
//    public Users getUser() {
//        return new Users();
//    }

    @ModelAttribute("DataModel")
    public Data Data() {
        return new Data();
    }
    
     @ModelAttribute("DataList")
    public JSONArray getDataList() {
        JSONArray dataList= dataService.getAllData();
        System.out.println("datalist"+dataList);
       
        return dataList;
    }
    
    @RequestMapping(value = "data", method = RequestMethod.GET)
    public String show() {
        System.out.println("Inside the show");
        return "/home";
    }
    
//    @RequestMapping(value = "saveData", method = RequestMethod.POST)
//    public String add(Data data, RedirectAttributes flash) {
//        if (dataService.addData(data)) {
//            flash.addFlashAttribute("success", "System settings has been successfully added.");
//        } else {
//            flash.addFlashAttribute("error", "Could not add System settings.");
//        }
//        return "redirect:/home";
//    }

//    @RequestMapping(value = "/devices/delete", method = RequestMethod.POST)
//    public String delete(@RequestParam("deviceid") Long deviceId) {
//        systemSettingsService.remove(deviceId);
//        return "redirect:/device/add";
//    }

}
