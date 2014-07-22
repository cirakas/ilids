/**
 *
 */
package com.ilids.controller;

import com.ilids.conf.ServerConfig;
import com.ilids.domain.Data;
import com.ilids.domain.PollData;
import com.ilids.service.DataService;
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
	List<Data> cumilativeData = dataService.getCumilativeEnergy(startDate, endDate, true);
	if (cumilativeData.size() > 0) {
	    startCumilative = cumilativeData.get(1).getData();
	    endCumilative = cumilativeData.get(0).getData();
	}
	PollData pollData = ServerConfig.pollData;
	pollData.energyCost = (endCumilative - startCumilative) * 6.5;
	return pollData;
    }
}
