/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ilids.domain;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author cirakas
 */
public class PollData {
    
    public double phase1Value=0.68;
    public double phase2Value=0.68;
    public double phase3Value=0.68;
    public double energyCost=0;
    public double normalCost=0;
    public double offPeakCost=0;
    public double peakCost=0;
    public double alertCount=0;
    public String alertListValue=null;

    public List<String> getAlertList() {
        return alertList;
    }

    public void setAlertList(List<String> alertList) {
        this.alertList = alertList;
    }
    public List<String> alertList=new ArrayList<String>();
    
}
