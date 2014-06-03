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
<link href="${resources}css/bootstrap.css" rel="stylesheet">
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
            <div style="width:200px;height: 400px;;float: left; margin: 5px;">
              
            </div>  
                       <div class="container">
