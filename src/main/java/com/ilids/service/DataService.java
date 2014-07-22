package com.ilids.service;

import com.ilids.dao.DataRepository;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.ilids.domain.Data;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import org.json.JSONArray;
import org.json.JSONObject;

@Component
@Transactional
public class DataService {

    @Autowired
    private DataRepository dataRepository;

    //should be removed
    public JSONArray getAllData() {
	List<Data> dataList = dataRepository.getAll();
	JSONArray arr = new JSONArray();
	JSONObject tmp;
	for (int i = 0; i < dataList.size(); i++) {
	    System.out.println("Inside loop" + i);
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

    public Data findById(Long id) {
	return dataRepository.findById(id);
    }

    public Data remove(Long id) {
	Data data = dataRepository.findById(id);
	if (data == null) {
	    throw new IllegalArgumentException();
	}
	//  device.getUser().getDevices().remove(device); //pre remove
	dataRepository.delete(data);
	return data;
    }

    public boolean addData(Data data) {

	dataRepository.persist(data);
	return true;
    }

    public List<Data> getAllAlertData(double mdv) {
	List<Data> alertDatas = dataRepository.getAllAlertData(mdv);
	return alertDatas;
    }

    public Data getLatestPowerFactorValues(int offSet) {
	Data data = dataRepository.getLatestDataList(offSet);
	return data;
    }

    public List<Data> getCumilativeEnergy(String startDateValue, String endDateValue, boolean startFlag) throws ParseException {
	startDateValue = convertToDate(startDateValue);
	endDateValue = convertToDate(endDateValue);
	List<Data> cumilativeDataList = dataRepository.getCumilativeEnergy(startDateValue, endDateValue, startFlag);
	return cumilativeDataList;
    }

    public String convertToDate(String dateValue) throws ParseException {
	String dateFormat = "MM/dd/yyyy";
	String toDateFormat = "yyyy-MM-dd";
	SimpleDateFormat parsePattern = new SimpleDateFormat(dateFormat);
	SimpleDateFormat parseFormat = new SimpleDateFormat(toDateFormat);
	dateValue = parseFormat.format(parsePattern.parse(dateValue));

	return dateValue;
    }

}
