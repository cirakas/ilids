/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ilids.servlet;

import com.ilids.conf.ServerConfig;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;
import java.io.IOException;
import java.io.PrintWriter;
import static java.lang.System.out;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.TimeZone;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author cirakas
 */
public class DataAccessServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     * @throws java.sql.SQLException
     * @throws java.text.ParseException
     */
    private HashMap<Integer, Integer> hashAddress = new HashMap<Integer, Integer>();

    @Override
    public void init() throws ServletException {
        super.init(); //To change body of generated methods, choose Tools | Templates.
        hashAddress.put(6, 0);
        hashAddress.put(8, 2);
        hashAddress.put(10, 4);
        hashAddress.put(12, 0);
        hashAddress.put(14, 2);
        hashAddress.put(16, 4);
    }
    
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
	 throws ServletException, IOException, SQLException, ParseException {
	response.setContentType("application/json");
	String connectionUrl=ServerConfig.DB_TYPE+"//"+ServerConfig.DB_HOSTNAME+":"+ServerConfig.DB_PORT+"/"+ServerConfig.DB_NAME;
	String dbUserName = ServerConfig.DB_USERNAME;
	String dbPassword = ServerConfig.DB_PASS;
	Connection connection = (Connection) DriverManager.getConnection(connectionUrl, dbUserName, dbPassword);
	Statement statement = (Statement) connection.createStatement();
        String phase=request.getParameter("phase");
        int addressMap = Integer.parseInt(phase);
        String start=request.getParameter("fromDate");
        String end=request.getParameter("toDate");
	String fromHours=request.getParameter("fromHours");
	String fromMinutes=request.getParameter("fromMinutes");
	String toHours=request.getParameter("toHours");
	String toMinutes=request.getParameter("toMinutes");
	String fromTime=fromHours+":"+fromMinutes+":00";
	String toTime=toHours+":"+toMinutes+":59";
        String deviceId=request.getParameter("deviceId");
        
        String dateFormat="MM/dd/yyyy";
        String toDateFormat="yyyy-MM-dd";
        SimpleDateFormat parsePattern = new SimpleDateFormat(dateFormat);
        SimpleDateFormat parseFormat = new SimpleDateFormat(toDateFormat);
        start=parseFormat.format(parsePattern.parse(start));
        end=parseFormat.format(parsePattern.parse(end));
                
	//String selectQuery = "SELECT time as data_time , data as real_data FROM data_3m_1 WHERE `time` BETWEEN '"+start+" "+fromTime+"' AND '"+end+" "+toTime+"'  and address_map="+addressMap+""+deviceCondition+" ORDER BY time ";
        String selectQuery = 
            "SELECT tab2.device_id, tab2.time, tab2.data y0_data, SUM(tab2.y1data) y1_data FROM (" +
            "SELECT tab.device_id, tab.time , tab.data ," +
            "CASE WHEN tab.time BETWEEN tab1.stdate AND tab1.enddate THEN tab1.ydata " +
            "ELSE 0 END y1data FROM (" +
            "SELECT device_id, data , time FROM data_3m_1 d" +
            " WHERE d.device_id = " + deviceId +
            " AND d.address_map = " + addressMap +
            " AND d.time BETWEEN '" + start + " " + fromTime + "' AND '" + end + " " + toTime + "' " +
            ") tab LEFT JOIN" +
            "(" +
            "" +
            "SELECT tab2.device_id, tab2.stdate, tab2.enddate, ((MAX(tab2.data) - MIN(tab2.data)) * 2) ydata FROM (" +
            "SELECT device_id, tab1.stdate, tab1.enddate, `data`  FROM data_3m_1 d, " +
            "(" +
            "SELECT CAST(CONCAT(tab.dt, ' ', 30min_range) AS DATETIME) stdate, " +
            "DATE_ADD(CAST(CONCAT(tab.dt, ' ', 30min_range) AS DATETIME), INTERVAL '29:59' MINUTE_SECOND) enddate FROM (" +
            "SELECT DISTINCT DATE(time) dt FROM data_3m_1 d " +
            "WHERE device_id = " + deviceId +
            " AND address_map = 512 " +
            " AND time BETWEEN '" + start + " " + fromTime + "' AND '" + end + " " + toTime + "' " +
            ") tab, time_minute_range tmr WHERE tmr.30min_range IS NOT NULL" +
            ") tab1 " +
            "WHERE d.time BETWEEN tab1.stdate AND tab1.enddate" +
            " AND d.device_id = " + deviceId +
            " AND d.address_map = 512 " +
            " AND time BETWEEN '" + start + " " + fromTime + "' AND '" + end + " " + toTime + "' " +
            ") tab2 GROUP BY tab2.stdate, tab2.enddate" +
            "" +
            ") tab1 ON (tab.device_id = tab1.device_id)" +
            ") tab2 GROUP BY tab2.device_id, tab2.time, tab2.data ORDER BY tab2.time";
        
        
        ResultSet rs = statement.executeQuery(selectQuery);
	PrintWriter out = response.getWriter();
 
	JSONArray jsonArray = new JSONArray();
	try {
	    /* TODO output your page here. You may use following sample code. */
	    float datas = 1;
            Date prevDate = null;
            Date nextDate = null;
	    Date realDate = null;
            String date1 = "";
            long diff;
            long diffSeconds = 0;
            long diffMinutes = 0;
            long diffHours = 0;
            String diffSec ="";
            String diffMin = "";
            String diffHou = "";
	   // String pattern = "MM/dd/yyyy HH:mm:ss";
            DecimalFormat df = new DecimalFormat("#.##");
	    SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
	    while (rs.next()) {
                realDate = rs.getTimestamp("time");
                //System.out.println("djklsd---sck--" + realDate);
                if(rs.getRow() == 1) {
                    prevDate = realDate;
                }
		JSONObject json = new JSONObject();
		json.put("date", format.format(realDate));
                json.put("pd", format.format(prevDate));                
		json.put("current", df.format(rs.getFloat("y0_data")));
                json.put("mdv", df.format(rs.getFloat("y1_data")));
                try {
                    if(rs.next()){
                        nextDate = rs.getTimestamp("time");
                        System.out.println("---curr date--" + realDate);
                        System.out.println("--next date--" + nextDate);
                        rs.previous();
                    } 
                    
                diff = nextDate.getTime() - realDate.getTime();
                    System.out.println("-- dd  --- " + diff);
                diffSeconds = diff / 1000 % 60;
                diffMinutes = diff / (60 * 1000) % 60;         
                diffHours = diff / (60 * 60 * 1000) % 60; 
                if(diffSeconds < 10) {
                    System.out.println("---diffSeconds--" + diffSeconds);
                    diffSec = "0" + diffSeconds;
                    System.out.println("dfscnd--" + diffSec);
                }
                else{
                    diffSec = Long.toString(diffSeconds);
                    System.out.println("dfscnd--" + diffSec);
                }
                
                 if(diffMinutes < 10) {
                    System.out.println("---diffMinutes--" + diffMinutes);
                    diffMin = "0" + diffMinutes;
                    System.out.println("diffMin--" + diffMin);
                }
                 
                 else{
                    diffMin = Long.toString(diffMinutes);
                    System.out.println("diffMin--" + diffMin);
                }
                
                 if(diffHours < 10) {
                    System.out.println("---diffHours--" + diffHours);
                    diffHou = "0" + diffHours;
                    System.out.println("diffHou--" + diffHou);
                }
                 
                 else{
                    diffHou = Long.toString(diffHours);
                    System.out.println("diffHou--" + diffHou);
                }
                
                date1=diffHou+":"+diffMin+":"+diffSec;
                System.out.println("--difff----" + date1);
                
                } catch(SQLException sqe) {}
                json.put("nd", format.format(nextDate));	
                json.put("df",date1);	
		jsonArray.put(json);
                prevDate = realDate;
                
                
                
	    }
	    out.println(jsonArray.toString());
            System.out.println("----json---" + jsonArray.toString());
            
           
	    
	} catch (Exception e) {
	    e.printStackTrace();
	} finally {
	    out.close();
            if (rs != null) {
		rs.close();
	    }
	    if (connection != null) {
		connection.close();
	    } 
            
	}
//        PrintWriter out = response.getWriter();
//        JSONArray jsonArray = new JSONArray();
//        JSONObject json = new JSONObject();
//		json.put("date", "11/20/2014 12:10:10");               
//		json.put("current", "50");
//                json.put("mdv", "260");
//                jsonArray.put(json);
//                
//                json = new JSONObject();
//		json.put("date", "11/20/2014 12:15:10");               
//		json.put("current", "125"); 
//                json.put("mdv", "310");
//		jsonArray.put(json);
//                
//                json = new JSONObject();
//		json.put("date", "11/20/2014 12:25:10");               
//		json.put("current", "35"); 
//                json.put("mdv", "320");
//		jsonArray.put(json);
//                
//                json = new JSONObject();
//		json.put("date", "11/20/2014 12:30:10");               
//		json.put("current", "54"); 
//                json.put("mdv", "256");
//		jsonArray.put(json);
//                
//                json = new JSONObject();
//		json.put("date", "11/20/2014 12:36:10");               
//		json.put("current", "60"); 
//                json.put("mdv", "347");
//		jsonArray.put(json);
//                
//                System.out.println("-- " + jsonArray.toString());
//                out.println(jsonArray.toString());
    }
    
// <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
	    throws ServletException, IOException {
	try {
	    processRequest(request, response);
	} catch (SQLException ex) {
	    Logger.getLogger(DataAccessServlet.class.getName()).log(Level.SEVERE, null, ex);
	} catch (ParseException ex) {
            Logger.getLogger(DataAccessServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
	    throws ServletException, IOException {
	try {
	    processRequest(request, response);
	} catch (SQLException ex) {
	    Logger.getLogger(DataAccessServlet.class.getName()).log(Level.SEVERE, null, ex);
	} catch (ParseException ex) {
            Logger.getLogger(DataAccessServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
	return "Short description";
    }// </editor-fold>

}
