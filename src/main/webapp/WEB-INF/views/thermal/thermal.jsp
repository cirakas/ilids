<%@page import="org.springframework.security.core.userdetails.User"%>
<%@page import="org.springframework.security.core.context.SecurityContextHolder"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ include file="../../header.jsp" %>
<%@ include file="../../footer.jsp" %>
<!--<nav class="navbar navbar-inverse navbar-fixed-top main_nav"  role="navigation" style="">-->
<meta charset="utf-8">
<div>
    <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-ex1-collapse">
        <span class="sr-only">Toggle navigation</span>
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
    </button>
    <div class="collapse  navbar-collapse navbar-ex1-collapse bg_res">
        <c:url value="/systemsettings" var="sysurl"/>
        <security:authorize access="isAuthenticated()">
            <ul class="nav navbar-nav side-nav color-menu"  style="background: #272727;background: #4f5b6f;">             
                <c:forEach var="menuIdLists" items="${menuIdLists}" >
                    <c:if test="${menuIdLists=='1'}">
                        <li><a href="<c:url value="/thermal"/>"><i class=""><img src="/ilids/resources/images/notes_1.png"></i>&nbsp; Dashboard<div class="active_arrow"></div></a></li>
                                </c:if>
                                <c:if test="${menuIdLists=='7'}">
                        <li><a href="<c:url value="/add"/>"><i class=""><img src="/ilids/resources/images/notes_1.png"></i>&nbsp; Notes<div class="active_arrow"></div></a></li>
                                </c:if>   
                            </c:forEach>
            </ul>
<!--            <ul class="nav navbar-nav navbar-right navbar-user margin_">
                <security:authorize access="isAuthenticated()">
                    <li><a class="view_all_bg" style="padding-top: 11px!important;padding-bottom: 11px!important;" href='<c:url value="/j_spring_security_logout"/>'><i class="fa fa-power-off"></i> Log Out</a></li>
                    </security:authorize>
            </ul>-->
            </li>-->
            </ul>
        </security:authorize>
    </div>
    <!--<a class="navbar-brand" href="home" style="outline: none;font-size: 25px;color:#e8e7e7;color:#fff;">iLids</a>-->
</div>
<!--</nav>-->

