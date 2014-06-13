<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<c:url var="resources" value="/resources/" />

<!DOCTYPE html>
<html lang="en">
  <head>
    
    <meta charset="utf-8">
      <meta http-equiv="content-type" content="text/html; charset=UTF8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="">
    <meta name="author" content="">
    <title>ILIDS</title>

    <!-- Bootstrap core CSS -->
    <link href="${resources}ilids-template/css/bootstrap.min.css" rel="stylesheet">

    <!-- Add custom CSS here -->
    <link href="${resources}ilids-template/css/sb-admin.css" rel="stylesheet">
    <link rel="stylesheet" href="${resources}ilids-template/font-awesome/css/font-awesome.min.css">
    <!-- Page Specific CSS -->
<!--    <link href="css/dc.css" rel="stylesheet">-->
    
    
<!--    <link rel="stylesheet" href="http://cdn.oesmith.co.uk/morris-0.4.3.min.css">-->
<style>
    .color-menu{color: #f00!important;}
</style>
  </head>

  <body>

    <div id="wrapper">

      <!-- Sidebar -->
      <nav class="navbar navbar-inverse navbar-fixed-top" role="navigation">
        <!-- Brand and toggle get grouped for better mobile display -->
        <div class="navbar-header">
          <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-ex1-collapse">
            <span class="sr-only">Toggle navigation</span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
          </button>
          <a class="navbar-brand" href="home">ILIDS</a>
        </div>

        <!-- Collect the nav links, forms, and other content for toggling -->
        <div class="collapse navbar-collapse navbar-ex1-collapse">
            <security:authorize access="isAuthenticated()">
                <security:authorize access="hasRole('ROLE_ADMIN')">
            <ul class="nav navbar-nav side-nav color-menu">
             <li><a href="#"><i class="fa fa-bar-chart-o"></i> Charts</a></li>
              <li><a href="<c:url value="/systemsettings"/>"><i class="fa fa-bar-chart-o"></i> System settings</a></li>
<!--              <li class="dropdown">
              <a href="#" class="dropdown-toggle" data-toggle="dropdown"><i class="fa fa-caret-square-o-down"></i> System settings<b class="caret"></b></a>
              <ul class="dropdown-menu">
                <li><a href="#">MDV</a></li>
                <li><a href="#">System clock settings</a></li>
                <li><a href="#">Rates/unit management</a></li>    
                <li><a href="#">Time zone settings</a></li>
                <li><a href="#">Periodic billing</a></li>
              </ul>-->
            </li>
              <li class="dropdown">
              <a href="#" class="dropdown-toggle" data-toggle="dropdown"><i class="fa fa-caret-square-o-down"></i> User Management<b class="caret"></b></a>
              <ul class="dropdown-menu">
                <li><a href='<c:url value="/user"/>'>Users</a></li>
                <li><a href="#">User types</a></li>
              </ul>
            </li>
             <li><a href="#"><i class="fa fa-bar-chart-o"></i> Alerts</a></li>
            <li><a href="<c:url value="/devices"/>"><i class="fa fa-bar-chart-o"></i> Devices</a></li>
            <li><a href="<c:url value="/systemsettings"/>"><i class="fa fa-bar-chart-o"></i> System Settings</a></li>
            <li><a href="#"><i class="fa fa-bar-chart-o"></i> E-mail/SMS settings</a></li>
             <li><a href='<c:url value="/note/add"/>'><i class="fa fa-bar-chart-o"></i> Notes</a></li>
             <li><a href="#"><i class="fa fa-bar-chart-o"></i> Live chat</a></li>
         </ul>
            </security:authorize>
          <security:authorize access="hasRole('ROLE_USER')">
            <ul class="nav navbar-nav side-nav">
             <li><a href="#"><i class="fa fa-bar-chart-o"></i> Charts</a></li>
             <li><a href="#"><i class="fa fa-bar-chart-o"></i> Alerts</a></li>
            <li><a href='<c:url value="/note/add"/>'><i class="fa fa-bar-chart-o"></i> Notes</a></li>
             <li><a href="#"><i class="fa fa-bar-chart-o"></i> Live chat</a></li>
              <li><a href="#"><i class="fa fa-bar-chart-o"></i>Help</a></li>
         </ul>
            </security:authorize>
          <ul class="nav navbar-nav navbar-right navbar-user">
            <li class="dropdown messages-dropdown">
              <a href="#" class="dropdown-toggle" data-toggle="dropdown"><i class="fa fa-envelope"></i> Messages <span class="badge" style="background:#FB8805;"></span> <b class="caret"></b></a>
              <ul class="dropdown-menu">
                <li class="dropdown-header">7 New Messages</li>
                <li class="message-preview">
                  <a href="#">
                    <span class="avatar"><img src="http://placehold.it/50x50"></span>
                    <span class="name">John Smith:</span>
                    <span class="message">Hey there, I wanted to ask you something...</span>
                    <span class="time"><i class="fa fa-clock-o"></i> 4:34 PM</span>
                  </a>
                </li>
                <li class="divider"></li>
                <li class="message-preview">
                  <a href="#">
                    <span class="avatar"><img src="http://placehold.it/50x50"></span>
                    <span class="name">John Smith:</span>
                    <span class="message">Hey there, I wanted to ask you something...</span>
                    <span class="time"><i class="fa fa-clock-o"></i> 4:34 PM</span>
                  </a>
                </li>
                <li class="divider"></li>
                <li class="message-preview">
                  <a href="#">
                    <span class="avatar"><img src="http://placehold.it/50x50"></span>
                    <span class="name">John Smith:</span>
                    <span class="message">Hey there, I wanted to ask you something...</span>
                    <span class="time"><i class="fa fa-clock-o"></i> 4:34 PM</span>
                  </a>
                </li>
                <li class="divider"></li>
                <li><a href="#">View Inbox <span class="badge">7</span></a></li>
              </ul>
            </li>
            <li class="dropdown alerts-dropdown">
              <a href="#" class="dropdown-toggle" data-toggle="dropdown"><i class="fa fa-bell"></i> Alerts <span id="alertId" class="badge" style="background:#FF0404;">3</span> <b class="caret"></b></a>
              <ul class="dropdown-menu">
                <li><a href="#">Default <span class="label label-default">Default</span></a></li>
                <li><a href="#">Primary <span class="label label-primary">Primary</span></a></li>
                <li><a href="#">Success <span class="label label-success">Success</span></a></li>
                <li><a href="#">Info <span class="label label-info">Info</span></a></li>
                <li><a href="#">Warning <span class="label label-warning">Warning</span></a></li>
                <li><a href="#">Danger <span class="label label-danger">Danger</span></a></li>
                <li class="divider"></li>
                <li><a href="#">View All</a></li>
              </ul>
            </li>
            <li class="dropdown user-dropdown">
              <a href="#" class="dropdown-toggle" data-toggle="dropdown"><i class="fa fa-user"></i> <security:authentication property="principal.username" /> <b class="caret"></b></a>
              <ul class="dropdown-menu">
                <li><a href="#"><i class="fa fa-user"></i> Profile</a></li>
                <li><a href="#"><i class="fa fa-envelope"></i> Inbox <span class="badge">7</span></a></li>
                <li><a href="#"><i class="fa fa-gear"></i> Settings</a></li>
                <li class="divider"></li>
                  <security:authorize access="isAuthenticated()">
                   <li><a href='<c:url value="/j_spring_security_logout"/>'><i class="fa fa-power-off"></i> Log Out</a></li>
                    </security:authorize>
              </ul>
            </li>
          </ul>
           </security:authorize>
        </div><!-- /.navbar-collapse -->
      </nav>
      <div id="page-wrapper">