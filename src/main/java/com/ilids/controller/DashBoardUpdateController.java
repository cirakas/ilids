/**
 *
 */
package com.ilids.controller;

import com.ilids.IService.AddressMapsService;
import com.ilids.IService.DataService;
import com.ilids.IService.SystemSettingsService;
import com.ilids.conf.ServerConfig;
import com.ilids.domain.AddressMaps;
import com.ilids.domain.PollData;
import com.ilids.domain.SystemSettings;
import com.ilids.service.impl.SystemSettingsServiceImpl;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Long pole dashboard update.
 *
 * @author Cirakas
 *
 */
@Controller()
public class DashBoardUpdateController {

    private static final Logger logger = LoggerFactory.getLogger(DashBoardUpdateController.class);

    @Autowired
    private DataService dataService;

    @Autowired
    private SystemSettingsService systemSettingsService;
    
    @Autowired
    private AddressMapsService addressMapsService;

    PollData pollData;

    /**
     *
     * @return
     */
    @RequestMapping(value = "/dashboardupdate/subscribe" + "", method = RequestMethod.GET)
    @ResponseBody
    public String start() {
	//updateService.subscribe();
	return "OK";
    }

    /**
     * hold on to server resources
     * @return 
     */
    @RequestMapping(value = "/dashboardupdate/polldata", method = RequestMethod.GET)
    @ResponseBody
    public PollData getUpdate() {
	pollData = ServerConfig.pollData;
        pollData.alertListValue=null;
        for(String alert:pollData.getAlertList()){
             if(pollData.alertListValue==null){
                  pollData.alertListValue="ILIDS system captured alerts on "+alert;
             }else{
		pollData.alertListValue=pollData.alertListValue+" , "+alert;
	     }
        }
	
	if(pollData.getAlertList().size()>0){
	    pollData.alertListValue=pollData.alertListValue+ ". Please follow up.";
	}
	
        ServerConfig.pollData.setAlertList(new ArrayList<String>());
	return pollData;
    }

    /**
     * hold on to server resources
     *
     * @param startDate
     * @param fromHours
     * @param fromMinutes
     * @param toHours
     * @param toMinutes
     * @param endDate
     * @param deviceId
     * @return
     * @throws java.text.ParseException
     */
    @RequestMapping(value = "/dashboardupdate/energyCost", method = RequestMethod.GET)
    @ResponseBody

    public PollData energyCost(@RequestParam(value = "startDate", required = true) String startDate, 
            @RequestParam(value = "fromHours", required = true) String fromHours, 
            @RequestParam(value = "fromMinutes", required = true) String fromMinutes, 
            @RequestParam(value = "endDate", required = true) String endDate, 
            @RequestParam(value = "toHours", required = true) String toHours, 
            @RequestParam(value = "toMinutes", required = true) String toMinutes, 
            @RequestParam(value = "deviceId", required = true) String deviceId) throws ParseException {
        
            String startDateTime = startDate + " " + fromHours + ":" + fromMinutes + ":" + "00" ;
            String endDateTime = endDate + " " + toHours + ":" + toMinutes + ":" + "00" ;
            String realFormat = "MM/dd/yyyy HH:mm:ss";
            String pattern = "yyyy-MM-dd HH:mm:ss";
            SimpleDateFormat parsePattern = new SimpleDateFormat(realFormat);
            SimpleDateFormat parseFormat = new SimpleDateFormat(pattern);
            startDateTime = parseFormat.format(parsePattern.parse(startDateTime));
            endDateTime = parseFormat.format(parsePattern.parse(endDateTime));
            System.out.println("startDate--- " + startDateTime );
            System.out.println("endDate--- " + endDateTime );
            
        
            PollData pollData = dataService.getCumilativeEnergy(startDateTime, endDateTime, true,deviceId);
            return pollData;
    }

    /**
     *
     * @param startDate
     * @param endDate
     * @param rate
     * @return
     * @throws ParseException
     */
//    public double calculateEnergy(String startDate, String endDate, double rate,String deviceId) throws ParseException {
//	double startCumilative = 0;
//	double endCumilative = 0;
//	double energyCost = 0;
//	PollData cumilativeData = dataService.getCumilativeEnergy(startDate, endDate, true,deviceId);
//	return energyCost;
//    }

    /**
     *
     * @param startDate
     * @param endDate
     * @param start
     * @param end
     * @return
     * @throws ParseException
     */
    @RequestMapping(value = "/dashboardupdate/alertList", method = RequestMethod.POST)
    @ResponseBody
    public List<Object[]> alertData(@RequestParam(value = "startDate", required = true) String startDate, @RequestParam(value = "endDate", required = true) String endDate,int start,int end) throws ParseException {
	SystemSettings systemSettings = systemSettingsService.getAllSystemSettings().get(0);
	double mdv = systemSettings.getMdv();
        List<Object[]> alertDataList = new ArrayList<Object[]>();
	alertDataList = dataService.getAllAlertData(startDate, endDate, mdv,start,end);
	return alertDataList;
    }

    /**
     *
     * @param dateValue
     * @return
     * @throws ParseException
     */
    public Date convertToDate(String dateValue) throws ParseException {
	String dateFormat = "MM/dd/yyyy HH:mm:ss";
	String toDateFormat = "yyyy-MM-dd HH:mm:ss";
	SimpleDateFormat parsePattern = new SimpleDateFormat(dateFormat);
	SimpleDateFormat parseFormat = new SimpleDateFormat(toDateFormat);
	dateValue = parseFormat.format(parsePattern.parse(dateValue));
	return parseFormat.parse(dateValue);
    }

    /**
     *
     * @param dateValue
     * @return
     * @throws ParseException
     */
    public Date convertToDateSameFormat(String dateValue) throws ParseException {
	String toDateFormat = "yyyy-MM-dd HH:mm:ss";
	SimpleDateFormat parseFormat = new SimpleDateFormat(toDateFormat);
	return parseFormat.parse(dateValue);
    }

    /**
     *
     * @param dateValue
     * @return
     * @throws ParseException
     */
    public String convertDateToString(Date dateValue) throws ParseException {
	String toDateFormat = "yyyy-MM-dd HH:mm:ss";
	SimpleDateFormat parseFormat = new SimpleDateFormat(toDateFormat);
	return parseFormat.format(dateValue);
    }

}