<script type="text/javascript">
    <%
        String admap1 = request.getParameter("phase");
        //String divId = request.getParameter("deviceId");
        String fromDt1 = request.getParameter("fromDate");
        String fromHr1 = request.getParameter("fromHours");
        String fromMin1 = request.getParameter("fromMinutes");
        String toDt1 = request.getParameter("toDate");
        String toHr1 = request.getParameter("toHours");
        String toMin1 = request.getParameter("toMinutes");
        Cookie[] cookies = request.getCookies();

        if (admap1 == null) {
            System.out.println("dfdg-fgbgbbbb---");
            if (cookies != null) {
                System.out.println("cookie not null----length" + cookies.length);
                for (Cookie cookie : cookies) {
                    if (cookie.getName().equals("tamap")) {
                        admap1 = cookie.getValue();
                        System.out.println("admap---" + admap1);
                    }

                    if (cookie.getName().equals("tfDate")) {
                        fromDt1 = cookie.getValue();
                        System.out.println("fromDt---" + fromDt1);
                    }

                    if (cookie.getName().equals("tfHour")) {
                        fromHr1 = cookie.getValue();
                        System.out.println("fromHr---" + fromHr1);
                    }

                    if (cookie.getName().equals("tfMin")) {
                        fromMin1 = cookie.getValue();
                        System.out.println("fromMin---" + toMin1);
                    }

                    if (cookie.getName().equals("ttoDt")) {
                        toDt1 = cookie.getValue();
                        System.out.println("toDt---" + toDt1);
                    }

                    if (cookie.getName().equals("ttoHr")) {
                        toHr1 = cookie.getValue();
                        System.out.println("toHr---" + toHr1);
                    }

                    if (cookie.getName().equals("ttoMin")) {
                        toMin1 = cookie.getValue();
                        System.out.println("toMin---" + toMin1);
                    }

                }
            }

        }
    %>

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

    var phaseParam = "<%=admap1%>";
    var fromDateParam = "<%=fromDt1%>";
    var toDateParam = "<%=toDt1%>";
    var fromHoursParam = "<%=fromHr1%>";
    var fromMinutesParam = "<%=fromMin1%>";
    var toHoursParam = "<%=toHr1%>";
    var toMinutesParam = "<%=toMin1%>";
    // var deviceParam = readCookie('deviceId');
    if (!phaseParam) {
        phaseParam = '2';
    }
    if (!fromDateParam) {
        fromDateParam = new Date().getMonth() + 1 + "/" + new Date().getDate() + "/" + new Date().getFullYear();//"7/20/2014"
    }
    if (!toDateParam) {
        toDateParam = new Date().getMonth() + 1 + "/" + new Date().getDate() + "/" + new Date().getFullYear();//"7/20/2014"
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
//    if (!deviceParam) {
//        deviceParam = '0';
//    }


    var servlet = "TransducerDataServlet?phase=" + phaseParam + "&fromDate=" + fromDateParam + "&fromHours=" + fromHoursParam + "&fromMinutes=" + fromMinutesParam + "&toDate=" + toDateParam + "&toHours=" + toHoursParam + "&toMinutes=" + toMinutesParam;



    function selectFunction()
    {

        var graphType = document.getElementById("graphType").value;
        //var device = document.getElementById("deviceList").value;
        var fromDate = document.getElementById("SelectedDate").value;
        var toDate = document.getElementById("SelectedDate1").value;
        var fromHours = document.getElementById("from-hours").value;
        var fromMinutes = document.getElementById("from-minutes").value;
        var toHours = document.getElementById("to-hours").value;
        var toMinutes = document.getElementById("to-minutes").value;
        var d1 = new Date(fromDate + " " + fromHours + ":" + fromMinutes + ":00");
        var d2 = new Date(toDate + " " + toHours + ":" + toMinutes + ":59");
        if (d2 < d1) {
            alert("end date should not be less than start date");
        }
        else {

//            document.cookie = "phase=" + graphType + " " + "start=" + fromDate + " " + "end=" + toDate + " " + "frHours=" + fromHours + " " + "frMinutes=" + fromMinutes + " " + "tHours=" + toHours + " " + "tMinutes=" + toMinutes + " ";
//            document.cookie = "cname=; expires=Thu, 01 Jan 1970 00:00:00 GMT";
            var myURL = window.location.protocol + "//" + window.location.host + window.location.pathname;
            document.location = myURL + "?phase=" + graphType + "&fromDate=" + fromDate + "&fromHours=" + fromHours + "&fromMinutes=" + fromMinutes + "&toDate=" + toDate + "&toHours=" + toHours + "&toMinutes=" + toMinutes;
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
        stroke: green;
        fill: none;
        stroke-width: 3;
    }

    .line.line1 {
        stroke:#e35c03;
        stroke:#d6b014;
        stroke:#ae4805;
        z-index:999;
        stroke-width: 0.5;
    }

    .line.line0 {
        stroke:steelblue ;
        stroke:#2faae1 ;
        stroke:#3bb4ea ;
        stroke-width: 3;
    }

    .line.line2 {
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

    .d3-tip {
        line-height: 1;
        font-weight: bold;
        padding: 12px;
        background: rgba(0, 0, 0, 0.8);
        color: #fff;
        border-radius: 2px;
    }

    .d3-tip:after {
        box-sizing: border-box;
        display: inline;
        font-size: 10px;
        width: 100%;
        line-height: 1;
        color: rgba(0, 0, 0, 0.8);
        content: "\25BC";
        position: absolute;
        text-align: center;
    }

    /* Style northward tooltips differently */
    .d3-tip.n:after {
        margin: -1px 0 0 0;
        top: 100%;
        left: 0;
    }

    .main rect {
        fill: steelblue;
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
        stroke: #F2700D;
        stroke-width: 2;
    }


    .focus text.y0 {
        border: 1px dotted #000000; 
        outline:1; 
        font: 16px sans-serif;
        height:25px; 
        width: 275px; 
    }

    .focus text.y1 {
        stroke: #0B6121;
        stroke-width: 1;
        position: absolute;			
        text-align: center;			
        width: 60px;					
        height: 28px;					
        padding: 4px;				
        font: 16px sans-serif;		
        background: green;	
        border: 0px;		
        border-radius: 8px;			
        pointer-events: none;
        opacity: yellow;
    }

    .focus line {
        stroke: purple;
        shape-rendering: crispEdges;
    }

    .focus line.y0 {
        stroke: #ED2A37;
        stroke-dasharray: 10 7;
        stroke-width: 2;
        opacity: .8;
    }

    .focus line.y1 {
        stroke: #F802E0;
        stroke-dasharray: 10 7;
        stroke-width: 2;
        opacity: .8;
    }
    .area {
        fill: lightsteelblue;
        stroke-width: 0;
        line-height:50%;
        fill: #b2e7ff;
        fill: #aae3fd;
        opacity: 1;
        z-index: -1;
    }

    .brush .extent {
        stroke: yellow;
        stroke-width: 2;
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

    .d3-tip {
        line-height: 1;
        font-weight: bold;
        padding: 12px;
        background: rgba(0, 0, 0, 0.8);
        color: #fff;
        border-radius: 2px;
    }

    /* Creates a small triangle extender for the tooltip */
    .d3-tip:after {
        box-sizing: border-box;
        display: inline;
        font-size: 10px;
        width: 100%;
        line-height: 1;
        color: rgba(0, 0, 0, 0.8);
        content: "\25BC";
        position: absolute;
        text-align: center;
    }

    /* Style northward tooltips differently */
    .d3-tip.n:after {
        margin: -1px 0 0 0;
        top: 100%;
        left: 0;
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
    .proceed_{padding:7px 10px;border-radius: 4px;background: #efba16;border:none; border-bottom: 2px solid #daa911;color:#fff;font-size:13px;font-weight: bold; }
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
    .row_date_bg{ margin: 5px 0 20px;padding: 10px 15px;
                  /*                background: #e8e9ea; background: url(/ilids/resources/images/bg_default1.jpg);*/
    }

    .power-factor-warning{padding: 4px 0;/*background: #ee6369;*/margin-left: 0;margin-bottom: 8px;width: 100%;float: left;border:none;border-radius:3px!important;
                          /*border-left: 3px solid #be7611;*/ background: #e6ad2d;color: #f00!important;
                          background-image: linear-gradient(#f98d49, #e0722c 70%, #c76120)!important;background: #fe9272 !important; border: 1px solid #e0623d;
                          filter: none;color:#9b3618!important;box-shadow: 0px 0px 5px #e46742 inset;}
    .power-factor-success{padding: 4px 0;/*background: #ee6369;*/margin-left: 0;margin-bottom: 8px;width: 100%;float: left;border:none;border-radius:3px!important;
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



<div class="row row_date_bg" style="width:100%; padding-left: 230px;">



    <div class="col-lg-3 drop_down_bg1" style="">
        <div class="form-group form-group1 form-group2" style="">
            <!--                <label></label>-->
            <select class="form-control form_new" id="graphType" value="" onchange="selectFunction();" >
                <!--                <option value="00" style="background: #fff;">Phase1 Voltage Vs Time</option>
                                    <option value="02">Phase2 Voltage Vs Time</option>
                                    <option value="04">Phase3 Voltage Vs Time</option>-->
                <option value="2">Temperature Vs Time </option>
                <!--                <option value="8">Phase2 Current Vs Time</option>
                                <option value="10">Phase3 Current Vs Time</option>
                                <option value="12">Phase1 Power Vs Time</option>
                                <option value="14">Phase2 Power Vs Time</option>
                                <option value="16">Phase3 Power Vs Time</option>-->
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
                <input type="text" class=" input_"  name="SelectedDate" id="SelectedDate" readonly value="">
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
                <input type="text" class=" input_" name="SelectedDate" id="SelectedDate1" readonly value="">
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

<div class="row" style="margin-bottom: 0;padding-left: 235px;">
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


</div>

<tbody>





<script type="text/javascript" src="${resources}ilids-d3/js/d3.js" charset="utf-8"></script>
<script type="text/javascript" src="${resources}ilids-d3/js/d3.tip.v0.6.3.js"></script>
<!--<script type="text/javascript" src="${resources}ilids-d3/js/crossfilter.js"></script>-->
<!--<script type="text/javascript" src="${resources}ilids-d3/js/dc.js"></script>-->
<!--<script type="text/javascript" src="${resources}ilids-d3/js/jquery.min.js"></script>-->
<!--<script type="text/javascript" src="${resources}ilids-d3/js/jquery-1.10.2.js"></script>-->
<!--<script type="text/javascript" src="${resources}ilids-d3/graph.js"></script>-->
<script type="text/javascript" src="${resources}ilids-d3/transducerGraph.js"></script>
<!--<script type="text/javascript" src="${resources}ilids-d3/js/dimple.v2.0.0.min.js"></script>-->

<script type="text/javascript">
                document.getElementById("SelectedDate").value = fromDateParam;
                document.getElementById("SelectedDate1").value = toDateParam;
                document.getElementById("from-hours").value = fromHoursParam;
                document.getElementById("from-minutes").value = fromMinutesParam;
                document.getElementById("to-hours").value = toHoursParam;
                document.getElementById("to-minutes").value = toMinutesParam;
                var index = 0;

//    if (deviceParam != 0) {
//        var deviceArray = document.getElementById('deviceList').options;
//        for (var i = 0; i < deviceArray.length; i++) {
//            if (deviceArray[i].value === deviceParam) {
//                break;
//            }
//            index++;
//        }
//    }
//    document.getElementById("deviceList").selectedIndex = index;
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
                    jq("#SelectedDate").datepicker({
                        onSelect: function(selectedDate) {
                            jq("#SelectedDate1").datepicker("option", "minDate", selectedDate);
                        }
                    });
                    jq("#SelectedDate1").datepicker({minDate: fromDateParam});
                });
</script>
