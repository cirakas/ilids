package com.ilids.dao;

import org.springframework.stereotype.Component;

import com.ilids.domain.Data;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.NoResultException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
public class DataRepository extends AbstractGenericDao<Data> {

    private static final Logger logger = LoggerFactory.getLogger(DataRepository.class);

    public DataRepository() {
	super(Data.class);
    }

    public List<Object[]> getAllAlertData(String startDate, String endDate, double mdv) {
	return (List<Object[]>) entityManager.createNativeQuery("SELECT d.id, d.data, d.time, d.address_map, am.param_name,  am.name, d.category FROM data d INNER JOIN address_map am on d.address_map=am.off_set where d.time > '" + startDate + " 00:00:01' and d.time < '" + endDate + " 23:59:59' and d.data>" + mdv + " and (d.address_map=12 or d.address_map=14 or d.address_map=16)").getResultList();
    }

    public Data getLatestDataList(int offset) {
	Data powerData = null;
	try {
	    Timestamp time = (Timestamp) entityManager.createQuery("select max(e.time) from Data e where e.addressMap = " + offset).getSingleResult();
	    powerData = (Data) entityManager.createQuery("select e from Data e where e.time = '" + time + "' and  e.addressMap = " + offset).getSingleResult();
	} catch (Exception e) {
	    logger.error("There is an Exception(NoResultException) in getLatestDataList method " + e.getMessage());
	}
	return powerData;
    }

    public List<Object[]> getCumilativeEnergy(String startDateParam, String endDateParam, boolean startFlag) {
	List<Object[]> cumialtiveDataList = new ArrayList<Object[]>();
	Object[] startCumilative = null;
	Object[] endCumilative = null;
	try {
	    startCumilative = (Object[]) entityManager.createNativeQuery("SELECT id,data,time,address_map FROM data where time > '" + startDateParam + "' and address_map=512 order by id asc limit 1 ").getSingleResult();
	    endCumilative = (Object[]) entityManager.createNativeQuery("SELECT id,data,time,address_map FROM data where time < '" + endDateParam + "' and address_map=512 order by id desc limit 1 ").getSingleResult();
	} catch (NoResultException e) {
	    // e.printStackTrace();
	    logger.error("There is an Exception in getCumilativeEnergy method for the date between " + startDateParam + " and " + endDateParam + "message" + e.getMessage());
	}
	cumialtiveDataList.add(0, startCumilative);
	cumialtiveDataList.add(1, endCumilative);
	return cumialtiveDataList;
    }

    public Long getAlertCount(String startDateParam, String endDateParam, double mdv) {
	Object alertCount = 0;
	try {
	    String alertQuery="SELECT count(id) FROM data where time > '" + startDateParam + " 00:00:01' and time < '" + endDateParam + " 23:59:59' and data>" + mdv + " and (address_map=12 or address_map=14 or address_map=16)";
	    alertCount = entityManager.createNativeQuery(alertQuery).getSingleResult();
	    Long.valueOf(alertCount.toString());
	} catch (Exception e) {
	    logger.error("There is an Exception in getAlertCount method " + e.getMessage());
	}
	return Long.valueOf(alertCount.toString());
    }

    public Long getAlertCountForScheduling(String startDateParam, String endDateParam, double mdv) {
	Object alertCount = 0;
	try {
	    alertCount = entityManager.createNativeQuery("SELECT count(id) FROM data where time > '" + startDateParam + "' and time < '" + endDateParam + "' and data>" + mdv + " and (address_map=12 or address_map=14 or address_map=16)").getSingleResult();
	} catch (Exception e) {
	    logger.error("There is an Exception in getAlertCountForScheduling method " + e.getMessage());
	}
	return Long.valueOf(alertCount.toString());
    }

    public List<Object[]> getAllAlertDataSchedule(String startDate, String endDate, double mdv) {
	return (List<Object[]>) entityManager.createNativeQuery("SELECT d.id, d.data, d.time, d.address_map, am.param_name, am.name, d.category FROM data d INNER JOIN address_map am on d.address_map=am.off_set where d.time > '" + startDate + "' and d.time < '" + endDate + "' and d.data>" + mdv + " and (d.address_map=12 or d.address_map=14 or d.address_map=16)").getResultList();
    }

    public List<Data> getAllAlertDataForSchedule(String lastScheduleTime, String currentScheduleTime, double mdv) {
	return (List<Data>) entityManager.createQuery("SELECT u FROM Data u where time BETWEEN '" + lastScheduleTime + "' AND '" + currentScheduleTime + "' and data > " + mdv + " and (address_map=12 or address_map=14 or address_map=16)").getResultList();
    }

//    public List<Data> getAllData() {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//    }
}
