package com.ilids.repository.impl;

import com.ilids.IRepository.DataRepository;
import org.springframework.stereotype.Component;
import com.ilids.domain.Data;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.NoResultException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author cirakas
 */
@Component
public class DataRepositoryImpl extends GenericRepositoryImpl<Data> implements DataRepository {

    private static final Logger logger = LoggerFactory.getLogger(DataRepositoryImpl.class);

    DataRepositoryImpl() {
        super(Data.class);
    }

    public List<Object[]> getAllAlertData(String startDate, String endDate, double mdv, int start, int end) {
        return (List<Object[]>) entityManager.createNativeQuery("SELECT d.id, d.data, d.time, d.address_map, am.param_name,  am.name, d.category FROM data d INNER JOIN address_map am on d.address_map=am.off_set where d.time > '" + startDate + " 00:00:01' and d.time < '" + endDate + " 23:59:59' and d.data>" + mdv + " and (d.address_map=12 or d.address_map=14 or d.address_map=16) ORDER BY d.time DESC LIMIT " + start + "," + end + "").getResultList();
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

    public List<Object[]> getCumilativeEnergy(String startDateParam, String endDateParam, boolean startFlag, String deviceId) {
        List<Object[]> cumialtiveDataList = new ArrayList<Object[]>();
        Object[] startCumilative = null;
        
        cumialtiveDataList = (List<Object[]>) entityManager.createNativeQuery("SELECT tab2.device_id, tab2.zone_identifier , "
                + "SUM(tab2.udiff) units_consumed, ROUND(SUM(tab2.tprice), 3) totalamt "
                + "FROM ( "
                + "SELECT tab1.device_id, tab1.start_time, tab1.cmaxdata - tab1.cmindata udiff, "
                + "(tab1.cmaxdata - tab1.cmindata) * tab1.unit_price tprice , tab1.zone_identifier "
                + "FROM ( "
                + "SELECT tab.device_id, tab.dtime, MIN(tab.data) cmindata, MAX(tab.data) cmaxdata, "
                + "tab.start_time, tab.unit_price , tab.zone_identifier "
                + "FROM ( "
                + "SELECT `device_id`, `data`, DATE(`time`) dtime, tzr.`start_time`, tzr.unit_price , tzr.zone_identifier "
                + "FROM `data` d, `time_zone_range` tzr "
                + "WHERE device_id = '" + deviceId + "' AND `address_map` = 514 AND `time` >= '" + startDateParam + "' AND `time` <= '" + endDateParam + "' "
                + "AND TIME(d.`time`) BETWEEN tzr.`start_time` AND tzr.`end_time` "
                + ") tab GROUP BY tab.`device_id`, tab.dtime, tab.`start_time`, tab.unit_price , tab.zone_identifier "
                + ") tab1 "
                + ") tab2 GROUP BY tab2.device_id, tab2.zone_identifier ").getResultList();
        System.out.println("dhjksha---" + cumialtiveDataList.toString());
        return cumialtiveDataList;
    }

    public Long getAlertCount(String startDateParam, String endDateParam, double mdv) {
        Object alertCount = 0;
        try {
            String alertQuery = "SELECT count(id) FROM data where time > '" + startDateParam + " 00:00:01' and time < '" + endDateParam + " 23:59:59' and data>" + mdv + " and (address_map=12 or address_map=14 or address_map=16)";
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

    public List<Object[]> getAllDataAtTheMoment(String currDate, String beforeDate, long deviceId) {
        return (List<Object[]>) entityManager.createQuery("SELECT u FROM Data u where time BETWEEN '" + beforeDate + "' AND '" + currDate + "' and deviceId='" + deviceId + "'").getResultList();
    }

//    public List<Data> getAllData() {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//    }
}
