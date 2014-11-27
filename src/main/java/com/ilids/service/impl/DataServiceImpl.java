package com.ilids.service.impl;

import com.ilids.IRepository.DataRepository;
import com.ilids.IService.DataService;
import com.ilids.conf.ServerConfig;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import com.ilids.domain.Data;
import com.ilids.domain.PollData;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author cirakas
 */
@Component
@Transactional
public class DataServiceImpl implements DataService {

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
        dataRepository.close();
        return arr;
    }

    @Override
    public Data findById(Long id) {
        Data dataId = dataRepository.findById(id);
        dataRepository.close();
        return dataId;
    }

    @Override
    public Data remove(Long id) {
        Data data = dataRepository.findById(id);
        if (data == null) {
            throw new IllegalArgumentException();
        }
        //  device.getUser().getDevices().remove(device); //pre remove
        dataRepository.delete(data);
        dataRepository.close();
        return data;
    }

    @Override
    public boolean addData(Data data) {

        dataRepository.persist(data);
        dataRepository.close();
        return true;
    }

    @Override
    public List<Object[]> getAllAlertData(String startDate, String endDate, double mdv, int start, int end) throws ParseException {
        startDate = convertToDate(startDate);
        endDate = convertToDate(endDate);
        List<Object[]> alertData = new ArrayList<Object[]>();
        alertData = dataRepository.getAllAlertData(startDate, endDate, mdv, start, end);
        dataRepository.close();
        return alertData;
    }

    @Override
    public Data getLatestPowerFactorValues(int offSet) {
        Data data = dataRepository.getLatestDataList(offSet);
        dataRepository.close();
        return data;
    }

    @Override
    public PollData getCumilativeEnergy(String startDateValue, String endDateValue, boolean startFlag, String deviceId) throws ParseException {
//      startDateValue = convertToDate(startDateValue);
//      endDateValue = convertToDate(endDateValue);
        List<Object[]> cumilativeDataList = dataRepository.getCumilativeEnergy(startDateValue, endDateValue, startFlag, deviceId);
        PollData pollData = ServerConfig.pollData;
        pollData.normalCost = 0;
        pollData.peakCost = 0;
        pollData.offPeakCost = 0;
        for (Object[] o : cumilativeDataList) {
            int zoneId = ((Integer) o[1]).intValue();
            if (zoneId == 1) {
                pollData.normalCost = ((Double) o[3]).doubleValue();

            } else if (zoneId == 2) {
                pollData.peakCost = ((Double) o[3]).doubleValue();
            } else {
                pollData.offPeakCost = ((Double) o[3]).doubleValue();
            }
        }
        dataRepository.close();
        return pollData;
    }

    
    @Override
    public Long getAlertCount(String startDateValue, String endDateValue, double mdv) throws ParseException {
        startDateValue = convertToDate(startDateValue);
        endDateValue = convertToDate(endDateValue);
        Long alertCount = dataRepository.getAlertCount(startDateValue, endDateValue, mdv);
        dataRepository.close();
        return alertCount;
    }

    @Override
    public String convertToDate(String dateValue) throws ParseException {
        String dateFormat = "MM/dd/yyyy";
        String toDateFormat = "yyyy-MM-dd";
        SimpleDateFormat parsePattern = new SimpleDateFormat(dateFormat);
        SimpleDateFormat parseFormat = new SimpleDateFormat(toDateFormat);
        dateValue = parseFormat.format(parsePattern.parse(dateValue));
        dataRepository.close();
        return dateValue;
    }

}
