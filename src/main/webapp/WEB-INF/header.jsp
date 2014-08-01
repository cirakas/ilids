<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
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
    <link href="${resources}ilids-template/css/bootstrap-datetimepicker.min.css" rel="stylesheet" media="screen">
    
    <!-- Add custom CSS here -->
    <link href="${resources}ilids-template/css/sb-admin.css" rel="stylesheet">
    <link rel="stylesheet" href="${resources}ilids-template/font-awesome/css/font-awesome.min.css">
    <!-- Page Specific CSS -->
<!--    <link href="css/dc.css" rel="stylesheet">-->
    
    
<!--    <link rel="stylesheet" href="http://cdn.oesmith.co.uk/morris-0.4.3.min.css">-->
<style>
    .color-menu li a{outline:none;}
    .color-menu li{border-top: 1px solid #343434;}
    .color-menu li:first-child{border: none;}
    body{font: 14px sans-serif;outline: none!important;}
    .demo-class{
        cursor: default;
    }
/*    .thead_style{background: #0093c3!important;}
   .thead_style tr td{text-align: center!important;}*/
</style>

<script type="text/javascript">
    function latestAlertRequest(){
        var fromDateAlert=document.getElementById("SelectedDate").value;
        var toDateAlert=document.getElementById("SelectedDate1").value;
        var alertUrl = "dashboardupdate/alertList";
       $.post(alertUrl,{'startDate':fromDateAlert, 'endDate':toDateAlert},function(data) {
            // $('#demo').html( '<table cellpadding="0" cellspacing="0" border="0" class="display" id="example"></table>' );
               var theTable = "<tbody id=\"alertBody\">";
      for(var j=0;j<data.length;j++){
          var currentDates=new Date(Number(data[j][2]));
          theTable += '<tr><td>Chiller</td><td>'+data[j][5]+'</td><td>'+data[j][1]+'</td><td>'+currentDates.getDate()+'/'+(currentDates.getMonth()+1)+'/'+currentDates.getFullYear()+' '+currentDates.getHours()+':'+currentDates.getMinutes()+':'+currentDates.getSeconds()+'</td>';
          theTable += '</tr>';
      }
      theTable+="</tbody>";
        $(theTable).replaceAll("#alertBody");
       });
    }
</script>

  </head>

  <body>

    <div id="wrapper">

      <!-- Sidebar -->
      <nav class="navbar navbar-inverse navbar-fixed-top"  role="navigation" style="border:none;">
        <!-- Brand and toggle get grouped for better mobile display -->
        <div class="navbar-header">
          <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-ex1-collapse">
            <span class="sr-only">Toggle navigation</span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
          </button>
          <a class="navbar-brand" href="home" style="outline: none;font-size: 25px;color:#e8e7e7;">iLids</a>
        </div>

        <!-- Collect the nav links, forms, and other content for toggling -->
        <div class="collapse navbar-collapse navbar-ex1-collapse">
            <c:url value="/systemsettings" var="sysurl"/>
            <security:authorize access="isAuthenticated()">
               
              <security:authorize access="hasRole('ROLE_ADMIN')">
            <ul class="nav navbar-nav side-nav color-menu"  style="background: #272727;">
            <li class="dropdown">
              <a href="#" class="dropdown-toggle" data-toggle="dropdown"><i class=""><img src="/ilids/resources/images/manage_.png"></i> User Management<div class="active_arrow"></div><b class="caret"></b></a>
              <ul class="dropdown-menu">
                <li><a href='<c:url value="/user"/>'>Users</a></li>
              <li><a href="<c:url value="/role"/>"> Roles</a></li>
              </ul>
            </li>
             <li><a href="<c:url value="/systemsettings"/>"><i class=""><img src="/ilids/resources/images/system_.png"></i> System settings<div class="active_arrow"></div></a></li>
             <li><a href="<c:url value="/devices"/>"><i class=""><img src="/ilids/resources/images/mbl_.png"></i> Devices<div class="active_arrow"></div></a></li>
             <li><a href="#"><i class=""><img src="/ilids/resources/images/chart_.png"></i> Charts<div class="active_arrow"></div></a></li>
             <li><a href="#"><i class=""><img src="/ilids/resources/images/alerts_.png"></i> Alerts<div class="active_arrow"></div></a></li>
             <li><a  href="<c:url value="/mailsms"/>"><i class=""><img src="/ilids/resources/images/sms_.png"></i> E-mail/SMS settings<div class="active_arrow"></div></a></li>
             <li><a href="<c:url value="/note/add"/>"><i class=""><img src="/ilids/resources/images/notes_.png"></i> Notes<div class="active_arrow"></div></a></li>
             <li><a href="#"><i class=""><img src="/ilids/resources/images/chat_.png"></i> Live chat<div class="active_arrow"></div></a></li>
            
         </ul>
            </security:authorize>
              <!--
                <security:authorize access="hasRole('ROLE_ADMIN')">
            <ul class="nav navbar-nav side-nav color-menu"  style="background: #272727;">
            <li class="dropdown">
              <a href="#" class="dropdown-toggle demo-class" ><i class=""><img src="/ilids/resources/images/manage_.png"></i> User Management<div class="active_arrow"></div><b class="caret"></b></a>
              <ul class="dropdown-menu">
                <li><a  class="demo-class" href="#">Users</a></li>
              <li><a  class="demo-class" href="#"> Roles</a></li>
              </ul>
            </li>
            <li><a  class="demo-class" href="#"><i class=""><img src="/ilids/resources/images/system_.png"></i> System settings<div class="active_arrow"></div></a></li>
             <li><a  class="demo-class" href="#"><i class=""><img src="/ilids/resources/images/mbl_.png"></i> Devices<div class="active_arrow"></div></a></li>
             <li><a  class="demo-class" href="#"><i class=""><img src="/ilids/resources/images/chart_.png"></i> Charts<div class="active_arrow"></div></a></li>
             <li><a  class="demo-class" href="#"><i class=""><img src="/ilids/resources/images/alerts_.png"></i> Alerts<div class="active_arrow"></div></a></li>
             <li><a  class="demo-class" href="#"><i class=""><img src="/ilids/resources/images/sms_.png"></i> E-mail/SMS settings<div class="active_arrow"></div></a></li>
             <li><a  class="demo-class" href="#"><i class=""><img src="/ilids/resources/images/notes_.png"></i> Notes<div class="active_arrow"></div></a></li>
             <li><a  class="demo-class" href="#"><i class=""><img src="/ilids/resources/images/chat_.png"></i> Live chat<div class="active_arrow"></div></a></li>
            
         </ul>
            </security:authorize>-->
             <!--
             <security:authorize access="hasRole('ROLE_USER')">
            <ul class="nav navbar-nav side-nav"  style="outline:none; ">
             <li><a href="#"><i class=""><img src="/ilids/resources/images/chart_.png"></i> Charts<div class="active_arrow"></div></a></li>
             <li><a href="#"><i class=""><img src="/ilids/resources/images/alerts_.png"></i> Alerts<div class="active_arrow"></div></a></li>
            <li><a href="<c:url value="/note/add"/>"><i class=""><img src="/ilids/resources/images/notes_.png"></i> Notesv</a></li>
             <li><a href="#"><i class=""><img src="/ilids/resources/images/chat_.png"></i> Live chat<div class="active_arrow"></div></a></li>
              <li><a href="#"><i class="fa fa-bar-chart-o"></i>Help<div class="active_arrow"></div></a></li>
         </ul>
            </security:authorize>-->
            
          <security:authorize access="hasRole('ROLE_USER')">
            <ul class="nav navbar-nav side-nav"  style="outline:none; ">
             <li><a   class="demo-class" href="#"><i class=""><img src="/ilids/resources/images/chart_.png"></i> Charts<div class="active_arrow"></div></a></li>
             <li><a  class="demo-class" href="#"><i class=""><img src="/ilids/resources/images/alerts_.png"></i> Alerts<div class="active_arrow"></div></a></li>
            <li><a  class="demo-class" href="#"><i class=""><img src="/ilids/resources/images/notes_.png"></i> Notesv</a></li>
             <li><a  class="demo-class" href="#"><i class=""><img src="/ilids/resources/images/chat_.png"></i> Live chat<div class="active_arrow"></div></a></li>
              <li><a  class="demo-class" href="#"><i class="fa fa-bar-chart-o"></i>Help<div class="active_arrow"></div></a></li>
         </ul>
            </security:authorize>
                <ul class="nav navbar-nav navbar-right navbar-user">
            <li class="dropdown messages-dropdown">
              <a href="#" class="dropdown-toggle" data-toggle="dropdown"><i class="fa fa-envelope"></i> Messages <span class="badge" style="background:#FB8805;"></span> <b class="caret"></b></a>
              <ul class="dropdown-menu">
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
                <li class="divider"></li>
                <li><a href="#">View Inbox <span class="badge">7</span></a></li>
              </ul>
            </li>
            <li class="dropdown alerts-dropdown">
                <a href="#" class="dropdown-toggle" data-toggle="dropdown"><i class="fa fa-bell"></i> <span id="alertCountId" style="color: red;">${alertCount}</span> Alerts <span id="alertId" class="badge" style="background:#FF0404;"></span> <b class="caret"></b></a>
              <ul class="dropdown-menu">
<!--            <li><a href="#">Default <span class="label label-default">Default</span></a></li>
                <li><a href="#">Primary <span class="label label-primary">Primary</span></a></li>
                <li><a href="#">Success <span class="label label-success">Success</span></a></li>
                <li><a href="#">Info <span class="label label-info">Info</span></a></li>-->
                <li><a href="#">Warning <span class="label label-warning">Warning</span></a></li>
                <li><a href="#">Danger <span class="label label-danger">Danger</span></a></li>
                <li class="divider"></li>
                <li><a href="#" onclick="latestAlertRequest();" data-toggle="modal" data-target="#myAlertModal">View All</a></li>
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
      
      <!-- Modal to show alerts -->
<div class="modal fade" id="myAlertModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog" style="width:750px;">
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
                <ul class="pager">
                <li><a href="#">Previous</a></li>
                <li><a href="#">Next</a></li>
                </ul>
        </div>
    </div>
</div>
      
      <div id="page-wrapper">