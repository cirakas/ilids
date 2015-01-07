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
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author cirakaspvt
 */
public class ChartDataServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException, ParseException {
        response.setContentType("application/json");
        String connectionUrl = ServerConfig.DB_TYPE + "//" + ServerConfig.DB_HOSTNAME + ":" + ServerConfig.DB_PORT + "/" + ServerConfig.DB_NAME;
        String dbUserName = ServerConfig.DB_USERNAME;
        String dbPassword = ServerConfig.DB_PASS;

        Connection connection = (Connection) DriverManager.getConnection(connectionUrl, dbUserName, dbPassword);
        Statement statement = (Statement) connection.createStatement();
        String start1 = request.getParameter("fromDates");
        String end1 = request.getParameter("toDates");
        String fromHours = request.getParameter("fromHourss");
        String fromMinutes = request.getParameter("fromMinutess");
        String toHours = request.getParameter("toHourss");
        String toMinutes = request.getParameter("toMinutess");
        String fromTime = fromHours + ":" + fromMinutes + ":00";
        String toTime = toHours + ":" + toMinutes + ":59";

        String deviceId = request.getParameter("deviceIds");

        String dateFormat = "MM/dd/yyyy";
        String toDateFormat = "yyyy-MM-dd";
        SimpleDateFormat parsePattern = new SimpleDateFormat(dateFormat);
        SimpleDateFormat parseFormat = new SimpleDateFormat(toDateFormat);
        String start = parseFormat.format(parsePattern.parse(start1));
        String end = parseFormat.format(parsePattern.parse(end1));

        //String selectQuery = "SELECT time as data_time , data as real_data FROM data_3m_1 WHERE `time` BETWEEN '"+start+" "+fromTime+"' AND '"+end+" "+toTime+"'  and address_map="+addressMap+""+deviceCondition+" ORDER BY time ";
        String selectQuery1
                = "SELECT device_id, address_map, data, YEAR(`time`) as year, MONTH(`time`) as month, "
                + "DAY(`time`) as day, HOUR(`time`) as hour, MINUTE(`time`) as minute, "
                + "SECOND(`time`) as second FROM `data_3m_1` "
                + "WHERE TIME BETWEEN '" + start + " " + fromTime + "' AND '" + end + " " + toTime + "' "
                + "AND device_id = " + deviceId + " AND address_map = 6 ORDER BY time";

        String selectQuery2
                = "SELECT device_id, address_map, data, YEAR(`time`) as year, MONTH(`time`) as month, "
                + "DAY(`time`) as day, HOUR(`time`) as hour, MINUTE(`time`) as minute, "
                + "SECOND(`time`) as second FROM `data_3m_1` "
                + "WHERE TIME BETWEEN '" + start + " " + fromTime + "' AND '" + end + " " + toTime + "' "
                + "AND device_id = " + deviceId + " AND address_map = 8 ORDER BY time";

        String selectQuery3
                = "SELECT device_id, address_map, data, YEAR(`time`) as year, MONTH(`time`) as month, "
                + "DAY(`time`) as day, HOUR(`time`) as hour, MINUTE(`time`) as minute, "
                + "SECOND(`time`) as second FROM `data_3m_1` "
                + "WHERE TIME BETWEEN '" + start + " " + fromTime + "' AND '" + end + " " + toTime + "' "
                + "AND device_id = " + deviceId + " AND address_map = 10 ORDER BY time";

        PrintWriter out = response.getWriter();

