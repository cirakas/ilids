<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<c:url var="resources" value="/resources/" />

<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <title>ILIDS</title>
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <meta name="description" content="">
        <meta name="author" content="">

        <!-- Le styles -->
        <script src='http://ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js'></script>
        <script type='text/javascript' src='${resources}jquery/menu_jquery.js'></script>
        <link href="${resources}css/bootstrap.css" rel="stylesheet">
        <link href="${resources}css/menu-style.css" rel="stylesheet">
        <style>
            body {
                padding-top: 60px;
                /* 60px to make the container go all the way to the bottom of the topbar */
            }
        </style>
        <link href="${resources}css/bootstrap-responsive.css" rel="stylesheet">

        <!-- Le HTML5 shim, for IE6-8 support of HTML5 elements -->
        <!--[if lt IE 9]>
              <script src="http://html5shim.googlecode.com/svn/trunk/html5.js"></script>
            <![endif]-->

        <!-- Le fav and touch icons -->
        <link rel="shortcut icon" href="${resources}ico/favicon.ico">
        <link rel="apple-touch-icon-precomposed" sizes="144x144"
              href="${resources}ico/apple-touch-icon-144-precomposed.png">
        <link rel="apple-touch-icon-precomposed" sizes="114x114"
              href="${resources}ico/apple-touch-icon-114-precomposed.png">
        <link rel="apple-touch-icon-precomposed" sizes="72x72"
              href="${resources}ico/apple-touch-icon-72-precomposed.png">
        <link rel="apple-touch-icon-precomposed"
              href="${resources}ico/apple-touch-icon-57-precomposed.png">
    </head>
    <body>
        <div class="navbar navbar-inverse navbar-fixed-top">
            <div class="navbar-inner">
                <div class="container">
                    <a class="btn btn-navbar" data-toggle="collapse"
                       data-target=".nav-collapse"> <span class="icon-bar"></span> <span
                            class="icon-bar"></span> <span class="icon-bar"></span>
                    </a> <a class="brand" href='<c:url value="/home"/>'>ILIDS</a>
                     <security:authorize access="hasRole('ROLE_ADMIN')">
                    <a class="brand" style="margin-left:-20px;font-size: 15px;" href='<c:url value="/home"/>'>Admin</a>
                        </security:authorize>
                    <div class="nav-collapse collapse">
                       
                        <ul class="nav">
                            <security:authorize access="isAuthenticated()">
                                <li><a href='<c:url value="/j_spring_security_logout"/>'>Logout</a></li>
                                </security:authorize>
                        </ul>
                    </div>
                    <!--/.nav-collapse -->
                </div>
            </div>
        </div>
        <!--                   Left side Menu bar                         -->
        <div style="width:200px;float: left;margin-top: -20px;padding-right: 10px;">
            <security:authorize access="hasRole('ROLE_ADMIN')">
                <div id='cssmenu'>
                    <ul>
                        <li class='active'></li>
                        <li class='has-sub'><a href='#'><span>User management</span></a>
                            <ul>
                                <li><a href='#'><span>Users</span></a></li>
                                <li><a href='#'><span>User types</span></a></li>
                            </ul>
                        </li>
                        <li class='has-sub'><a href='#'><span>System settings</span></a>
                            <ul>
                                <li><a href='#'><span>MDV</span></a></li>
                                <li><a href='#'><span>System clock settings</span></a></li>
                                <li><a href='#'><span>Rates/unit management</span></a></li>
                                <li><a href='#'><span>Time zone settings</span></a></li>
                                <li><a href='#'><span>Periodic billing</span></a></li>
                            </ul>
                        </li>
                        <li><a href='#'><span>Charts management</span></a> </li>
                        <li><a href='#'><span>Alerts management</span></a>  </li>
                        <li><a href='#'><span>Device management</span></a> </li>
                        <li><a href='#'><span>E-mail/Sms settings</span></a> </li>
                        <li><a href='#'><span>Live chat</span></a>  </li>
                        <li><a href='#'><span>Profile management</span></a>  </li>
                        <li class='last'> <a href='<c:url value="/note/add"/>'><span>Notes</span></a>  </li>
                    </ul>
                </div>
            </security:authorize>

            <security:authorize access="hasRole('ROLE_USER')">
                <div id='cssmenu'>
                    <ul>
                        <li class='active'></li>
                        <li ><a href='#'><span>Graphs</span></a></li>
                        <li ><a href='#'><span>Alerts</span></a></li>
                        <li ><a href='<c:url value="/note/add"/>'><span>Notes</span></a></li>
                        <li ><a href='#'><span>Message</span></a></li>
                        <li ><a href='#'><span>Live chat</span></a></li>
                        <li ><a href='#'><span>Profile</span></a></li>
                        <li ><a href='#'><span>Help</span></a></li>
                </div>
            </security:authorize>

        </div>
        <div class="container">
