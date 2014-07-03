<%@page import="org.springframework.security.core.userdetails.User"%>
<%@page import="org.springframework.security.core.context.SecurityContextHolder"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<script type="text/javascript">
    var _gaq = _gaq || [];
    _gaq.push(['_setAccount', 'UA-33628816-1']);
    _gaq.push(['_trackPageview']);

    (function () {
        var ga = document.createElement('script');
        ga.type = 'text/javascript';
        ga.async = true;
        ga.src = ('https:' == document.location.protocol ? 'https://ssl' : 'http://www') + '.google-analytics.com/ga.js';
        var s = document.getElementsByTagName('script')[0];
        s.parentNode.insertBefore(ga, s);
    })();
    
   
</script>

<script type="text/javascript">

var mdvValue1 = '<c:out value="${SystemSettings.mdv}"/>';
var myArray=null;
myArray= '<c:out value="${dataList}"/>';
console.log("----kkkk--"+myArray.length);

</script>

<!--<script type="text/javascript">

var datalist = '<c:out value="${Data.dataList.size()}"/>';

</script>-->



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
  stroke-width: 0.75px;
}

.line.line1 {
  stroke:#0D98B3;
}

.line.line0 {
  stroke:#290AF2 ;
}

.line.line3 {
  stroke:#FFCC00 ;
}

.svg line{
 fill: none;  
}
.svg line.x{
 stroke: #FF0000   
}

.svg line{
    stroke: #FF0000
}

.overlay {
  fill: none;
  pointer-events: all;
}

.focus circle {
  fill: none;
}

.focus circle.y0 {
  stroke: blue;
}

.focus circle.y1 {
  stroke: red;
}

.focus line {
  stroke: purple;
  shape-rendering: crispEdges;
}

.focus line.y0 {
  stroke: steelblue;
  stroke-dasharray: 3 3;
  opacity: .5;
}

.focus line.y1 {
  stroke: indianred;
  stroke-dasharray: 3 3;
  opacity: .5;
}

.brush .extent {
  stroke: #fff;
  fill-opacity: .125;
  shape-rendering: crispEdges;
} 


.axis path {
  fill: none;
  stroke: #000;
  shape-rendering: crispEdges;
}

/*.grid .tick {
    stroke: lightgrey;
    opacity: 0.7;
}
.grid path {
      stroke-width: 0;
}
.grid .tick {
    stroke: lightgrey;
    opacity: 0.7;
}
.grid path {
      stroke-width: 0;
}*/

 </style> 
        <div class="row">
          <div class="col-lg-12">
            <h1>Dashboard <small>Statistics Overview</small></h1>
            <ol class="breadcrumb">
              <li class="active"><i class="fa fa-dashboard"></i> Dashboard</li>
            </ol>
<!--            <div class="alert alert-success alert-dismissable">
              <button type="button" class="close" data-dismiss="alert" aria-hidden="true">&times;</button>
              Welcome to ILIDS
            </div>-->
          </div>
        </div><!-- /.row -->
        
        
         <div class="row">
          <div class="col-lg-12">
            <div class="panel panel-primary" style="height: 600px;">
              <div class="panel-heading">
                <h3 class="panel-title"><i class="fa fa-bar-chart-o"></i> Phase1 Current vs Time Graph </h3>
              </div>
              <div class="panel-body">
                <div class="flot-chart">
                  <div class="flot-chart-content" id="powGraph"></div>
                </div>
              </div>
            </div>
          </div>
        </div><!-- /.row -->
        
        
        <div class="row">
          <div class="col-lg-12">
<!--            <h2>Charts</h2>-->
            <div class="panel panel-primary" style="height: 600px;">
              <div class="panel-heading">
                <h3 class="panel-title"><i class="fa fa-bar-chart-o"></i> Current vs Time Graph </h3>
              </div>
              <div class="panel-body">
                <div class="flot-chart">
                  <div class="flot-chart-content" id="linGraph"></div>
                </div>
              </div>
            </div>
          </div>
        </div><!-- /.row -->
        
        <div class="row" style="margin-top:70px;">
            
            
        </div>
        
       

        
  <tbody>
               <c:forEach var="data" items="${DataList}">
                   
                   console.log("--------"+${DataList})
<!--		
                 </c:forEach> 
        
        
        
<!-- <div class="row">
         <div class="container">
   <div id="dashboard" style="display:block;">
        <div>
         <h3 style="font-size: 20px;">Current vs Time </h3>
         <div id="linGraph" style=" float: left;margin-top: 0px;width:100%;" ></div>
         </div>
       <div>
          <h3 style="font-size: 20px;">Power vs Time </h3>
         <div id="powGraph" style=" float: left;margin-top: 0px;width:100%;" ></div>
         
       </div>
       
         <table class="table table-hover dc-data-table">
        <thead>
        <tr class="header">
            <th>date</th>
            <th>voltage</th>
            <th>current</th>
            <th>power</th>
            
       </tr>
        </thead>
    </table>
   </div>   
       <div class="clearfix"></div>
       
   </div>  
         </div>-->
<!--welcome <security:authentication property="principal.username" />

-->

<!--<div style="width: 200px;height: 100px; float: right;">
    Learn crud operations
     <security:authorize access="hasRole('ROLE_ADMIN')">
         <li><a href='<c:url value="/admin"/>'>Admin</a></li>
    </security:authorize>
        <security:authorize access="isAuthenticated()">
           <li><a href='<c:url value="/book/add"/>'>Books</a></li>
     </security:authorize>

</div>-->

<br/>
<!--Logged-in Users</h1><table>
<tr>
<td width="100">Username</td>
<td width="150">Authorities</td>
<td width="170">IsAccountNonExpired</td>
<td width="190">IsCredentialsNonExpired</td>
<td width="150">IsAccountNonLocked</td>
</tr>
<c:forEach items="${users}" var="user">
<tr>
<td><a href='<c:url value="/admin/invalidate/${user.username}"/>'><c:out value="${user.username}"/></a></td>
<td><c:out value="${user.authorities}" /></td>
<td><c:out value="${user.accountNonExpired}" /></td>
<td><c:out value="${user.credentialsNonExpired}" /></td>
<td><c:out value="${user.accountNonLocked}" /></td>
</tr>
</c:forEach>


</table>-->
<!--${SystemSettings.mdv}-->

<script type="text/javascript" src="${resources}ilids-d3/js/d3.js" charset="utf-8"></script>
<script type="text/javascript" src="${resources}ilids-d3/js/crossfilter.js"></script>
<script type="text/javascript" src="${resources}ilids-d3/js/dc.js"></script>
<script type="text/javascript" src="${resources}ilids-d3/js/jquery.min.js"></script>
<script type="text/javascript" src="${resources}ilids-d3/graph.js"></script>
<script type="text/javascript" src="${resources}ilids-d3/powGrap.js"></script>
<script type="text/javascript" src="${resources}ilids-d3/js/dimple.v2.0.0.min.js"></script>