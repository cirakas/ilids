<%@page import="org.springframework.security.core.userdetails.User"%>
<%@page import="org.springframework.security.core.context.SecurityContextHolder"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<script type="text/javascript">
    
//    var _gaq = _gaq || [];
//    _gaq.push(['_setAccount', 'UA-33628816-1']);
//    _gaq.push(['_trackPageview']);
//
//    (function () {
//        var ga = document.createElement('script');
//        ga.type = 'text/javascript';
//        ga.async = true;
//        ga.src = ('https:' == document.location.protocol ? 'https://ssl' : 'http://www') + '.google-analytics.com/ga.js';
//        var s = document.getElementsByTagName('script')[0];
//        s.parentNode.insertBefore(ga, s);
//    })();

    (function() {
        var ga = document.createElement('script');
        ga.type = 'text/javascript';
        ga.async = true;
        ga.src = ('https:' == document.location.protocol ? 'https://ssl' : 'http://www') + '.google-analytics.com/ga.js';
        var s = document.getElementsByTagName('script')[0];
        s.parentNode.insertBefore(ga, s);
    })();

    var mdvValue1 = '<c:out value="${SystemSettings.mdv}"/>';

    var phaseParam = readCookie('phase');
    var fromDateParam = readCookie('start');
    var toDateParam = readCookie('end');
    var fromHoursParam = readCookie('frHours');
    var fromMinutesParam = readCookie('frMinutes');
    var toHoursParam = readCookie('tHours');
    var toMinutesParam = readCookie('tMinutes');
    var deviceParam = readCookie('deviceId');

    if (!phaseParam) {
        phaseParam = '06';
    }
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

    var servlet = "DataAccessServlet?phase=" + phaseParam + "&fromDate=" + fromDateParam + "&fromHours=" + fromHoursParam + "&fromMinutes=" + fromMinutesParam + "&toDate=" + toDateParam + "&toHours=" + toHoursParam + "&toMinutes=" + toMinutesParam + "&deviceId=" + deviceParam;

    switch (phaseParam) {
        case '00':
//        headTitle="Phase1 Voltage vs Time";
            yaxisTitle = "Phase1 Voltage";
            break;

        case '02':
//        headTitle="Phase2 Voltage vs Time";
            yaxisTitle = "Phase2 Voltage";
            break;

        case '04':
//        headTitle="Phase3 Voltage vs Time";
            yaxisTitle = "Phase3 Voltage";
            break;

        case '06':
//        headTitle="Phase1 Current vs Time";
            yaxisTitle = "Phase1 Current";
            break;
        case '08':
//        headTitle="Phase2 Current vs Time";
            yaxisTitle = "Phase2 Current";
            break;
        case '10':
//        headTitle="Phase3 Current vs Time";
            yaxisTitle = "Phase3 Current";
            break;
        case '12':
//        headTitle="Phase1 Power vs Time";
            yaxisTitle = "Phase1 Power";
            break;
        case '14':
//        headTitle="Phase2 Power vs Time";
            yaxisTitle = "Phase2 Power";
            break;
        case '16':
//        headTitle="Phase3 Power vs Time";
            yaxisTitle = "Phase3 Power";
            break;
        case '30':
            headTitle = "Phase1 Power Factor vs Time";
            yaxisTitle = "Phase1 Power Factor";
            break;
        case '32':
            headTitle = "Phase2 Power Factor vs Time";
            yaxisTitle = "Phase2 Power Factor";
            break;
        case '34':
            headTitle = "Phase3 Power Factor vs Time";
            yaxisTitle = "Phase3 Power Factor";
            break;
        case '512':
            headTitle = "Cumulative Energy kVAh vs Time";
            yaxisTitle = "Cumulative Energy kVAh";
            break;
        case '514':
            headTitle = "Cumulative Energy kWh vs Time";
            yaxisTitle = "Cumulative Energy kWh";
            break;
        default :
            headTitle = "Phase1 Current vs Time";
            yaxisTitle = "Phase1 Current";
    }




    function selectFunction()
    {
        var graphType = document.getElementById("graphType").value;
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
            document.cookie = "phase=" + graphType + " " + "start=" + fromDate + " " + "end=" + toDate + " " + "frHours=" + fromHours + " " + "frMinutes=" + fromMinutes + " " + "tHours=" + toHours + " " + "tMinutes=" + toMinutes + " " + "deviceId=" + device + " ";
            document.cookie = "cname=; expires=Thu, 01 Jan 1970 00:00:00 GMT";
            var myURL = window.location.protocol + "//" + window.location.host + window.location.pathname;
            document.location = myURL + "?phase=" + graphType + "&fromDate=" + fromDate + "&fromHours=" + fromHours + "&fromMinutes=" + fromMinutes + "&toDate=" + toDate + "&toHours=" + toHours + "&toMinutes=" + toMinutes + "&deviceId=" + device;
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



<script language="JavaScript" src="${resources}ilids-template/js/htmlDatePicker.js" type="text/javascript"></script>
<link href="${resources}ilids-template/css/htmlDatePicker.css" rel="stylesheet" />
<link rel="stylesheet" href="${resources}ilids-template/css/jquery.amaran.min.css">
<link rel="stylesheet" href="${resources}ilids-template/css/all-themes.css">
<script language="JavaScript" src="${resources}ilids-template/js/polling.js" type="text/javascript"></script>
<script language="JavaScript" src="${resources}ilids-template/js/notification.js" type="text/javascript"></script>  

<link rel="stylesheet" href="${resources}ilids-d3/css/jquery-ui.css">
<!--<link rel="stylesheet" href="${resources}ilids-d3/css/style.css">-->
<script src="${resources}ilids-d3/js/jquery-1.10.2.js"></script>
<script>
    var jq = jQuery.noConflict();
</script>
<script type="text/javascript" src="${resources}ilids-d3/js/jquery-ui.js"></script>


<script>

    function generateNotification(content) {
        if (content && content !== 'undefined') {
            if (content !== null) {
                if (Notification.permission !== 'granted') {
                    Notification.requestPermission();
                }
                n = new Notification("Hello", {
                    body: content,
                    style: 'color:#F5A9A9;',
                    icon: "star.ico"
                });
                n.close();
            }
        }
    }
</script>                                                              

<style>    
    body {
        font: 14px sans-serif;
        outline: none!important;
    }

    .axis path,
    .axis line {
        fill: none;
        stroke: #000;  
        shape-rendering: crispEdges;
    }    

    .line {
        stroke: #F2700D;
        fill: none;
        stroke-width: 3;
    }

    .line.line1 {
        stroke:#0D98B3;
        stroke-width: 4;
    }

    .line.line0 {
        stroke:#F2700D ;
        stroke-width: 3;
    }

    .line.line3 {
        stroke:#FFCC00 ;
        stroke-width: 4;
    }

    .svg line{
        fill: none;  
    }
    .svg line.x{
        stroke: #FF0000;   
        stroke-width: 4;    
    }

    .svg line{
        stroke: #FF0000;
        stroke-width: 4; 
    }

    .overlay {
        fill: none;
        pointer-events: all;
    }

    .focus circle {
        fill: #fff;
    }

    .focus circle.y0 {
        stroke: #F2700D;
        stroke-width: 2;
    }

    .focus circle.y1 {
        stroke: red;
    }

    .focus line {
        stroke: purple;
        shape-rendering: crispEdges;
    }

    .focus line.y0 {
        stroke: #015c85;
        stroke-dasharray: 10 7;
        stroke-width: 2;
        opacity: .8;
    }

    .focus line.y1 {
        stroke: indianred;
        stroke-dasharray: 3 3;
        opacity: .5;
    }
    .area {
        fill: lightsteelblue;
        stroke-width: 0;
        line-height:50%;
        fill: #97defd;
        opacity: 1;
    }

    .brush .extent {
        stroke: #ff0;
        fill-opacity: .125;
        shape-rendering: crispEdges;
    } 
    rect.pane {
        cursor: move;
        fill: none;
        pointer-events: all;
    }

    .axis path {
        fill: none;
        stroke: #015c85;
        shape-rendering: crispEdges;
        stroke-width: 3;
        line-height:500%;
    }

    .grid .tick {
        stroke: lightgrey;
        opacity: 0.7;
    }
    .grid path {
        stroke-width: 0;
    }
    .heading_top_{   
        fill:#e02222;
        font-size: 17px;
        font-family:  sans-serif;
        font-weight: bold;
    }




    .power-factor{
        /*    height: 20px;*/
        padding-top: 0px;
        width: 220px;
        padding: 5px 0;
        text-align: center;
/*        color: #fff;*/
    }
    .power-factor-panel{
        margin-left: 4px;
        float: left;
    }
    .text-announce{
        background: #14a164;color:#fff;width: 100%;padding: 10px 0 10px 5px;
        text-align: center;
        font-size: 14px;
        font-weight: bold;
        /*    border:1px solid #14a164;*/
        background-image: linear-gradient(#2bc2ba, #27beb6 60%, #1FB5AD);
/*        background:#1FB5AD;*/
        border-bottom: 2px solid #14a199;
        filter: none;
    }
    .announce_text_11{word-break:break-all;}
    .panel-heading1{padding: 0;}

    

    .graph_heading{
        /*   background-color: #f6b54e!important;*/
        border-color: #d98b12!important;
        /*   background: url(/ilids/resources/images/graph_head_bg.png);*/
        background: #4cbdbb!important;border: none;
        background: #3399eb!important;
        background-image: linear-gradient(#54B4EB, #2FA4E7 60%, #1D9CE5)!important;
        border-bottom: 1px solid #178ACC!important;
        filter: none;
    }
    #graph33 {
        width: 100%;
        height: 100%;
        position: absolute;
    }


    .power_factor_bg{padding: 9px 0;/*background: #ee6369;*/margin-left: 0;margin-bottom: 8px;width: 100%;float: left;border:none;border-radius:3px!important;
                     /*border-left: 3px solid #be7611;*/ background: #e6ad2d;
                     background-image: linear-gradient(#f98d49, #e0722c 70%, #c76120)!important;
                     border-bottom: 1px solid #bb581a;filter: none;
                     background: #fff6bf !important;
                     border: 1px solid #ffd840;
                     color: #9E7540;
                     box-shadow: 0px 0px 5px #fbee9c inset;
    }
    .success_{width: 100%;border:1px solid #14a164;border:none!important;float: left!important;background:#79d3ac;
                      box-shadow: 0px 0px 10px #e5e5e5;
                      
/*              background:#c5e4a3;*/
                background:#fff;
                
                
/*                background: url(/ilids/resources/images/right_bulb_bg.png);*/
    }
    
    .image-bg{border: 1px solid #dde0e0!important;border-radius: 4px 4px 0 0;border-bottom: none!important;box-shadow: 0px 0px 5px #dfdfdf inset;
              background-image: radial-gradient(#fff, #ebeded 50%, #fff);
    }
    
    
    .panel_1{
        -webkit-transition: all 0.2s ease-in-out; /* For Safari 3.1 to 6.0 */
        -moz-transition: all 0.2s ease-in-out;
        -o-transition: all 0.2s ease-in-out;
        transition: all 0.2s ease-in-out;
        margin-bottom: 0;height: auto;background: #fff;border-color: #f6b54e;border: none; 

        box-shadow:0px 0px 20px #d8d9d9;
        -webkit-box-shadow:0px 0px 20px #d8d9d9;
        -moz-box-shadow:0px 0px 20px #d8d9d9;
        -o-box-shadow:0px 0px 20px #d8d9d9;
        -ms-box-shadow:0px 0px 20px #d8d9d9;
    }
    .panel_1:hover{
        box-shadow:0px 0px 20px #d8d9d9;
        -webkit-box-shadow:0px 0px 20px #d8d9d9;
        -moz-box-shadow:0px 0px 20px #d8d9d9;
        -o-box-shadow:0px 0px 20px #d8d9d9;
        -ms-box-shadow:0px 0px 20px #d8d9d9;
    }

    /*.power_factor_main_bg{background: #eeeded;padding: 12px;border-radius:3px;}*/
    .proceed_bg{float: left;}
    .proceed_{padding:7px 10px;border-radius: 4px;background: #efba16;border:none; border-bottom: 2px solid #daa911;color:#fff; }
    .select_bg_11,.select_bg_12{float:left!important;}
    .from_to_bg{float: left;margin-top: 0px;margin-bottom: 0px;margin-bottom: 0;padding-left: 20px;padding-right: 0!important;}
    .input_min_bg{float: left!important;margin-left: 5px;width: 200px}
    .min_bg{float:left!important;width:auto;margin-top: 0px;margin-left: 5px;}

    .form-group1{float:left!important;margin-top: 0px;margin-bottom: 0px;width: 100%;}
    .form-group2{float: left!important;width: 100%!important;}
    .drop_down_bg{width:17%;margin: 0px;margin-bottom: 0;float: right;padding: 0;float: left;}
    .drop_down_bg1{width:19%;margin: 0px;margin-bottom: 0;float: right;padding: 0;float: left;margin-left: 15px;}

    .right_bg{width: 21%;float: right;/*padding: 12px 0 12px 15px;background: #f1f2f2;*/ margin-right: 15px;}
    .graph_bg{width:77.1%;padding-right: 0;float: left;/*background: #ececeb;*/padding:0 15px;border-radius: 5px;}
    .row_date_bg{background: #e8e9ea; margin: 5px 0 20px;padding: 10px 15px;background: url(/ilids/resources/images/bg_default1.jpg);}
    
    .power-factor-warning{padding: 9px 0;/*background: #ee6369;*/margin-left: 0;margin-bottom: 8px;width: 100%;float: left;border:none;border-radius:3px!important;
                     /*border-left: 3px solid #be7611;*/ background: #e6ad2d;color: #f00!important;
                     background-image: linear-gradient(#f98d49, #e0722c 70%, #c76120)!important;background: #fe9272 !important; border: 1px solid #e0623d;
                     filter: none;color:#9b3618!important;box-shadow: 0px 0px 5px #e46742 inset;}
    .power-factor-success{padding: 9px 0;/*background: #ee6369;*/margin-left: 0;margin-bottom: 8px;width: 100%;float: left;border:none;border-radius:3px!important;
                     /*border-left: 3px solid #be7611;*/ background: #e6ad2d;color: #f00!important;
                     background-image: linear-gradient(#f98d49, #e0722c 70%, #c76120)!important;background: #c7d789 !important; border: 1px solid #a1b650;
                     filter: none;color:#5e7506!important;box-shadow: 0px 0px 5px #a9bd5a inset;}
    .bulb_{margin: 0 auto;width: 130px;}


@media all and (max-width: 1280px) and (min-width: 1120px){
    .right_bg{float: right;width: 30%;padding:0px 16px;margin-right: 0;}
    .graph_bg{width:70%;}

        .drop_down_bg,.drop_down_bg1{width: 15.6%;}
        .from_to_bg{padding-left: 0px;margin-left:1%; float: left;}
        .form-group1,.form-group2{width:100%!important;float: left;}
        
        
        .input_min_bg{margin-left: 5px;float: left;width: auto;padding: 0!important;}
        .input_{width: auto;}
        
        .select_bg_11{float:left;}
        .select_bg_12{float:left;margin-left: 10px;}
        
        .proceed_bg{float: right;margin-left: 10px;}
        .input_{margin-left: 0;}
        
        .row_date_bg{padding-right:15px;padding-left:15px;}
        .bulb_{width: 115px;}
        
}

    @media all and (max-width: 1119px) and (min-width: 768px){
        .graph_bg{width:100%;}
        .right_bg{float: right;width: 100%;padding:0px 16px;margin-top: 20px;margin-right:0px;}
        
        .drop_down_bg,.drop_down_bg1{width: 48.3%;}
        .drop_down_bg1{float: right;}
        .from_to_bg{width: 100%;padding-left: 0px;margin-top:15px; float: left;}
        .proceed_bg{float: right;}.input_{width:40%;}
        .select_bg_11,.select_bg_12{float:left!important;width:43%;}    
    }
    @media all and (max-width: 899px) and (min-width: 768px){
        .select_bg_11,.select_bg_12{float:left!important;width:37%;}
        .select_bg_12{margin-left: 4%;}
        .proceed_bg{float: right;margin-top: 28px;}
    }
    
    @media all and (max-width: 767px) and (min-width: 150px){
        .graph_bg{width:100%;}
        .right_bg{float: right;width: 100%;padding:0px 16px;margin-top: 20px;margin-right:0px;}
        .row_date_bg{margin-top: 15px;}
        .drop_down_bg,.drop_down_bg1{width: 100%;}.drop_down_bg1{margin-left: 0;margin-top: 15px;}
        .from_to_bg{width: 100%;padding-left: 0px;margin-top:15px; float: left;}
        .proceed_bg{float: right;}
    }
    @media all and (max-width: 630px) and (min-width: 570px){
        .select_bg_11,.select_bg_12{width:39.5%;}
        .proceed_bg{float: right;margin-top: 30px;}
        .input_min_bg{margin-left: 0;}
    }
    @media all and (max-width: 569px) and (min-width: 510px){
        .select_main_bg{width: 100%;float: left;}
        .select_bg_11,.select_bg_12{width:44.5%;}
        .select_bg_12{float: right!important;}
        .input_min_bg{margin-left: 0;}
        .proceed_bg{float: right;margin-top: 15px;width: 100%;}
        .proceed_{width: 100% !important;}
    }
    @media all and (max-width: 509px) and (min-width: 150px){
        .select_bg_11,.select_bg_12{width:100%;}
        .select_bg_12{margin-top:15px; }
        .input_{width:40%;font-size: 13px;text-align: center;}
        .input_min_bg{margin-left: 5px;width:80%;}
        .input_11{margin-left: 30px!important;}
        .proceed_bg{float: left;margin-top: 15px;width: 100%;}
        .proceed_{width: 100% !important;}
    }
    @media all and (max-width: 330px) and (min-width: 150px){
        .input_min_bg{margin-left: 0px;width:100%;}
        .input_11{margin-left: 0px!important;}
        .bulb_{width: 110px;}
    }

</style> 



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


    <div class="col-lg-3 drop_down_bg1" style="">
        <div class="form-group form-group1 form-group2" style="">
            <!--                <label></label>-->
            <select class="form-control form_new" id="graphType" value="" onchange="selectFunction();" >
                <!--                <option value="00" style="background: #fff;">Phase1 Voltage Vs Time</option>
                                    <option value="02">Phase2 Voltage Vs Time</option>
                                    <option value="04">Phase3 Voltage Vs Time</option>-->
                <option value="6">Phase1 Current Vs Time </option>
                <option value="8">Phase2 Current Vs Time</option>
                <option value="10">Phase3 Current Vs Time</option>
                <option value="12">Phase1 Power Vs Time</option>
                <option value="14">Phase2 Power Vs Time</option>
                <option value="16">Phase3 Power Vs Time</option>
                <!--                <option value="30">Phase1 Power Factor Vs Time</option>
                                    <option value="32">Phase2 Power Factor Vs Time</option>
                                    <option value="34">Phase3 Power Factor Vs Time</option>
                                    <option value="512">Cumulative Energy kVAh Vs Time</option>
                                    <option value="514">Cumulative Energy kWh Vs Time</option>-->

            </select>
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

<div class="row" style="margin-bottom: 0;">
    <div class="col-lg-6 graph_bg">
        <div class="panel panel-primary panel_1" style="">
            <div class="panel-heading graph_heading">
                <!--                  <h3 class="panel-title"><i class="fa fa-bar-chart-o"></i> <script type="text/javascript">
                                    document.write(headTitle);
                                        </script>                 
                                  </h3>-->
            </div>
            <div class="panel-body" style="width: 100%; border: 1px solid #ebeaea;">
                <div class="flot-chart">
                    <div class="flot-chart-content" id="powGraph" style="margin-left: 0px;"></div>
                </div>
            </div>
        </div>
    </div>


    <div class="right_bg">
        <div class="" style="float: right;width:100%">
            <div id="energyCostPanel" class="panel panel-success success_" style="">
                <div  class="panel-heading panel-heading1" style="background:none;border:none;width:100%;float: left;">
                    <div class="image-bg">
                        <div class="bulb_"><img src="/ilids/resources/images/bulb_green1.png" style=""></div>
                    </div>
                    <div id="energyCost" class="text-announce" style="float: left;padding-left:12px;padding-right:12px;">
                        <p id="energyCostValue" class="announcement-text" style="padding: 5px;"></p>
                    </div>
                </div>
            </div>
        </div>
        <div class="power_factor_main_bg"style="float: right; width: 100%;">
            <div id="phase1PowerFactorPanel" class="power_factor_bg">
                <div  class="power-factor" style="border:none;width: 100%;float: left;">
                    <div id="phase1PowerFactor">
                        <p class="announcement-text" style="">Power factor Phase 1: ${phase1PowerFactor}</p>
                    </div>
                </div>
            </div>
            <div id="phase2PowerFactorPanel" class="power_factor_bg"  style="">
                <div  class="power-factor" style="border:none;width: 100%;float: left;">
                    <div id="phase2PowerFactor">
                        <p class="announcement-text" style="">Power factor Phase 2: ${phase2PowerFactor}</p>
                    </div>
                </div>
            </div>   
            <div id="phase3PowerFactorPanel"  class="power_factor_bg" style="margin-bottom: 0;">
                <div class="power-factor" style="border:none;width: 100%;float: left;">
                    <div id="phase3PowerFactor">
                        <p class="announcement-text" style="">Power factor Phase 3: ${phase3PowerFactor}</p>
                    </div>
                </div>
            </div>              
        </div>
    </div>
</div>

<tbody>





<script type="text/javascript" src="${resources}ilids-d3/js/d3.js" charset="utf-8"></script>
<!--<script type="text/javascript" src="${resources}ilids-d3/js/crossfilter.js"></script>-->
<!--<script type="text/javascript" src="${resources}ilids-d3/js/dc.js"></script>-->
<!--<script type="text/javascript" src="${resources}ilids-d3/js/jquery.min.js"></script>-->
<!--<script type="text/javascript" src="${resources}ilids-d3/js/jquery-1.10.2.js"></script>-->
<!--<script type="text/javascript" src="${resources}ilids-d3/graph.js"></script>-->
<script type="text/javascript" src="${resources}ilids-d3/powGrap.js"></script>
<!--<script type="text/javascript" src="${resources}ilids-d3/js/dimple.v2.0.0.min.js"></script>-->

<script type="text/javascript">
    jq(document).ready(function() {
        var startUrl = "dashboardupdate/subscribe";
        var pollUrl = "dashboardupdate/polldata";
        var poll = new Poll();
        poll.start(startUrl, pollUrl);
    });

                jq(function() {
                    var fromDateParams = document.getElementById("SelectedDate").value;
                    var fromHoursParams = document.getElementById("from-hours").value;
                    var fromMinutesParams = document.getElementById("from-minutes").value;
                    var toDateParams = document.getElementById("SelectedDate1").value;
                    var toHoursParams = document.getElementById("to-hours").value;
                    var toMinutesParams = document.getElementById("to-minutes").value;
                    var deviceId = document.getElementById("deviceList").value;
                    var energyRequestUrl = "dashboardupdate/energyCost";
                    var energyValueDiv = 0;
                    var alertSpan = 0;
                    if (energyRequest) {
                        energyRequest.abort(); // abort any pending request
                    }

                    // fire off the request to MatchUpdateController
                    var energyRequest = $.ajax({
                        url: energyRequestUrl,
                        type: "get",
                        data: {"startDate": fromDateParams, "endDate": toDateParams, "fromHours": fromHoursParams, "fromMinutes": fromMinutesParams, "toHours": toHoursParams, "toMinutes": toMinutesParams, "deviceId": deviceId},
                        success: function(pollData) {
                            energyValueDiv = '<p id="energyCostValue" class="announcement-text" style="float:left;">\n\
                                               <div style="float:left;width:100%;line-height:20px;"><div style="float:left;width:100px;text-align:left;">Peak cost</div><div style="float:left;margin-left:2px;text-align:left;word-break: break-all;">: Rs. ' + Number(pollData.peakCost) + '</div></div><br/>\n\
                                               <div style="float:left;width:100%;line-height:20px;"><div style="float:left;width:100px;text-align:left;">Normal cost</div><div style="float:left;margin-left:2px;text-align:left;word-break: break-all;">: Rs. ' + Number(pollData.normalCost) + '</div></div><br/>\n\
                                               <div style="float:left;width:100%;line-height:20px;"><div style="float:left;width:100px;text-align:left;">Off peak cost</div><div style="float:left;"><div style="float:left;margin-left:3px;text-align:left;word-break: break-all;">: Rs. ' + Number(pollData.offPeakCost) + '</div></div>\n\
                                            </p>';
                            alertSpan = '<span id="alertCountId" style="color: red;">' + Number(pollData.alertCount) + '</span>';
                            jq(energyValueDiv).replaceAll('#energyCostValue');
                            jq(alertSpan).replaceAll('#alertCountId');

                        }
                    });
                });

</script>
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
    if (phaseParam != 0) {
        var graphTypeArray = document.getElementById('graphType').options;
        for (var i = 0; i < graphTypeArray.length; i++) {
            if (graphTypeArray[i].value === phaseParam) {
                break;
            }
            index++;
        } 
    }
    document.getElementById("graphType").selectedIndex = index;
    
    jq(function() {
        jq( "#SelectedDate" ).datepicker({
            onSelect: function(selectedDate) {                
                jq( "#SelectedDate1" ).datepicker("option", "minDate", selectedDate);
            }
        });
        jq( "#SelectedDate1" ).datepicker({minDate: toDateParam});
  });
</script>
