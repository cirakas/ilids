/*
 * 
 * Push data in one hour interval to the table `1hour_data`
 * 
 */
package com.ilids.generateData;

import static java.lang.System.out;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DecimalFormat;

public class oneHourInterval {

    // JDBC driver name and database URL
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://192.168.1.50:3306/ilidskims_nov14";

    // Database credentials
    static final String USER = "root";
    static final String PASS = "";

    /**
     *
     * @param args
     * @throws InterruptedException
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    @SuppressWarnings("unchecked")
    public static void main(String[] args) throws InterruptedException, ClassNotFoundException, SQLException {
       String ipdate;
        if(args.length == 0){
            ipdate = "CURRENT_DATE()";
        }
        else{
            ipdate = args[0];
            System.out.println("Date you entered is: " + ipdate );
        }
        
         oneHourInterval obj = new oneHourInterval();
         obj.query(ipdate);
    } 
    
    @SuppressWarnings("unchecked")
    private void query(String idate) throws InterruptedException, ClassNotFoundException, SQLException {
        Connection conn = null;
        // Declare PreparedStatement Objects
        PreparedStatement deviceStmt = null;
        PreparedStatement amapStmt = null;
        PreparedStatement selectStmt = null;
        PreparedStatement updateStmt = null;
        PreparedStatement insertStmt = null;
        // Register JDBC driver
        Class.forName("com.mysql.jdbc.Driver");

        System.out.println("Connecting to database...");

        // Open a connection
        conn = DriverManager.getConnection(DB_URL, USER, PASS);
        if (conn != null) {
            System.out.println("---Connection Successful----");
        }

        //SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Timestamp sdate1;
        Timestamp edate1;
        String stdate = "";
        String enddate = "";
        int deviceId1 = 0;
        int addressMap = 0;
        String maxDat1;
        String minDat1;
        String avg1;
        int slaveId;
        int addmap;
        DecimalFormat df = new DecimalFormat("#.##");
        // SQL Query
        String deviceQuery = "SELECT slave_id as deviceId FROM `devices` WHERE used = 1";

        String amapQuery = "SELECT off_set FROM `address_map`";

        String selectQuery = "SELECT tab5.* , CASE WHEN ROUND(AVG(d.data),2) IS NULL "
                + "THEN  ROUND((tab5.mindat + tab5.maxdat)/2, 2) ELSE ROUND(AVG(d.data),2) END avgdata FROM \n"
                + "(SELECT tab4.device_id, tab4.address_map, tab4.stdate, tab4.enddate, MAX(tab4.data) maxdat , \n"
                + "MIN(tab4.data) mindat FROM\n"
                + "\n"
                + "(SELECT device_id, address_map, tab3.stdate, tab3.enddate, `data`  FROM `30min_data` d, \n"
                + "\n"
                + "(SELECT CAST(CONCAT(tab2.cd, ' ', `1hr_range`) AS DATETIME) stdate, \n"
                + "DATE_ADD(CAST(CONCAT(tab2.cd, ' ', `1hr_range`) AS DATETIME), INTERVAL '59:59' MINUTE_SECOND) "
                + "enddate FROM\n"
                + "\n"
                + "(SELECT tab1.* FROM (\n"
                + "\n"
                + "SELECT tab.*,`1hr_range`, DATE_ADD(`1hr_range`, INTERVAL '59:59' MINUTE_SECOND) enddate FROM (\n"
                + "\n"
                + "SELECT "+idate+" cd) tab ,`time_minute_range` WHERE `1hr_range` IS NOT NULL) tab1\n"
                + "WHERE `1hr_range` BETWEEN tab1.1hr_range AND tab1.enddate) tab2 ) \n"
                + "tab3 \n"
                + "WHERE d.time BETWEEN tab3.stdate AND tab3.enddate AND d.device_id = ? AND d.address_map = ?) \n"
                + "tab4 \n"
                + "GROUP BY tab4.stdate, tab4.enddate) tab5 \n"
                + "\n"
                + "LEFT JOIN `30min_data` d ON ( tab5.device_id = d.device_id AND tab5.address_map = d.address_map \n"
                + "AND d.time BETWEEN tab5.stdate AND tab5.enddate AND d.data > tab5.mindat AND d.data < tab5.maxdat) \n"
                + "GROUP BY tab5.device_id , tab5.address_map , tab5.stdate, tab5.enddate, tab5.maxdat, tab5.mindat";

        String updateQuery = "UPDATE `1hour_data` SET `data` = ?, `time` = ?  WHERE "
                + "`device_id` = ? AND `address_map`= ? AND `time`= ? and `flag` = ? ";

        String insertQuery = "INSERT INTO `1hour_data`  ( `device_id` , `data` , `time` , `address_map`, `flag` )  "
                + "VALUES ( ?, ?, ?, ?, ? )";

        // Declare ResulSet
        ResultSet selectResult = null;
        ResultSet deviceResult = null;
        ResultSet amapResult = null;

        try {
            // Define PreparedStatement Objects
            deviceStmt = conn.prepareStatement(deviceQuery);
            amapStmt = conn.prepareStatement(amapQuery);
            selectStmt = conn.prepareStatement(selectQuery);
            updateStmt = conn.prepareStatement(updateQuery);
            insertStmt = conn.prepareStatement(insertQuery);
            // Execute Query
            deviceResult = deviceStmt.executeQuery();
            amapResult = amapStmt.executeQuery();

            // Extract data from result set - deviceResult
            while (deviceResult.next()) {
                slaveId = deviceResult.getInt("deviceId");
                // Extract data from result set - amapResult
                while (amapResult.next()) {
                    addmap = amapResult.getInt("off_set");

                    selectStmt.setInt(1, slaveId); //slaveId
                    selectStmt.setInt(2, addmap); //addmap
                    selectResult = selectStmt.executeQuery();
                    // Extract data from result set - selectResult
                    while (selectResult.next()) {

                        sdate1 = selectResult.getTimestamp(("stdate"));
                        System.out.println("dddddddddd--" + sdate1);
                        //stdate = format.format(sdate);
                        edate1 = selectResult.getTimestamp(("enddate"));
                        //enddate = format.format(edate);
                        deviceId1 = selectResult.getInt("device_id");
                        addressMap = selectResult.getInt("address_map");
                        maxDat1 = df.format(selectResult.getFloat("maxdat"));
                        minDat1 = df.format(selectResult.getFloat("mindat"));
                        avg1 = df.format(selectResult.getFloat("avgdata"));
                        System.out.println("hhhh--" + deviceId1);

                        updateStmt.setString(1, maxDat1);
                        updateStmt.setTimestamp(2, sdate1);
                        updateStmt.setInt(3, deviceId1); //deviceId1
                        updateStmt.setInt(4, addressMap); // addressMap
                        updateStmt.setTimestamp(5, sdate1);
                        updateStmt.setInt(6, 0);
                        int rows = updateStmt.executeUpdate();
                        System.out.println("rowsss---" + rows);

                        updateStmt.setString(1, minDat1);
                        updateStmt.setTimestamp(2, sdate1);
                        updateStmt.setInt(3, deviceId1); //deviceId1
                        updateStmt.setInt(4, addressMap); // addressMap
                        updateStmt.setTimestamp(5, sdate1);
                        updateStmt.setInt(6, 1);
                        int rows2 = updateStmt.executeUpdate();
                        System.out.println("rowsss2---" + rows2);

                        updateStmt.setString(1, avg1);
                        updateStmt.setTimestamp(2, sdate1);
                        updateStmt.setInt(3, deviceId1); //deviceId1
                        updateStmt.setInt(4, addressMap); // addressMap
                        updateStmt.setTimestamp(5, sdate1);
                        updateStmt.setInt(6, 2);
                        int rows3 = updateStmt.executeUpdate();
                        System.out.println("rowsss1---" + rows3);

                        if (rows == 0) {
                            insertStmt.setInt(1, deviceId1);//deviceId1
                            insertStmt.setString(2, maxDat1);
                            insertStmt.setTimestamp(3, sdate1);
                            insertStmt.setInt(4, addressMap);//addressMap
                            insertStmt.setInt(5, 0);
                            int rowss1 = insertStmt.executeUpdate();
                            System.out.println("insert3--" + rowss1);
                        }

                        if (rows2 == 0) {
                            insertStmt.setInt(1, deviceId1);//deviceId1
                            insertStmt.setString(2, minDat1);
                            insertStmt.setTimestamp(3, sdate1);
                            insertStmt.setInt(4, addressMap);//addressMap
                            insertStmt.setInt(5, 1);
                            int rowss2 = insertStmt.executeUpdate();
                            System.out.println("insert3--" + rowss2);
                        }

                        if (rows3 == 0) {
                            insertStmt.setInt(1, deviceId1);//deviceId1
                            insertStmt.setString(2, avg1);
                            insertStmt.setTimestamp(3, sdate1);
                            insertStmt.setInt(4, addressMap);//addressMap
                            insertStmt.setInt(5, 2);
                            int rowss3 = insertStmt.executeUpdate();
                            System.out.println("insert3--" + rowss3);
                        }

                    } //while (selectResult.next()) {
                } //while (amapResult.next()) {
                amapResult.beforeFirst();

            } //while (deviceResult.next()) {

        } catch (Exception e) {
            //Handle errors for Class.forName
            e.printStackTrace();
        } finally {
            // Clean-up environment
            out.close();
            if (deviceResult != null) {
                deviceResult.close();
            }
            if (amapResult != null) {
                amapResult.close();
            }
            if (selectResult != null) {
                selectResult.close();
            }
            if (deviceStmt != null) {
                deviceStmt.close();
            }
            if (amapStmt != null) {
                amapStmt.close();
            }
            if (selectStmt != null) {
                selectStmt.close();
            }
            if (updateStmt != null) {
                updateStmt.close();
            }
            if (insertStmt != null) {
                insertStmt.close();
            }
            if (conn != null) {
                conn.close();
            }

        }
    }

}
