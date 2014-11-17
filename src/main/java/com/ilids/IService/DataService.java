/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ilids.IService;

import com.ilids.domain.Data;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;

/**
 *
 * @author cirakas
 */
public interface DataService {

    /**
     *
     * @return
     */
    public JSONArray getAllData();

    /**
     *
     * @param id
     * @return
     */
    public Data findById(Long id);

    /**
     *
     * @param id
     * @return
     */
    public Data remove(Long id);

    /**
     *
     * @param data
     * @return
     */
    public boolean addData(Data data);

    /**
     *
     * @param startDate
     * @param endDate
     * @param mdv
     * @param start
     * @param end
     * @return
     * @throws ParseException
     */
    public List<Object[]> getAllAlertData(String startDate, String endDate, double mdv, int start, int end) throws ParseException;

    /**
     *
     * @param offSet
     * @return
     */
    public Data getLatestPowerFactorValues(int offSet);

    /**
     *
     * @param startDateValue
     * @param endDateValue
     * @param startFlag
     * @param deviceId
     * @return
     * @throws ParseException
     */
    public List<Object[]> getCumilativeEnergy(String startDateValue, String endDateValue, boolean startFlag, String deviceId) throws ParseException;

    /**
     *
     * @param startDateValue
     * @param endDateValue
     * @param mdv
     * @return
     * @throws ParseException
     */
    public Long getAlertCount(String startDateValue, String endDateValue, double mdv) throws ParseException;

    /**
     *
     * @param dateValue
     * @return
     * @throws ParseException
     */
    public String convertToDate(String dateValue) throws ParseException;

}
