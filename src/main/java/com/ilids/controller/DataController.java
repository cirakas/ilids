package com.ilids.controller;

import com.ilids.IService.DataService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.ilids.domain.Data;
import org.json.JSONArray;

/**
 *
 * @author cirakas
 */
@Controller
public class DataController {

    @Autowired
    private DataService dataService;

    /**
     *
     * @return
     */
    @ModelAttribute("DataModel")
    public Data Data() {
	return new Data();
    }

    /**
     *
     * @return
     */
    @ModelAttribute("DataList")
    public JSONArray getDataList() {
	JSONArray dataList = dataService.getAllData();
	return dataList;
    }

    /**
     *
     * @return
     */
    @RequestMapping(value = "data", method = RequestMethod.GET)
    public String show() {
	return "/home";
    }

}