        JSONArray jsonArray1 = new JSONArray();
        JSONArray jsonArray2 = new JSONArray();
        JSONArray jsonArray3 = new JSONArray();
        JSONArray jsonMainArray1 = new JSONArray();
        JSONArray jsonMainArray2 = new JSONArray();
        JSONArray jsonMainArray3 = new JSONArray();
        JSONObject json = new JSONObject();
        ResultSet rs1 = null;
        ResultSet rs2 = null;
        ResultSet rs3 = null;
        try {
            /* TODO output your page here. You may use following sample code. */
            // String pattern = "MM/dd/yyyy HH:mm:ss";
            DecimalFormat df = new DecimalFormat("#.##");
            // SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
            rs1 = statement.executeQuery(selectQuery1);
            while (rs1.next()) {
                JSONObject js1 = new JSONObject();
                js1.put("year", rs1.getInt("year"));
                js1.put("month", rs1.getInt("month"));
                js1.put("day", rs1.getInt("day"));
                js1.put("hour", rs1.getInt("hour"));
                js1.put("minute", rs1.getInt("minute"));
                js1.put("second", rs1.getInt("second"));
                js1.put("value1", rs1.getFloat("data"));
//                jsonArray1 = new JSONArray();
//                jsonArray1.put(js1);
                //jsonArray1.put(0, rs1.getLong("time"));
                //jsonArray1.put(0, "Date.UTC(2014,11,15,00,03,26,00)");                
                //jsonArray1.put(1, rs1.getFloat("data"));
                jsonMainArray1.put(js1);

            }
            json.put("data1", jsonMainArray1);
            rs2 = statement.executeQuery(selectQuery2);
            while (rs2.next()) {
                JSONObject js2 = new JSONObject();
                //js2.put("date2" , rs2.getLong("time"));
                js2.put("year", rs2.getInt("year"));
                js2.put("month", rs2.getInt("month"));
                js2.put("day", rs2.getInt("day"));
                js2.put("hour", rs2.getInt("hour"));
                js2.put("minute", rs2.getInt("minute"));
                js2.put("second", rs2.getInt("second"));
                js2.put("value2", rs2.getFloat("data"));
//                jsonArray2 = new JSONArray();
//                jsonArray2.put(js2);
                // jsonArray2.put(0, rs2.getLong("time"));
                //jsonArray2.put(0, "Date.UTC(2014,11,15,00,03,26,00)"); 
                //jsonArray2.put(1, rs2.getFloat("data"));
                jsonMainArray2.put(js2);

            }
            json.put("data2", jsonMainArray2);
            rs3 = statement.executeQuery(selectQuery3);
            while (rs3.next()) {
                JSONObject js3 = new JSONObject();
                // js3.put("date3" , rs3.getLong("time"));

                js3.put("year", rs3.getInt("year"));
                js3.put("month", rs3.getInt("month"));
                js3.put("day", rs3.getInt("day"));
                js3.put("hour", rs3.getInt("hour"));
                js3.put("minute", rs3.getInt("minute"));
                js3.put("second", rs3.getInt("second"));
                js3.put("value3", rs3.getFloat("data"));
//                jsonArray3 = new JSONArray();
//                jsonArray3.put(js3);
                //jsonArray3.put(0, rs3.getLong("time"));
                //sonArray3.put(1, rs3.getFloat("data"));
                jsonMainArray3.put(js3);

            }
            json.put("data3", jsonMainArray3);
            out.println(json.toString());

        } catch (SQLException sqe) {
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            out.close();
            if (rs1 != null) {
                rs1.close();
            }
            if (rs2 != null) {
                rs1.close();
            }
            if (rs3 != null) {
                rs1.close();
            }

            if (connection != null) {
                connection.close();
            }

        }
        // PrintWriter out = response.getWriter();
//        JSONArray jsonArray = new JSONArray();
//        JSONObject json = new JSONObject();
//		json.put("date", "11/20/2014 12:10:10");               
//		json.put("current", "50");
//                json.put("current2", "75");
//                json.put("current3", "35");
//                json.put("mdv", "260");
//                jsonArray.put(json);
//                
//                json = new JSONObject();
//		json.put("date", "11/20/2014 12:15:10");               
//		json.put("current", "125");
//                json.put("current2", "50");
//                json.put("current3", "100");
//                json.put("mdv", "310");
//		jsonArray.put(json);
//                
//                json = new JSONObject();
//		json.put("date", "11/20/2014 12:25:10");               
//		json.put("current", "35"); 
//                json.put("current2", "140");
//                json.put("current3", "112");
//                json.put("mdv", "320");
//		jsonArray.put(json);
//                
//                json = new JSONObject();
//		json.put("date", "11/20/2014 12:30:10");               
//		json.put("current", "54");
//                json.put("current2", "125");
//                json.put("current3", "79");
//                json.put("mdv", "256");
//		jsonArray.put(json);
//                
//                json = new JSONObject();
//		json.put("date", "11/20/2014 12:36:10");               
//		json.put("current", "140"); 
//                json.put("current2", "50");
//                json.put("current3", "101");
//                json.put("mdv", "347");
//		jsonArray.put(json);
//                
//                System.out.println("-- " + jsonArray.toString());
//                out.println(jsonArray.toString());
//       JSONObject json = new JSONObject(); 
//       JSONArray jsonMainArray = new JSONArray();
//        JSONArray jsonArray = new JSONArray();     
//        jsonArray.put(0, System.currentTimeMillis() + new Random().nextInt(999999));
//        jsonArray.put(1, 140);        
//        jsonMainArray.put(jsonArray);
//        
//        jsonArray = new JSONArray();
//        jsonArray.put(0, System.currentTimeMillis() + new Random().nextInt(999999));
//        jsonArray.put(1, 145);        
//        jsonMainArray.put(jsonArray);
//        
//        jsonArray = new JSONArray();
//        jsonArray.put(0, System.currentTimeMillis() + new Random().nextInt(999999));
//        jsonArray.put(1, 149);        
//        jsonMainArray.put(jsonArray);
//        
//        jsonArray = new JSONArray();
//        jsonArray.put(0, System.currentTimeMillis() + new Random().nextInt(999999));
//        jsonArray.put(1, 115);        
//        jsonMainArray.put(jsonArray);
//        
//        json.put("data1", jsonMainArray);
//        
//        
//        JSONArray jsonMainArray1 = new JSONArray();
//        JSONArray jsonArray1 = new JSONArray();
//        
//        jsonArray1.put(0, System.currentTimeMillis() + new Random().nextInt(999999));
//        jsonArray1.put(1, 150);        
//        jsonMainArray1.put(jsonArray1);
//        
//        jsonArray1 = new JSONArray();
//        jsonArray1.put(0, System.currentTimeMillis() + new Random().nextInt(999999));
//        jsonArray1.put(1, 125);        
//        jsonMainArray1.put(jsonArray1);
//        
//        jsonArray1 = new JSONArray();
//        jsonArray1.put(0, System.currentTimeMillis() + new Random().nextInt(999999));
//        jsonArray1.put(1, 135);        
//        jsonMainArray1.put(jsonArray1);
//        
//        jsonArray1 = new JSONArray();
//        jsonArray1.put(0, System.currentTimeMillis() + new Random().nextInt(999999));
//        jsonArray1.put(1, 65);        
//        jsonMainArray1.put(jsonArray1);
//        
//        json.put("data2", jsonMainArray1);
//        
//        JSONArray jsonMainArray2 = new JSONArray();
//        JSONArray jsonArray2 = new JSONArray();        
//        jsonArray2.put(0, System.currentTimeMillis() + new Random().nextInt(999999));
//        jsonArray2.put(1, 66);        
//        jsonMainArray2.put(jsonArray2);
//        
//        jsonArray2 = new JSONArray();
//        jsonArray2.put(0, System.currentTimeMillis() + new Random().nextInt(999999));
//        jsonArray2.put(1, 45);        
//        jsonMainArray2.put(jsonArray2);
//        
//        jsonArray2 = new JSONArray();
//        jsonArray2.put(0, System.currentTimeMillis() + new Random().nextInt(999999));
//        jsonArray2.put(1, 123);        
//        jsonMainArray2.put(jsonArray2);
//        
//        jsonArray2 = new JSONArray();
//        jsonArray2.put(0, System.currentTimeMillis() + new Random().nextInt(999999));
//        jsonArray2.put(1, 118);        
//        jsonMainArray2.put(jsonArray2);
//        
//        
//        
//        json.put("data3", jsonMainArray2);
//        
//        
//        System.out.println("-- " + json.toString());
//        out.println(json.toString());
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
            Logger.getLogger(ChartDataServlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParseException ex) {
            Logger.getLogger(ChartDataServlet.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(ChartDataServlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParseException ex) {
            Logger.getLogger(ChartDataServlet.class.getName()).log(Level.SEVERE, null, ex);
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
