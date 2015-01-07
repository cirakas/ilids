<%-- 
    Document   : options
    Created on : 29 Dec, 2014, 1:47:05 PM
    Author     : user
--%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<c:url var="resources" value="/resources/" />
<%@page contentType="text/html" pageEncoding="windows-1252"%>
<%--<%@ include file="../header.jsp" %>--%>
<%--<%@ include file="../footer.jsp" %>--%>

<!DOCTYPE html>

<html lang="en">
    <head>

        <!-- Bootstrap -->

        <!-- Bootstrap core CSS -->
            <!--    <link href="${resources}ilids-template/css/bootstrap.min.css" rel="stylesheet">-->
        <link href="${pageContext.request.contextPath}/resources/ilids-template/css/bootstrap.css" rel="stylesheet">
        <link href="${pageContext.request.contextPath}/resources/ilids-template/css/bootstrap-datetimepicker.min.css" rel="stylesheet" media="screen">

        <!-- Add custom CSS here -->
        <link href="${pageContext.request.contextPath}/resources/ilids-template/css/sb-admin.css" rel="stylesheet">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/ilids-template/font-awesome/css/font-awesome.min.css">

        <link href="${resources}css/style.css" media="all" rel="stylesheet" type="text/css">

        <style>
            .main_nav{border:none!important;height: auto!important; float: left!important;background: #3c495e!important;}
            .msg_bg{color:#fff!important;}
            @media (max-width: 767px){
                .margin_{margin-top: -8px!important;position: relative; background: #4f5b6f;}
                .navbar-header1{float: left;width: 100%;background: #3c495e;}
                .msg_bg{border-bottom: 1px solid #384355;background: none repeat scroll 0% 0% #4f5b6f;}
                .msg_bg1{border-bottom: none;}
                .msg_bg:hover{background: none repeat scroll 0% 0% #4f5b6f !important;}
            }
        </style>

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

        </div>      










        <section class="sec-bg">

            <div class="land-main">
                <div class="land-head-bg">
                    <p class="grn-head">Lorem Ipsum is simply dummy</p>
                    <div class="grn-cntnt">
                        text of the printing and typesetting industry. Lorem Ipsum
                        has been the industry's standard dummy text ever since the 
                        1500s, when an unknown printer took

                    </div>
                </div>
            </div>


            <div class="contnt-main-bg" style="width:100%">
                <div class="land-main">
                    <div class="settings-bg">
                        <a href="<c:url value="/systemsettings"/>"><img src="${resources}images/settings.png"></a>
                    </div>

                    <!--- Start col-lg-12 col-padd ----->     
                    <div class="col-lg-12 col-padd">

                        <div class="col-lg-6" style="padding:0 20px">
                            <div class="energy-meter-bg">
                                <div class="meter-bg"><img src="${resources}images/energy-meter.png"></div>
                                <div class="energy-head-bg">
                                    <h5>ENERGY METER</h5>
                                    <p>when an unknown printer took text of the</p>
                                </div>
                            </div>
                            <div class="click-here">
                                <a href='<c:url value="/energy/EMeter"/>'>CLICK HERE</a>
                            </div>
                            <div class="shadow"><img src="${resources}images/sdw-bg.png"></div>
                        </div>

                        <div class="col-lg-6" style="padding:0 20px">
                            <div class="transducer-meter-bg">
                                <div class="transducer-bg"><img src="${resources}images/transducer.png"></div>
                                <div class="energy-head-bg" style="background:#b8e4fa;">
                                    <h5>TRANSDUCER METER</h5>
                                    <p>when an unknown printer took text of the</p>
                                </div>
                            </div>
                            <div class="click-here">
                                <a href='<c:url value="/thermo/TMeter"/>'>CLICK HERE</a>
                            </div>
                            <div class="shadow"><img src="${resources}images/sdw-bg.png"></div>
                        </div>

                    </div>
                    <!--- End col-lg-12 col-padd ----->      
                </div>
            </div>



        </section>


        <footer>
            <div class="copt-right-bg">
                All Rights Reserved. Cirakas Consulting, 2014.
            </div>
        </footer>




        <!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
        <script src="${resources}ilids-d3/js/jquery-1.10.2.js"></script>
        <!-- Include all compiled plugins (below), or include individual files as needed -->
        <script src="${resources}js/bootstrap.min.js"></script>

        <script type="text/javascript">
            var opts = '${optModel}';
            function eneSelect() {
                var selectedOpt;
                selectedOpt = 'Energy Meter';
                document.frms.action = "energy/" + selectedOpt;
                document.frms.submit();
            }
            function temSelect() {
                var themOpt;
                themOpt = 'Thermal Meter';
                document.frms.action = "thermo/" + themOpt;
                document.frms.submit();
            }

        </script>

    </body>
</html>