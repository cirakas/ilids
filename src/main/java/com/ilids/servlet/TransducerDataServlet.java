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
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONArray;
import org.json.JSONObject;
import javax.servlet.http.Cookie;
/**
 *
 * @author user
 */
public class TransducerDataServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException, ParseException {
        response.setContentType("application/json");
        String connectionUrl = ServerConfig.DB_TYPE + "//" + ServerConfig.DB_HOSTNAME + ":" + ServerConfig.DB_PORT + "/" + ServerConfig.DB_NAME;
        String dbUserName = ServerConfig.DB_USERNAME;
        String dbPassword = ServerConfig.DB_PASS;
        Connection connection = (Connection) DriverManager.getConnection(connectionUrl, dbUserName, dbPassword);
        if(connection != null){
            System.out.println("---Connection Successful----");
        }
        Statement statement = (Statement) connection.createStatement();
        String phase = request.getParameter("phase");
        int addressMap = Integer.parseInt(phase);
        String start1 = request.getParameter("fromDate");
        String end1 = request.getParameter("toDate");
        String fromHours = request.getParameter("fromHours");
        String fromMinutes = request.getParameter("fromMinutes");
        String toHours = request.getParameter("toHours");
        String toMinutes = request.getParameter("toMinutes");
        String fromTime = fromHours + ":" + fromMinutes + ":00";
        String toTime = toHours + ":" + toMinutes + ":59";
        //String deviceId=request.getParameter("deviceId");
        String dateFormat = "MM/dd/yyyy";
        String toDateFormat = "yyyy-MM-dd";
        SimpleDateFormat parsePattern = new SimpleDateFormat(dateFormat);
        SimpleDateFormat parseFormat = new SimpleDateFormat(toDateFormat);
        String start = parseFormat.format(parsePattern.parse(start1));
        String end = parseFormat.format(parsePattern.parse(end1));
        
        if(start1!= null || phase!= null){
            
        Cookie tamapCook = new Cookie("tamap",phase);
        tamapCook.setMaxAge(60*60*24); //1 hour
        response.addCookie(tamapCook);
        
        Cookie tfrDateCook = new Cookie("tfDate",start1);
        tfrDateCook.setMaxAge(60*60); //1 hour
        response.addCookie(tfrDateCook);
        
        Cookie tfrHrCook = new Cookie("tfHour",fromHours);
        tfrHrCook.setMaxAge(60*60);
        response.addCookie(tfrHrCook);
        
        Cookie tfrMinCook = new Cookie("tfMin",fromMinutes);
        tfrMinCook.setMaxAge(60*60); //1 hour
        response.addCookie(tfrMinCook);
        
        Cookie ttoDateCook = new Cookie("ttoDt",end1);
        ttoDateCook.setMaxAge(60*60);
        response.addCookie(ttoDateCook);
        
        Cookie ttoHrCook = new Cookie("ttoHr",toHours);
        ttoHrCook.setMaxAge(60*60); //1 hour
        response.addCookie(ttoHrCook);
        
        Cookie ttoMinCook = new Cookie("ttoMin",toMinutes);
        ttoMinCook.setMaxAge(60*60);
        response.addCookie(ttoMinCook);
            
        
        }
        
        
        String selQuery = "SELECT `data`, `time` FROM `trans_data` WHERE "
                + "`time` BETWEEN '" + start + " " + fromTime + "' AND '" + end + " " + toTime + "' "
                + "AND `address_map` = " + addressMap + " AND device_id = 0";
        ResultSet rs = statement.executeQuery(selQuery);
	PrintWriter out = response.getWriter();
        JSONArray jsonArray = new JSONArray();
        
        try{
         Date selDate = null;   
         DecimalFormat df = new DecimalFormat("#.##");
	 SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
         
         while(rs.next()){
             selDate = rs.getTimestamp("time");
         JSONObject json = new JSONObject();
		json.put("date", format.format(selDate));
                json.put("temp", df.format(rs.getFloat("data")));
                jsonArray.put(json);
         }
         
         out.println(jsonArray.toString());
         
        }catch(Exception e) {
	    e.printStackTrace();
	}finally {
	    out.close();
            if (rs != null) {
		rs.close();
	    }
	    if (connection != null) {
		connection.close();
	    } 
            
	}
        
    }

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
