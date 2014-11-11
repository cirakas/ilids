/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ilids.controller;

import com.ilids.domain.ParameterCategory;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 *
 * @author cirakas
 */
@Controller
public class ParameterCategoryController {
    
    String module = "";
    
    @ModelAttribute("parametercategoryModel")
    public ParameterCategory getParameterCategory(){
        return new ParameterCategory();
    }
    
    
    @RequestMapping(value = "parametercategory", method = RequestMethod.GET)
    public String show() {
        getModuleName();
        return "/parametercategory/parametercategory";
    }
    
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
}

