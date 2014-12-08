<%@page import="org.springframework.security.core.userdetails.User"%>
<%@page import="org.springframework.security.core.context.SecurityContextHolder"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<script type="text/javascript">

(function() {
        var ga = document.createElement('script');
        ga.type = 'text/javascript';
        ga.async = true;
        ga.src = ('https:' == document.location.protocol ? 'https://ssl' : 'http://www') + '.google-analytics.com/ga.js';
        var s = document.getElementsByTagName('script')[0];
        s.parentNode.insertBefore(ga, s);
    })();
    
    var fromDateParam = readCookie('start');
    var toDateParam = readCookie('end');
    var fromHoursParam = readCookie('frHours');
    var fromMinutesParam = readCookie('frMinutes');
    var toHoursParam = readCookie('tHours');
    var toMinutesParam = readCookie('tMinutes');
    var deviceParam = readCookie('deviceId');
    
    if (!fromDateParam) {
        fromDateParam = new Date().toLocaleDateString();//"7/20/2014"
    }
    if (!toDateParam) {
        toDateParam = new Date().toLocaleDateString();//"7/20/2014"
    }
    if (!fromHoursParam) {
        fromHoursParam = "00";
    }
    if (!fromMinutesParam) {
        fromMinutesParam = "00";
    }
    if (!toHoursParam) {
        toHoursParam = "23";
    }
    if (!toMinutesParam) {
        toMinutesParam = "59";
    }
    if (!deviceParam) {
        deviceParam = '0';
    }

    var servlet = "DataAccessServlet?fromDate=" + fromDateParam + "&fromHours=" + fromHoursParam + "&fromMinutes=" + fromMinutesParam + "&toDate=" + toDateParam + "&toHours=" + toHoursParam + "&toMinutes=" + toMinutesParam + "&deviceId=" + deviceParam;
    
     function selectFunction()
    {
        var device = document.getElementById("deviceList").value;
        var fromDate = document.getElementById("SelectedDate").value;
        var toDate = document.getElementById("SelectedDate1").value;
        var fromHours = document.getElementById("from-hours").value;
        var fromMinutes = document.getElementById("from-minutes").value;
        var toHours = document.getElementById("to-hours").value;
        var toMinutes = document.getElementById("to-minutes").value;
        var toDate = document.getElementById("SelectedDate1").value;
        var d1 = new Date(fromDate + " " + fromHours + ":" + fromMinutes + ":00");
        var d2 = new Date(toDate + " " + toHours + ":" + toMinutes + ":59");
        if (d2 < d1) {
            alert("end date should not be less than start date");
        }
        else {
            document.cookie = "start=" + fromDate + " " + "end=" + toDate + " " + "frHours=" + fromHours + " " + "frMinutes=" + fromMinutes + " " + "tHours=" + toHours + " " + "tMinutes=" + toMinutes + " " + "deviceId=" + device + " ";
            document.cookie = "cname=; expires=Thu, 01 Jan 1970 00:00:00 GMT";
            var myURL = window.location.protocol + "//" + window.location.host + window.location.pathname;
            document.location = myURL + "?fromDate=" + fromDate + "&fromHours=" + fromHours + "&fromMinutes=" + fromMinutes + "&toDate=" + toDate + "&toHours=" + toHours + "&toMinutes=" + toMinutes + "&deviceId=" + device;
        }
    }
    
    function getQueryVariable(variable)
    {
        var query = window.location.search.substring(1);
        var vars = query.split("&");
        for (var i = 0; i < vars.length; i++) {
            var pair = vars[i].split("=");
            if (pair[0] == variable) {
                return pair[1];
            }
        }
    }

    function readCookie(cname) {
        var name = cname + "=";
        var doc = document.cookie.split(";");
        var ca = doc[0].split(" ");
        for (var i = 0; i < ca.length; i++) {
            var c = ca[i];
            while (c.charAt(0) === ' ')
                c = c.substring(1);
            if (c.indexOf(name) !== -1)
                return c.substring(name.length, c.length);
        }
        return c;
    }
    
    
 </script>   
 
 <link rel="stylesheet" href="${resources}ilids-d3/css/jquery-ui.css">
<!--<link rel="stylesheet" href="${resources}ilids-d3/css/style.css">-->
<script src="${resources}ilids-d3/js/jquery-1.10.2.js"></script>
<script>
    var jq = jQuery.noConflict();
