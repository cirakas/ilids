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
    
  var mdvValue1 = '<c:out value="${SystemSettings.mdv}"/>';
 
  var phaseParam=getQueryVariable('phase');
  var fromDateParam=getQueryVariable('fromDate');
  var toDateParam=getQueryVariable('toDate');
    if(!phaseParam){
       phaseParam='06';
   }
   if(!fromDateParam){
       fromDateParam=new Date().toLocaleDateString();
   }
   if(!toDateParam){
       toDateParam=new Date().toLocaleDateString();
   }
  var servlet = "DataAccessServlet?phase="+phaseParam+"&fromDate="+fromDateParam+"&toDate="+toDateParam;
 
 switch(phaseParam){
      case '06':
        headTitle="Phase1 Current vs Time";
        yaxisTitle= "Phase1 Current";
      break;
      case '08': 
        headTitle="Phase2 Current vs Time";
        yaxisTitle= "Phase2 Current"; 
      break; 
      case '10':
        headTitle="Phase3 Current vs Time";
        yaxisTitle= "Phase3 Current";
      break;
      case '12':
        headTitle="Phase1 Power vs Time";
        yaxisTitle= "Phase1 Power";
      break;
      case '14':
        headTitle="Phase2 Power vs Time";
        yaxisTitle= "Phase2 Power";
      break;
      case '16':  
        headTitle="Phase3 Power vs Time";
        yaxisTitle= "Phase3 Power"; 
        break;
      default :
        headTitle="Phase1 Current vs Time";
        yaxisTitle= "Phase1 Current";
      
    }
   
   function selectFunction()
   {
    var graphType=document.getElementById("graphType").value;   
    var fromDate=document.getElementById("SelectedDate").value;
    var toDate=document.getElementById("SelectedDate1").value;
   var d1 = new Date(fromDate)
   var d2 = new Date(toDate)
    if(d2<d1){
        alert("end date should not be less than start date");
    }
    else{
    var myURL = window.location.protocol + "//" + window.location.host + window.location.pathname;
    document.location = myURL + "?phase="+graphType+"&fromDate="+fromDate+"&toDate="+toDate;
    }
   }

    function getQueryVariable(variable) 
    {
       var query = window.location.search.substring(1);
       var vars = query.split("&");
       for (var i=0;i<vars.length;i++) {
       var pair = vars[i].split("=");
       if (pair[0] == variable) {
         return pair[1];
       }
      }
    }



    
    
</script>

<script language="JavaScript" src="${resources}ilids-template/js/htmlDatePicker.js" type="text/javascript"></script>
<link href="${resources}ilids-template/css/htmlDatePicker.css" rel="stylesheet" />


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
  stroke:#0101DF ;
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

.grid .tick {
    stroke: lightgrey;
    opacity: 0.7;
}
.grid path {
      stroke-width: 0;
}

 </style> 
 
        <div class="row">
          <div class="col-lg-12">
            <h1>Dashboard <small>Statistics Overview</small></h1>
            <ol class="breadcrumb">
              <li class="active"><i class="fa fa-dashboard"></i> Dashboard</li>
            </ol>
          </div>
        </div>
        
         <div class="row">
           <div class="col-lg-3">
             <div class="form-group" style="float:left;">
                <label>Graph type</label>
                <select class="form-control" id="graphType" value="" onchange="selectFunction()">
                    <option value="06">Phase1 Current Vs Time</option>
                    <option value="08">Phase2 Current Vs Time</option>
                    <option value="10">Phase3 Current Vs Time</option>
                    <option value="12">Phase1 Power Vs Time</option>
                    <option value="14">Phase2 Power Vs Time</option>
                    <option value="16">Phase3 Power Vs Time</option>
                  
                </select>
             </div>
           </div>
             
             <div class="col-lg-3" style="width: 700px;float: left;">
             <div class="form-group" style="float:left;">
                 <label>start date</label><br>
                <input type="text" name="SelectedDate" id="SelectedDate" readonly onClick="GetDate(this) ;" value="" onchange="selectFunction()" />
             </div>
                <div class="form-group" style="float:left;margin-left: 30px;">
                  <label>end date</label><br>
                  <input type="text" name="SelectedDate" id="SelectedDate1" readonly onClick="GetDate(this);" value="" onchange="selectFunction()" />
               </div>   
           </div>
            </div> 
         

          <div class="row">
            <div class="col-lg-12">
              <div class="panel panel-primary" style="height: 600px;">
                <div class="panel-heading">
                  <h3 class="panel-title"><i class="fa fa-bar-chart-o"></i> <script type="text/javascript">
                    document.write(headTitle);
                        </script> 
                  
                  </h3>
                </div>
              <div class="panel-body">
                <div class="flot-chart">
                  <div class="flot-chart-content" id="powGraph"></div>
                </div>
              </div>
            </div>
          </div>
        </div>
        
  <tbody>
<br/>

  <script type="text/javascript">
     document.getElementById("graphType").value=phaseParam;
     document.getElementById("SelectedDate").value=fromDateParam;
     document.getElementById("SelectedDate1").value=toDateParam; 
       
  </script>
  
<script type="text/javascript" src="${resources}ilids-d3/js/d3.js" charset="utf-8"></script>
<!--<script type="text/javascript" src="${resources}ilids-d3/js/crossfilter.js"></script>-->
<!--<script type="text/javascript" src="${resources}ilids-d3/js/dc.js"></script>-->
<script type="text/javascript" src="${resources}ilids-d3/js/jquery.min.js"></script>
<!--<script type="text/javascript" src="${resources}ilids-d3/graph.js"></script>-->
<script type="text/javascript" src="${resources}ilids-d3/powGrap.js"></script>
<!--<script type="text/javascript" src="${resources}ilids-d3/js/dimple.v2.0.0.min.js"></script>-->
