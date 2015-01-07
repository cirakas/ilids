<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<c:url var="resources" value="/resources/" />

<!DOCTYPE html>

<html lang="en">
    <head>
        <meta http-equiv="cache-control" CONTENT="public"/>
        <meta http-equiv="cache-control" content="max-age=604800" />
        <!--<meta http-equiv="expires" content="0" />-->
        <meta http-equiv="expires" content="Mon, 01 Jan 2015 2:00:00 GMT">  
        <meta http-equiv="Pragma" content="no-cache"/>
        <meta charset="utf-8">
        <meta http-equiv="content-type" content="text/html; charset=UTF8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <meta name="description" content="">
        <meta name="author" content="">
        <title>ILIDS</title>

        <!-- Bootstrap core CSS -->
    <!--    <link href="${resources}ilids-template/css/bootstrap.min.css" rel="stylesheet">-->
        <link href="${pageContext.request.contextPath}/resources/ilids-template/css/bootstrap.css" rel="stylesheet">
        <link href="${pageContext.request.contextPath}/resources/ilids-template/css/bootstrap-datetimepicker.min.css" rel="stylesheet" media="screen">

        <!-- Add custom CSS here -->
        <link href="${pageContext.request.contextPath}/resources/ilids-template/css/sb-admin.css" rel="stylesheet">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/ilids-template/font-awesome/css/font-awesome.min.css">
        <!--    <script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.11.0/jquery.min.js"></script>
            <link rel="stylesheet" type="text/css" href="${resources}ilids-template/css/jquery.datepick.css"> 
            <script type="text/javascript" src="${resources}ilids-template/js/jquery.plugin.js"></script> 
            <script type="text/javascript" src="${resources}ilids-template/js/jquery.datepick.js"></script>-->
        <!-- Page Specific CSS -->
        <!--    <link href="css/dc.css" rel="stylesheet">-->


        <!--    <link rel="stylesheet" href="http://cdn.oesmith.co.uk/morris-0.4.3.min.css">-->
        <style>
            .color-menu li a,.navbar-nav li a{outline:none!important;}
            .color-menu li{border-top: 1px solid #384355;}
            .color-menu li:first-child{border: none;}
            .color-menu li:last-child{border-bottom: 1px solid #384355;}
            .color-menu li a{color:#dedfe0!important;}
            body{font: 14px sans-serif;outline: none!important;}
            .view_all_bg{
                padding-top: 8px!important;padding-bottom: 8px!important;border-radius: 0 0 2px 2px;
            }
            .view_all_bg:hover{
                background: url(/ilids/resources/images/menu_bg2.png)!important;
                color: #fff!important;
                /*        border: 1px solid #374153;*/
            }
            .badge_1{background: #999;}
            .view_all_bg:hover .badge_1{background: #febb00;}
            .modal-header_1{border-bottom: 1px solid #0093c3;background: url(/ilids/resources/images/alert_head_bg.png);color:#fff;}
            .th_style{padding: 13px 10px 8px!important;color: #666666!important;text-align:center;
                      border: 1px solid #01a4d9!important;border-bottom: 2px solid #01a4d9!important;}
            .th_style:hover{background: #fff!important;}
            .dsd{table-layout: fixed; word-wrap: break-word; color:#015d7f;}
            .dsd > tbody > tr:nth-child(2n+1) > td{background: #f4fcff!important;}

            .dsd.table-hover > tbody > tr:hover > td,
            .dsd.table-hover > tbody > tr:hover > th {background: #fff!important;}

            .dsd.table-bordered > tbody > tr > td{border: 1px solid #94e5ff!important;}

            .msg_bg{color:#fff!important;}
            .alert_bg{width:75%;border-radius: 30px;overflow: hidden;padding: 0;float: none;}
            .main_nav{border:none!important;height: auto!important; float: left!important;background: #3c495e!important;}


            @media (max-width: 767px){.margin_{margin-top: -8px!important;position: relative; background: #4f5b6f;} 
                                      .bg_res{background: #4f5b6f;position: relative;float: left;width:100%;}
                                      .msg_bg{border-bottom: 1px solid #384355;background: none repeat scroll 0% 0% #4f5b6f;}
                                      .msg_bg1{border-bottom: none;}
                                      .msg_bg:hover{background: none repeat scroll 0% 0% #4f5b6f !important;}
                                      .alert_bg{margin: 0 auto;float: none;width:80%;}
                                      .navbar-header1{float: left;width: 100%;background: #3c495e;}
                                      .navbar-inverse{background: #0093c3;padding-bottom: 0;}

                                      .side-nav>li.dropdown>ul.dropdown-menu>li>a.active{background:#4f5b6f;}

                                      .nav .open > a,
                                      .nav .open > a:hover,
                                      .nav .open > a:focus {
                                          background: none repeat scroll 0% 0% #4f5b6f !important;
                                          border-color: #343434;
                                      }

                                      .navbar-nav .open .dropdown-menu {background: none repeat scroll 0% 0% #4f5b6f !important;}


            }

        </style>


        <script type="text/javascript">
            function PreviousData(start, end) {
                if (start > 0)
                    latestAlertRequest(start, end);

            }
        </script>

        <script type="text/javascript">
            function latestAlertRequest(start, end) {
                var fromDateAlert = document.getElementById("SelectedDate").value;
                var toDateAlert = document.getElementById("SelectedDate1").value;
                var alertUrl = "dashboardupdate/alertList";
                $.post(alertUrl, {'startDate': fromDateAlert, 'endDate': toDateAlert, 'start': start, 'end': end}, function(data) {
                    // $('#demo').html( '<table cellpadding="0" cellspacing="0" border="0" class="display" id="example"></table>' );
                    var theTable = "<tbody id=\"alertBody\">";
                    for (var j = 0; j < data.length; j++) {
                        var currentDates = new Date(Number(data[j][2]));
                        theTable += '<tr style=""><td style="text-align:center;">Chiller</td><td style="text-align:center;">' + data[j][5] + '</td><td style="text-align:center;">' + data[j][1] + '</td><td style="text-align:center;">' + currentDates.getDate() + '/' + (currentDates.getMonth() + 1) + '/' + currentDates.getFullYear() + ' ' + currentDates.getHours() + ':' + currentDates.getMinutes() + ':' + currentDates.getSeconds() + '</td>';
                        theTable += '</tr>';
                    }
                    var link = "<ul  id =\"linkId\" class=\"pager\"> <li><a href=\"#\" onclick=\"PreviousData(" + Number(start - 10) + "," + Number(end) + ")\">Previous</a></li> <li><a href=\"#\" onclick=\"NextData(" + Number(start + 10) + "," + Number(end) + ")\">Next</a></li></ul>";
                    $(link).replaceAll("#linkId");
                    theTable += "</tbody>";
                    $(theTable).replaceAll("#alertBody");

                });
            }
        </script>

        <script type="text/javascript">
            function NextData(start, end) {
                latestAlertRequest(start, end);
            }
        </script>

    </head>

    <body>

        <div id="wrapper">

            <!-- Sidebar -->
            <nav class="navbar navbar-inverse navbar-fixed-top main_nav"  role="navigation" style="">
                <!-- Brand and toggle get grouped for better mobile display -->
                <div class="navbar-header navbar-header1">
                    <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-ex1-collapse">
                        <span class="sr-only">Toggle navigation</span>
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                    </button>
                    <form:form  method="post"   id="selectopt"  name="frms"  class="header">
                        <a class="navbar-brand" href="options" style="outline: none;font-size: 25px;color:#e8e7e7;color:#fff;">iLids</a>
                </form:form>
                </div>

                <!-- Collect the nav links, forms, and other content for toggling -->
                <div class="collapse  navbar-collapse navbar-ex1-collapse bg_res">
                    <c:url value="/systemsettings" var="sysurl"/>
                    <security:authorize access="isAuthenticated()">

                        <!--        <ul class="nav navbar-nav side-nav color-menu"  style="background: #272727;background: #4f5b6f;">             
                        <c:forEach var="menuIdList" items="${menuIdList}" >
                            <c:if test="${menuIdList=='1'}">
                             <li class="dropdown">
                               <a href="#" class="dropdown-toggle" data-toggle="dropdown"><i class=""><img src="/ilids/resources/images/manage_1.png"></i>&nbsp; User Management<div class="active_arrow"></div><b class="caret"></b></a>
                               <ul class="dropdown-menu" style="padding-bottom: 0;">
                                   <li><a href='<c:url value="/user"/>'>&nbsp;&nbsp;&nbsp; Users</a></li>
                                   <li style="border-bottom:none;"><a href="<c:url value="/role"/>">&nbsp;&nbsp;&nbsp; Roles</a></li>
                               </ul>
                             </li>
                            </c:if>
                            <c:if test="${menuIdList==2}">
                           <li><a href="<c:url value="/systemsettings"/>"><i class=""><img src="/ilids/resources/images/system_1.png"></i>&nbsp; System Settings<div class="active_arrow"></div></a></li>
                            </c:if>
                            <c:if test="${menuIdList==3}">
                          <li><a href="<c:url value="/devices"/>"><i class=""><img src="/ilids/resources/images/mbl_1.png"></i>&nbsp; Devices<div class="active_arrow"></div></a></li>
                            </c:if>
                            <%--<c:if test="${menuIdList==4}">
                            <li><a href="#"><i class=""><img src="/ilids/resources/images/chart_1.png"></i>&nbsp; Charts<div class="active_arrow"></div></a></li>
                            </c:if>
                             <c:if test="${menuIdList==5}">
                            <li><a href="#"><i class=""><img src="/ilids/resources/images/alerts_1.png"></i>&nbsp; Alerts<div class="active_arrow"></div></a></li>
                            </c:if>  --%>    
                            <c:if test="${menuIdList=='6'}">
                            <li><a  href="<c:url value="/mailsms"/>"><i class=""><img src="/ilids/resources/images/sms_1.png"></i>&nbsp; E-mail/SMS Settings<div class="active_arrow"></div></a></li>
                            </c:if>  
                            <c:if test="${menuIdList=='7'}">
                            <li><a href="<c:url value="/note/add"/>"><i class=""><img src="/ilids/resources/images/notes_1.png"></i>&nbsp; Notes<div class="active_arrow"></div></a></li>
                            </c:if>   
                            <%-- <c:if test="${menuIdList=='8'}">
                             <li><a href="#"><i class=""><img src="/ilids/resources/images/chat_1.png"></i>&nbsp; Live Chat<div class="active_arrow"></div></a></li>
                              </c:if>--%>  
                            <c:if test="${menuIdList=='9'}">
                            <li><a href="<c:url value="/devicezones"/>"><i class=""><img src="/ilids/resources/images/mbl_1.png"></i>&nbsp; Device Zone<div class="active_arrow"></div></a></li>
                            </c:if>
                            <%--<c:if test="${menuIdList=='10'}">--%>
                            <li><a href="<c:url value="/addressmap"/>"><i class=""><img src="/ilids/resources/images/mbl_.png"></i>&nbsp; Address Map<div class="active_arrow"></div></a></li>
                            <%--</c:if>--%>
                            
                            <c:if test="${menuIdList=='10'}">
                           <li><a href="<c:url value="/charts"/>"><i class=""><img src="/ilids/resources/images/chart_1.png"></i>&nbsp; Charts<div class="active_arrow"></div></a></li>
                            </c:if>
                            
                        </c:forEach>
                      </ul>-->

                        <ul class="nav navbar-nav navbar-right navbar-user margin_">
                            <li class="dropdown messages-dropdown">
                                <a href="#" class="dropdown-toggle msg_bg" data-toggle="dropdown"><i class="fa fa-envelope"></i> Messages <span class="badge" style="background:#FB8805;"></span> <b class="caret"></b></a>
                                <ul class="dropdown-menu" style="padding-bottom: 0;">
                                    <li class="dropdown-header">7 New Messages</li>
                                    <li class="message-preview">
                                        <a href="#">
                                            <!--                    <span class="avatar"><img src="http://placehold.it/50x50"></span>-->
                                            <span class="name">John Smith:</span>
                                            <span class="message">Hey there, I wanted to ask you something...</span>
                                            <span class="time"><i class="fa fa-clock-o"></i> 4:34 PM</span>
                                        </a>
                                    </li>
                                    <li class="divider"></li>
                                    <li class="message-preview">
                                        <a href="#">
                                            <!--                    <span class="avatar"><img src="http://placehold.it/50x50"></span>-->
                                            <span class="name">John Smith:</span>
                                            <span class="message">Hey there, I wanted to ask you something...</span>
                                            <span class="time"><i class="fa fa-clock-o"></i> 4:34 PM</span>
                                        </a>
                                    </li>
                                    <li class="divider"></li>
                                    <li class="message-preview">
                                        <a href="#">
                                            <!--                    <span class="avatar"><img src="http://placehold.it/50x50"></span>-->
                                            <span class="name">John Smith:</span>
                                            <span class="message">Hey there, I wanted to ask you something...</span>
                                            <span class="time"><i class="fa fa-clock-o"></i> 4:34 PM</span>
                                        </a>
                                    </li>
                                    <li class="divider" style="margin-bottom: 0;"></li>
                                    <li><a class="view_all_bg" style="padding-top: 10px!important;padding-bottom: 10px!important;" href="#">View Inbox <span class="badge badge_1">7</span></a></li>
                                </ul>
                            </li>
                            <c:if test="${selection!=null}">
                                        <li class="dropdown alerts-dropdown">
                                            <a href="#" class="dropdown-toggle msg_bg" data-toggle="dropdown"><i class="fa fa-bell"></i> <span id="alertCountId" style="color: red;">${alertCount}</span> Alerts <span id="alertId" class="badge" style="background:#FF0404;"></span> <b class="caret"></b></a>
                                          <ul class="dropdown-menu" style="padding-bottom: 0;">
                                        <li><a href="#">Default <span class="label label-default">Default</span></a></li>
                                            <li><a href="#">Primary <span class="label label-primary">Primary</span></a></li>
                                            <li><a href="#">Success <span class="label label-success">Success</span></a></li>
                                            <li><a href="#">Info <span class="label label-info">Info</span></a></li>
                                            <li><a href="#">Warning <span class="label label-warning" style="margin-left: 4px;">Warning</span></a></li>
                                            <li><a href="#">Danger <span class="label label-danger" style="margin-left: 10px;padding: 3px 9px;">Danger</span></a></li>
                                            <li class="divider" style="margin-bottom: 0;"></li>
                                            <li style=""><a class="view_all_bg" href="#" onclick="latestAlertRequest(1,10);" data-toggle="modal" data-target="#myAlertModal">View All</a></li>
                                          </ul>
                                        </li>
                            </c:if>
                            <li class="dropdown user-dropdown">
                                <a href="#" class="dropdown-toggle msg_bg msg_bg1" data-toggle="dropdown"><i class="fa fa-user"></i> <security:authentication property="principal.username" /> <b class="caret"></b></a>
                                <ul class="dropdown-menu" style="padding-bottom: 0;">
                                    <li><a href="#"><i class="fa fa-user"></i> Profile</a></li>
                                    <li><a href="#"><i class="fa fa-envelope"></i> Inbox <span class="badge">7</span></a></li>
                                    <li><a href="#"><i class="fa fa-gear"></i> Settings</a></li>
                                    <li class="divider" style="margin-bottom: 0;"></li>
                                        <security:authorize access="isAuthenticated()">
                                        <li><a class="view_all_bg" style="padding-top: 11px!important;padding-bottom: 11px!important;" href='<c:url value="/j_spring_security_logout"/>'><i class="fa fa-power-off"></i> Log Out</a></li>
                                        </security:authorize>
                                </ul>
                            </li>
                        </ul>
                    </security:authorize>
                </div><!-- /.navbar-collapse -->
            </nav>

            <!-- Modal to show alerts -->
            <div class="modal fade" id="myAlertModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <div class="modal-header" style="border-bottom: 1px solid #0093c3;background:url(/ilids/resources/images/alert_head_bg.png);color:#fff;">
                            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                            <h4 class="modal-title" id="myModalLabel">Alerts</h4>
                        </div>
                        <table cellpadding="0" cellspacing="0" border="0"
                               class="table table-bordered table-hover table-striped tablesorter" id="example" style="table-layout: fixed; word-wrap: break-word;" >
                            <thead class="thead_style">
                                <tr style="">
                                    <th style="">Feeder <!--<i class="fa fa-sort"></i>--></th>
                                    <th style="">Data type <!--<i class="fa fa-sort"></i>--></th>
                                    <th style="">Value <!--<i class="fa fa-sort"></i>--></th>
                                    <th style="">Time <!--<i class="fa fa-sort"></i>--></th>
                                </tr>
                            </thead><tbody id="alertBody"></tbody>

                        </table>
                        <ul  id ="linkId" class="pager">
                            <li><a href="#">Previous</a></li>
                            <li><a href="#">Next</a></li>
                        </ul>
                    </div>
                </div>
            </div>

            <!-- Modal to show alerts -->    
            <div class="modal fade" id="myAlertModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
                <div class="modal-dialog alert_bg" style="">
                    <div class="modal-content">
                        <div class="modal-header modal-header_1">
                            <button type="button" class="close" data-dismiss="modal" aria-hidden="true" style="margin-right: 20px;font-size: 35px;">&times;</button>
                            <h4 class="modal-title" id="myModalLabel" style="text-indent:20px;">Alerts</h4>
                        </div>
                        <table cellpadding="0" cellspacing="0" border="0"
                               class="table table-bordered table-hover table-striped tablesorter dsd" id="example">
                            <thead class="thead_style">
                                <tr style="">
                                    <th class="th_style">Feeder <!--<i class="fa fa-sort"></i>--></th>
                                    <th class="th_style">Data type <!--<i class="fa fa-sort"></i>--></th>
                                    <th class="th_style">Value <!--<i class="fa fa-sort"></i>--></th>
                                    <th class="th_style">Time <!--<i class="fa fa-sort"></i>--></th>
                                </tr>
                            </thead>
                        </table>
                    </div>
                </div>
            </div>

            <div id="page-wrapper" style="">