</script>
<script type="text/javascript" src="${resources}ilids-d3/js/jquery-ui.js"></script>
 
 
    <div class="row row_date_bg" style="">
        
    <div class="col-lg-3 drop_down_bg" style="padding: 0;float: left;">
        <div class="form-group form-group1" style="float: left;" >               
            <form:form method="post" modelAttribute="deviceModel">                         
                <form:select cssClass="form-control" multiple="single" id="deviceList"  path="id"  onchange="selectFunction();">
                    <%--<form:option  label="SELECT DEVICE" value="00" />--%>
                    <form:options items="${deviceIdList}" itemLabel="name" itemValue="slaveId" />
                </form:select>                        
                <%--<form:select path="id" items="${deviceIdList}" itemLabel="name" itemValue="slaveId" multiple="false" id="deviceList" onchange="selectFunction()"/>--%>
            </form:form>
        </div>
    </div> 
        
 <div class=" from_to_bg">
        
        <div class="select_bg_11" style="">
            <label style="color:#5c5b5b;float: left;margin-top: 9px;">FROM</label>
            <div class="input_min_bg" style="float: left;">
                <!--<input type="button" name="SelectedDate" class=" input_" id="SelectedDate" readonly onClick="GetDate(this);" value="" />-->      
                <input type="button" class=" input_"  name="SelectedDate" id="SelectedDate" readonly value="">
                <div class="min_bg">
                    <select class="form-control" id="from-hours" value="" style="height:32px;padding:3px;">
                        <option value="00">00</option>
                        <option value="01">01</option>
                        <option value="02">02</option>
                        <option value="03">03</option>
                        <option value="04">04</option>
                        <option value="05">05</option>
                        <option value="06">06</option>
                        <option value="07">07</option>
                        <option value="08">08</option>
                        <option value="09">09</option>
                        <option value="10">10</option>
                        <option value="11">11</option>
                        <option value="12">12</option>
                        <option value="13">13</option>
                        <option value="14">14</option>
                        <option value="15">15</option>
                        <option value="16">16</option>
                        <option value="17">17</option>
                        <option value="18">18</option>
                        <option value="19">19</option>
                        <option value="20">20</option>
                        <option value="21">21</option>
                        <option value="22">22</option>
                        <option value="23">23</option>
                    </select>
                </div>
                <div class="min_bg">
                    <select class="form-control" id="from-minutes" value="" style="height:32px;padding:3px;">
                        <option value="00">00</option>
                        <option value="10">10</option>
                        <option value="20">20</option>
                        <option value="30">30</option>
                        <option value="40">40</option>
                        <option value="50">50</option>
                        <option value="59">59</option>
                    </select>
                </div>
            </div>
        </div>


        <div class="select_bg_12">
            <label class="to_" style="color:#5c5b5b;float: left;margin-top: 9px;">TO</label>
            <div  class="input_min_bg input_11" >
                <!--<input type="button" name="SelectedDate" class="input_" id="SelectedDate1" readonly onClick="GetDate(this);" value=""  />-->                 
               <input type="button" class=" input_" name="SelectedDate" id="SelectedDate1" readonly value="">
                <div class="min_bg">
                    <select class="form-control" id="to-hours" value="" style="height:32px;padding:3px;">
                        <option value="00">00</option>
                        <option value="01">01</option>
                        <option value="02">02</option>
                        <option value="03">03</option>
                        <option value="04">04</option>
                        <option value="05">05</option>
                        <option value="06">06</option>
                        <option value="07">07</option>
                        <option value="08">08</option>
                        <option value="09">09</option>
                        <option value="10">10</option>
                        <option value="11">11</option>
                        <option value="12">12</option>
                        <option value="13">13</option>
                        <option value="14">14</option>
                        <option value="15">15</option>
                        <option value="16">16</option>
                        <option value="17">17</option>
                        <option value="18">18</option>
                        <option value="19">19</option>
                        <option value="20">20</option>
                        <option value="21">21</option>
                        <option value="22">22</option>
                        <option value="23">23</option>
                    </select>
                </div>
                <div class="min_bg">
                    <select class="form-control" id="to-minutes" value=""  style="height:32px;padding:3px; ">
                        <option value="00">00</option>
                        <option value="10">10</option>
                        <option value="20">20</option>
                        <option value="30">30</option>
                        <option value="40">40</option>
                        <option value="50">50</option>
                        <option value="59">59</option>
                    </select>
                </div>
            </div>
        </div>
        
        <div class="proceed_bg" style=""> 
            <input type="submit" class="proceed_" value="Proceed" onClick="selectFunction()"/>
        </div> 
        </div>       
        
     
 </div>


<tbody>
<script type="text/javascript" src="${resources}ilids-d3/js/d3.js" charset="utf-8"></script>
<script type="text/javascript" src="${resources}ilids-d3/multipleLine.js"></script>

<script type="text/javascript">                                                           
    document.getElementById("SelectedDate").value = fromDateParam;
    document.getElementById("SelectedDate1").value = toDateParam;
    document.getElementById("from-hours").value = fromHoursParam;
    document.getElementById("from-minutes").value = fromMinutesParam;
    document.getElementById("to-hours").value = toHoursParam;
    document.getElementById("to-minutes").value = toMinutesParam;
    var index = 0;

    if (deviceParam != 0) {
        var deviceArray = document.getElementById('deviceList').options;
        for (var i = 0; i < deviceArray.length; i++) {
            if (deviceArray[i].value === deviceParam) {
                break;
            }
            index++;
        }
    }
    document.getElementById("deviceList").selectedIndex = index;
    index = 0;
    
    jq(function() {
        jq( "#SelectedDate" ).datepicker({
            onSelect: function(selectedDate) {                
                jq( "#SelectedDate1" ).datepicker("option", "minDate", selectedDate);
            }
        });
        jq( "#SelectedDate1" ).datepicker({minDate: fromDateParam});
  });
</script>