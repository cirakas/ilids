/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.ilids.conf;

import com.ilids.domain.PollData;

/**
 *
 * @author cirakas
 */
public final class ServerConfig {
    public static String DB_TYPE = "jdbc:mysql:";
    public static String DB_NAME = "ilidsdemo";
    public static String DB_HOSTNAME = "localhost";
    public static String DB_PORT = "3306";
    public static String DB_USERNAME = "root";
    public static String DB_PASS = "password";
    public static PollData pollData=new PollData();
    public static long latestAlertsScheduleCheckTime= 1405535461;
    }
