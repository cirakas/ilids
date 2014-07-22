package com.ilids.dao;

import org.springframework.stereotype.Component;

import com.ilids.domain.Data;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Component
public class DataRepository extends AbstractGenericDao<Data> {

    public DataRepository() {
	super(Data.class);
    }

    public List<Data> getAllAlertData(double mdv) {
	return (List<Data>) entityManager.createQuery("SELECT u FROM Data u where time BETWEEN '2014-07-10 00:00:01' AND '2014-07-29 23:59:59' and data > " + mdv + " and (address_map=12 or address_map=14 or address_map=16)").getResultList();
    }

    public Data getLatestDataList(int offset) {
	Timestamp time = (Timestamp) entityManager.createQuery("select max(e.time) from Data e where e.addressMap = " + offset).getSingleResult();
	Data powerData = (Data) entityManager.createQuery("select e from Data e where e.time = '" + time + "' and  e.addressMap = " + offset).getSingleResult();
	return powerData;
    }

    public List<Data> getCumilativeEnergy(String startDateParam, String endDateParam, boolean startFlag) {
	List<Data> cumilativeData = (List<Data>) entityManager.createQuery("SELECT u FROM Data u where time BETWEEN '" + startDateParam + " 00:00:01' AND '" + endDateParam + " 23:59:59' and address_map=512)").getResultList();
	List<Data> actualDataList = new ArrayList<Data>();
	if (cumilativeData != null && cumilativeData.size() > 0) {
	    actualDataList.add(0, cumilativeData.get(0));
	    actualDataList.add(0, cumilativeData.get(cumilativeData.size() - 1));
	}
	return actualDataList;
    }

    public List<Data> getAllAlertDataForSchedule(String lastScheduleTime, String currentScheduleTime, double mdv) {
	return (List<Data>) entityManager.createQuery("SELECT u FROM Data u where time BETWEEN '" + lastScheduleTime + "' AND '" + currentScheduleTime + "' and data > " + mdv + " and (address_map=12 or address_map=14 or address_map=16)").getResultList();
    }

//    public List<Data> getAllData() {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//    }
}
