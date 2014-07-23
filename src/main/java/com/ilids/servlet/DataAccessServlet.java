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
import java.math.BigDecimal;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
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
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
	 throws ServletException, IOException, SQLException, ParseException {
	response.setContentType("application/json");
	String connectionUrl=ServerConfig.DB_TYPE+"//"+ServerConfig.DB_HOSTNAME+":"+ServerConfig.DB_PORT+"/"+ServerConfig.DB_NAME;
	String dbUserName = ServerConfig.DB_USERNAME;
	String dbPassword = ServerConfig.DB_PASS;
	Connection connection = (Connection) DriverManager.getConnection(connectionUrl, dbUserName, dbPassword);
	Statement statement = (Statement) connection.createStatement();
        String phase=request.getParameter("phase");
        Long addressMap=Long.valueOf(phase);
        int addrMap = Integer.valueOf(phase);
        float diffCheck=0;
        String start=request.getParameter("fromDate");
        String end=request.getParameter("toDate");
        String dateFormat="MM/dd/yyyy";
        String toDateFormat="yyyy-MM-dd";
        SimpleDateFormat parsePattern = new SimpleDateFormat(dateFormat);
        SimpleDateFormat parseFormat = new SimpleDateFormat(toDateFormat);
        start=parseFormat.format(parsePattern.parse(start));
        end=parseFormat.format(parsePattern.parse(end));
	String selectQuery = "SELECT time as data_time , data as real_data FROM data WHERE `time` BETWEEN '"+start+"  00:00:01' AND '"+end+"   23:59:59'  and address_map="+addressMap;
        ResultSet rs = statement.executeQuery(selectQuery);
       
	PrintWriter out = response.getWriter();
        
            switch(addrMap){
            case 0:
              diffCheck=5;
              break;
            case 2:
              diffCheck=5;
              break;
           case 4:
              diffCheck=5;
              break;     
           case 6:
              diffCheck=20;
              break;    
           case 8:
              diffCheck=20;
              break;
           case 10:
              diffCheck=20;
              break;        
           case 12:
              diffCheck=7;
              break;
           case 14:
              diffCheck=7;
              break;    
           case 16:
              diffCheck=7;
              break;  
           case 30:
              diffCheck=(float) 0.1;
              break;  
           case 32:
              diffCheck=(float) 0.1;
              break;  
           case 34:
              diffCheck=(float) 0.1;
              break;      
           case 512:
              diffCheck=10;
              break;  
           case 514:
              diffCheck=10;
              break;      
           default:
              diffCheck=7;
          }

	JSONArray jsonArray = new JSONArray();
	try {
	    /* TODO output your page here. You may use following sample code. */
	    float datas = 1;
            float datas1=1;
	    String realDate = "";
            float predata=0;
            float predat=0;
	    String pattern = "MM/dd/yyyy HH:mm:ss";
	    SimpleDateFormat format = new SimpleDateFormat(pattern);
	    while (rs.next()) {
		datas = rs.getFloat("real_data");
                float difference= Math.abs(datas-predata);
                if(difference<0){
                   difference=difference*(-1); 
                }
                if((difference<=diffCheck) &&(Float.floatToIntBits (predata)!=0)){
                    datas=predata;
                }
                predata=datas;
		realDate = format.format(rs.getTimestamp("data_time"));
		JSONObject json = new JSONObject();
		json.put("date", realDate);
		json.put("current", datas);
		json.put("power", datas);
		json.put("voltage", datas);
		jsonArray.put(json);
	    }
	    out.println(jsonArray.toString());
	    if (rs != null) {
		rs.close();
	    }
	    if (connection != null) {
		connection.close();
	    }
	} catch (Exception e) {
	    e.printStackTrace();
	} finally {
	    out.close();
	    if (connection != null) {
		connection.close();
	    }
	    if (rs != null) {
		rs.close();
	    }
	}
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
