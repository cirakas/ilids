/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ilids.IRepository;

import com.ilids.domain.Data;
import java.util.List;

/**
 *
 * @author cirakas
 */
public interface DataRepository extends GenericRepository<Data>{

    /**
     *
     * @param startDate
     * @param endDate
     * @param mdv
     * @param start
     * @param end
     * @return
     */
    public List<Object[]> getAllAlertData(String startDate, String endDate, double mdv, int start, int end);

    /**
     *
     * @param offset
     * @return
     */
    public Data getLatestDataList(int offset);

    /**
     *
     * @param startDateParam
     * @param endDateParam
     * @param startFlag
     * @return
     */
    public List<Object[]> getCumilativeEnergy(String startDateParam, String endDateParam, boolean startFlag);

    /**
     *
     * @param startDateParam
     * @param endDateParam
     * @param mdv
     * @return
     */
    public Long getAlertCount(String startDateParam, String endDateParam, double mdv);

    /**
     *
     * @param startDateParam
     * @param endDateParam
     * @param mdv
     * @return
     */
    public Long getAlertCountForScheduling(String startDateParam, String endDateParam, double mdv);

    /**
     *
     * @param startDate
     * @param endDate
     * @param mdv
     * @return
     */
    public List<Object[]> getAllAlertDataSchedule(String startDate, String endDate, double mdv);

    /**
     *
     * @param lastScheduleTime
     * @param currentScheduleTime
     * @param mdv
     * @return
     */
    public List<Data> getAllAlertDataForSchedule(String lastScheduleTime, String currentScheduleTime, double mdv);

    /**
     *
     * @param currDate
     * @param beforeDate
     * @param deviceId
     * @return
     */
    public List<Object[]> getAllDataAtTheMoment(String currDate, String beforeDate, long deviceId);

}
