package com.ilids.service.impl;

import com.ilids.IRepository.DataRepository;
import com.ilids.IService.DataService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import com.ilids.domain.Data;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author cirakas
 */
@Component
@Transactional
public class DataServiceImpl implements DataService{

    @Autowired
    private DataRepository dataRepository;

    //should be removed
    @Override
    public JSONArray getAllData() {
	List<Data> dataList = dataRepository.getAll();
	JSONArray arr = new JSONArray();
	JSONObject tmp;
	for (int i = 0; i < dataList.size(); i++) {
	    if (8 == dataList.get(i).getAddressMap().getOffSet()) {
		tmp = new JSONObject();
		tmp.put("voltage", dataList.get(i).getData()); //some public getters inside GraphUser?
		tmp.put("date", dataList.get(i).getTime());
		tmp.put("current", dataList.get(i).getData());
		tmp.put("power", dataList.get(i).getData());
		arr.put(tmp);
	    }
	}

	return arr;
    }

    @Override
    public Data findById(Long id) {
	return dataRepository.findById(id);
    }

    @Override
    public Data remove(Long id) {
	Data data = dataRepository.findById(id);
	if (data == null) {
	    throw new IllegalArgumentException();
	}
	//  device.getUser().getDevices().remove(device); //pre remove
	dataRepository.delete(data);
	return data;
    }

    @Override
    public boolean addData(Data data) {

	dataRepository.persist(data);
	return true;
    }

    @Override
    public List<Object[]> getAllAlertData(String startDate,String endDate,double mdv,int start,int end) throws ParseException {
	startDate = convertToDate(startDate);
	endDate = convertToDate(endDate);
        List<Object[]> alertDatas = new ArrayList<Object[]>();
        alertDatas = dataRepository.getAllAlertData(startDate,endDate,mdv,start,end);
	return alertDatas;
    }

    @Override
    public Data getLatestPowerFactorValues(int offSet) {
	Data data = dataRepository.getLatestDataList(offSet);
	return data;
    }

    @Override
    public List<Object[]> getCumilativeEnergy(String startDateValue, String endDateValue, boolean startFlag) throws ParseException {
	//startDateValue = convertToDate(startDateValue);
	//endDateValue = convertToDate(endDateValue);
	List<Object[]> cumilativeDataList = dataRepository.getCumilativeEnergy(startDateValue, endDateValue, startFlag);
	return cumilativeDataList;
    }

    @Override
    public Long getAlertCount(String startDateValue, String endDateValue, double mdv) throws ParseException {
	startDateValue = convertToDate(startDateValue);
	endDateValue = convertToDate(endDateValue);
	Long alertCount = dataRepository.getAlertCount(startDateValue, endDateValue, mdv);
	return alertCount;
    }

    @Override
    public String convertToDate(String dateValue) throws ParseException {
	String dateFormat = "MM/dd/yyyy";
	String toDateFormat = "yyyy-MM-dd";
	SimpleDateFormat parsePattern = new SimpleDateFormat(dateFormat);
	SimpleDateFormat parseFormat = new SimpleDateFormat(toDateFormat);
	dateValue = parseFormat.format(parsePattern.parse(dateValue));

	return dateValue;
    }

}
