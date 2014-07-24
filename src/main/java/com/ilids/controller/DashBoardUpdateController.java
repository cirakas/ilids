/**
 *
 */
package com.ilids.controller;

import com.ilids.conf.ServerConfig;
import com.ilids.domain.Data;
import com.ilids.domain.PollData;
import com.ilids.domain.SystemSettings;
import com.ilids.service.DataService;
import com.ilids.service.SystemSettingsService;
import java.text.ParseException;
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

    PollData pollData;

    @RequestMapping(value = "/dashboardupdate/subscribe" + "", method = RequestMethod.GET)
    @ResponseBody
    public String start() {
	//updateService.subscribe();
	return "OK";
    }

    /**
     * hold on to server resources
     */
    @RequestMapping(value = "/dashboardupdate/polldata", method = RequestMethod.GET)
    @ResponseBody
    public PollData getUpdate() {
	pollData = ServerConfig.pollData;
	return pollData;
    }

    /**
     * hold on to server resources
     *
     * @param startDate
     * @param endDate
     * @return
     */
    @RequestMapping(value = "/dashboardupdate/energyCost", method = RequestMethod.GET)
    @ResponseBody
    public PollData energyCost(@RequestParam(value = "startDate", required = true) String startDate, @RequestParam(value = "endDate", required = true) String endDate) throws ParseException {
	double startCumilative = 0;
	double endCumilative = 0;
	double mdv=100;
	logger.info("Calculating energy cost");
	List<Object[]> cumilativeData = dataService.getCumilativeEnergy(startDate, endDate, true);
	SystemSettings systemSettings=systemSettingsService.getAllSystemSettings().get(0);
	mdv=systemSettings.getMdv();
	Long alertCount=dataService.getAlertCount(startDate, endDate, mdv);
	if (cumilativeData.size() > 0) {
	    startCumilative = (Double)cumilativeData.get(0)[1];
	    endCumilative = (Double)cumilativeData.get(1)[1];
	}
	PollData pollData = ServerConfig.pollData;
	pollData.energyCost = (endCumilative - startCumilative) * 6.5;
	pollData.alertCount=alertCount;
	logger.info("Energy cost calculated::"+pollData.alertCount);
	logger.info("Alert count::"+alertCount);
	return pollData;
    }
    
    @RequestMapping(value = "/dashboardupdate/alertList", method = RequestMethod.POST)
    @ResponseBody
    public List<Object[]> alertData(@RequestParam(value = "startDate", required = true) String startDate, @RequestParam(value = "endDate", required = true) String endDate ) throws ParseException{
	SystemSettings systemSettings=systemSettingsService.getAllSystemSettings().get(0);
	double mdv=systemSettings.getMdv();
	List<Object[]> alertDataList=dataService.getAllAlertData(startDate,endDate,mdv);
	return alertDataList;
    }
}
