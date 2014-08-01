/**
 *
 */
package com.ilids.controller;

import com.ilids.conf.ServerConfig;
import com.ilids.domain.PollData;
import com.ilids.domain.SystemSettings;
import com.ilids.service.DataService;
import com.ilids.service.SystemSettingsService;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
     * @param fromHours
     * @param fromMinutes
     * @param toHours
     * @param toMinutes
     * @param endDate
     * @return
     * @throws java.text.ParseException
     */
    @RequestMapping(value = "/dashboardupdate/energyCost", method = RequestMethod.GET)
    @ResponseBody

    public PollData energyCost(@RequestParam(value = "startDate", required = true) String startDate, @RequestParam(value = "fromHours", required = true) String fromHours, @RequestParam(value = "fromMinutes", required = true) String fromMinutes, @RequestParam(value = "endDate", required = true) String endDate, @RequestParam(value = "toHours", required = true) String toHours, @RequestParam(value = "toMinutes", required = true) String toMinutes) throws ParseException {
	double startCumilative = 0;
	double endCumilative = 0;
	double peakCost = 0;
	double normalCost = 0;
	double offPeakCost = 0;
	double energyCost = 0;
	double mdv = 100;
	logger.info("Calculating energy cost");
	SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
	SimpleDateFormat formatterWithTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	String fromDateTime = startDate + " " + fromHours + ":" + fromMinutes + ":01";
	String toDateTime = endDate + " " + toHours + ":" + toMinutes + ":00";
	Date fromDate = convertToDate(fromDateTime);
	Date toDate = convertToDate(toDateTime);
	Calendar beginCalendar = Calendar.getInstance();
	beginCalendar.setTime(fromDate);
	Calendar endCalendar = Calendar.getInstance();
	endCalendar.setTime(toDate);
	while (beginCalendar.compareTo(endCalendar) <= 0) {
	    // ... calculations
	    if (!beginCalendar.after(endCalendar)) {
		Date normalStart = convertToDateSameFormat(formatter.format(beginCalendar.getTime()) + " 06:00:01");
		Date normalEnd = convertToDateSameFormat(formatter.format(beginCalendar.getTime()) + " 17:59:59");
		Date peakStart = convertToDateSameFormat(formatter.format(beginCalendar.getTime()) + " 18:00:01");
		Date peakEnd = convertToDateSameFormat(formatter.format(beginCalendar.getTime()) + " 21:59:59");
		Date offPeakStart = convertToDateSameFormat(formatter.format(beginCalendar.getTime()) + " 22:00:01");
		Date offPeakEnd = convertToDateSameFormat(formatter.format(beginCalendar.getTime()) + " 23:59:59");
		Date offPeakStartMrng = convertToDateSameFormat(formatter.format(beginCalendar.getTime()) + " 00:00:01");
		Date offPeakEndMrng = convertToDateSameFormat(formatter.format(beginCalendar.getTime()) + " 05:59:59");
		if (offPeakStartMrng.getTime() < beginCalendar.getTimeInMillis()) {
		    offPeakStartMrng = convertToDateSameFormat(formatterWithTime.format(beginCalendar.getTime()));
		}
		if (offPeakEndMrng.getTime() > endCalendar.getTimeInMillis()) {
		    offPeakEndMrng = convertToDateSameFormat(formatterWithTime.format(endCalendar.getTime()));
		}

		if (normalStart.getTime() < beginCalendar.getTimeInMillis()) {
		    normalStart = convertToDateSameFormat(formatterWithTime.format(beginCalendar.getTime()));
		}
		if (normalEnd.getTime() > endCalendar.getTimeInMillis()) {
		    normalEnd = convertToDateSameFormat(formatterWithTime.format(endCalendar.getTime()));
		}

		if (peakStart.getTime() < beginCalendar.getTimeInMillis()) {
		    peakStart = convertToDateSameFormat(formatterWithTime.format(beginCalendar.getTime()));
		}
		if (peakEnd.getTime() > endCalendar.getTimeInMillis()) {
		    peakEnd = convertToDateSameFormat(formatterWithTime.format(endCalendar.getTime()));
		}

		if (offPeakStart.getTime() < beginCalendar.getTimeInMillis()) {
		    offPeakStart = convertToDateSameFormat(formatterWithTime.format(beginCalendar.getTime()));
		}
		if (offPeakEnd.getTime() > endCalendar.getTimeInMillis()) {
		    offPeakEnd = convertToDateSameFormat(formatterWithTime.format(endCalendar.getTime()));
		}

		if (offPeakEndMrng.after(offPeakStartMrng)) {
		    energyCost = calculateEnergy(convertDateToString(offPeakStartMrng), convertDateToString(offPeakEndMrng), 5.525);
		}
		offPeakCost = offPeakCost + energyCost;

		if (normalEnd.after(normalStart)) {
		    energyCost = calculateEnergy(convertDateToString(normalStart), convertDateToString(normalEnd), 6.5);
		}
		normalCost = normalCost + energyCost;

		if (peakEnd.after(peakStart)) {
		    energyCost = calculateEnergy(convertDateToString(peakStart), convertDateToString(peakEnd), 9.1);
		    peakCost = peakCost + energyCost;
		}

		if (offPeakEnd.after(offPeakStart)) {
		    energyCost = calculateEnergy(convertDateToString(peakStart), convertDateToString(peakEnd), 6.5);
		    offPeakCost = offPeakCost + energyCost;
		}
		beginCalendar.add(Calendar.DATE, 1);
	    }
	}

	SystemSettings systemSettings = systemSettingsService.getAllSystemSettings().get(0);
	mdv = systemSettings.getMdv();
	Long alertCount = dataService.getAlertCount(startDate, endDate, mdv);
	PollData pollData = ServerConfig.pollData;
	pollData.normalCost = 0;
	pollData.offPeakCost = 0;
	pollData.peakCost = 0;
	pollData.energyCost = (endCumilative - startCumilative) * 6.5;
	pollData.alertCount = alertCount;
	if (peakCost > 0) {
	    pollData.peakCost = Math.round(peakCost);
	}
	if (normalCost > 0) {
	    pollData.normalCost = Math.round(normalCost);
	}
	if (offPeakCost > 0) {
	    pollData.offPeakCost = Math.round(offPeakCost);
	}

	logger.info("Energy cost calculated::" + pollData.alertCount);
	//logger.info("Alert count::"+alertCount);
	return pollData;
    }

    public double calculateEnergy(String startDate, String endDate, double rate) throws ParseException {
	double startCumilative = 0;
	double endCumilative = 0;
	double energyCost = 0;
	List<Object[]> cumilativeData = dataService.getCumilativeEnergy(startDate, endDate, true);
	if (cumilativeData.size() > 0 && cumilativeData.get(0) != null && cumilativeData.get(0) != null) {
	    startCumilative = (Double) cumilativeData.get(0)[1];
	    endCumilative = (Double) cumilativeData.get(1)[1];
	}
	energyCost = (endCumilative - startCumilative) * rate;
	return energyCost;
    }

    @RequestMapping(value = "/dashboardupdate/alertList", method = RequestMethod.POST)
    @ResponseBody
    public List<Object[]> alertData(@RequestParam(value = "startDate", required = true) String startDate, @RequestParam(value = "endDate", required = true) String endDate) throws ParseException {
	SystemSettings systemSettings = systemSettingsService.getAllSystemSettings().get(0);
	double mdv = systemSettings.getMdv();
	List<Object[]> alertDataList = dataService.getAllAlertData(startDate, endDate, mdv);
	return alertDataList;
    }

    public Date convertToDate(String dateValue) throws ParseException {
	String dateFormat = "MM/dd/yyyy HH:mm:ss";
	String toDateFormat = "yyyy-MM-dd HH:mm:ss";
	SimpleDateFormat parsePattern = new SimpleDateFormat(dateFormat);
	SimpleDateFormat parseFormat = new SimpleDateFormat(toDateFormat);
	dateValue = parseFormat.format(parsePattern.parse(dateValue));
	return parseFormat.parse(dateValue);
    }

    public Date convertToDateSameFormat(String dateValue) throws ParseException {
	String toDateFormat = "yyyy-MM-dd HH:mm:ss";
	SimpleDateFormat parseFormat = new SimpleDateFormat(toDateFormat);
	return parseFormat.parse(dateValue);
    }

    public String convertDateToString(Date dateValue) throws ParseException {
	String toDateFormat = "yyyy-MM-dd HH:mm:ss";
	SimpleDateFormat parseFormat = new SimpleDateFormat(toDateFormat);
	return parseFormat.format(dateValue);
    }

}
