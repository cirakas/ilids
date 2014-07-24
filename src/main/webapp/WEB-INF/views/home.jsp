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
       fromDateParam="7/19/2014"//new Date().toLocaleDateString();
   }
   if(!toDateParam){
       toDateParam="7/19/2014"//new Date().toLocaleDateString();
   }
  var servlet = "DataAccessServlet?phase="+phaseParam+"&fromDate="+fromDateParam+"&toDate="+toDateParam;
 
 switch(phaseParam){
      case '00':
//        headTitle="Phase1 Voltage vs Time";
        yaxisTitle= "Phase1 Voltage";
      break;
      
      case '02':
//        headTitle="Phase2 Voltage vs Time";
        yaxisTitle= "Phase2 Voltage";
      break;
      
      case '04':
//        headTitle="Phase3 Voltage vs Time";
        yaxisTitle= "Phase3 Voltage";
      break;
     
      case '06':
//        headTitle="Phase1 Current vs Time";
        yaxisTitle= "Phase1 Current";
      break;
      case '08': 
//        headTitle="Phase2 Current vs Time";
        yaxisTitle= "Phase2 Current"; 
      break; 
      case '10':
//        headTitle="Phase3 Current vs Time";
        yaxisTitle= "Phase3 Current";
      break;
      case '12':
//        headTitle="Phase1 Power vs Time";
        yaxisTitle= "Phase1 Power";
      break;
      case '14':
//        headTitle="Phase2 Power vs Time";
        yaxisTitle= "Phase2 Power";
      break;
      case '16':  
//        headTitle="Phase3 Power vs Time";
        yaxisTitle= "Phase3 Power"; 
        break;
      case '30':  
        headTitle="Phase1 Power Factor vs Time";
        yaxisTitle= "Phase1 Power Factor"; 
        break;
    case '32':  
        headTitle="Phase2 Power Factor vs Time";
        yaxisTitle= "Phase2 Power Factor"; 
        break;
      case '34':  
        headTitle="Phase3 Power Factor vs Time";
        yaxisTitle= "Phase3 Power Factor"; 
        break;
    case '512':  
        headTitle="Cumulative Energy kVAh vs Time";
        yaxisTitle= "Cumulative Energy kVAh"; 
        break;
     case '514':  
        headTitle="Cumulative Energy kWh vs Time";
        yaxisTitle= "Cumulative Energy kWh"; 
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
<script language="JavaScript" src="${resources}ilids-template/js/polling.js" type="text/javascript"></script>



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
  stroke-width: 4;
}

.line.line1 {
  stroke:#0D98B3;
  stroke-width: 4;
}

.line.line0 {
  stroke:#0101DF ;
  stroke-width: 4;
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
  fill: yellow;
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
  stroke: red;
  stroke-dasharray: 5 5;
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
.power-factor{
/*    height: 20px;*/
    padding-top: 0px;
    width: 220px;
    padding: 5px 0;
    text-align: center;
    color: #fff;
}
.power-factor-panel{
   margin-left: 4px;
   float: left;
}
.text-announce{
    background: #17a668;color:#fff;width: 100%;padding: 10px 0 10px 5px;
    text-align: center;
    font-size: 11px;
}
.panel-heading1{padding-left: 0;padding-right: 0;padding-bottom: 0;}


 </style> 
 
        
   
         <div class="row">
           <div class="col-lg-3">
             <div class="form-group" style="float:left;margin-top: 6px;">
                <label></label>
                <select class="form-control form_new _" id="graphType" value="" onchange="selectFunction()">
                    <option value="00" style="background: #fff;">Phase1 Voltage Vs Time</option>
                    <option value="02">Phase2 Voltage Vs Time</option>
                    <option value="04">Phase3 Voltage Vs Time</option>
                    <option value="06">Phase1 Current Vs Time</option>
                    <option value="08">Phase2 Current Vs Time</option>
                    <option value="10">Phase3 Current Vs Time</option>
                    <option value="12">Phase1 Power Vs Time</option>
                    <option value="14">Phase2 Power Vs Time</option>
                    <option value="16">Phase3 Power Vs Time</option>
<!--                    <option value="30">Phase1 Power Factor Vs Time</option>
                    <option value="32">Phase2 Power Factor Vs Time</option>
                    <option value="34">Phase3 Power Factor Vs Time</option>
                    <option value="512">Cumulative Energy kVAh Vs Time</option>
                    <option value="514">Cumulative Energy kWh Vs Time</option>-->
                  
                </select>
             </div>
           </div>
             
             <div class="col-lg-3" style="width: 50%;float: left;">
                <div class="form-group" style="float:left;">
                    <label style="color:#047187;">FROM</label><br>
                    <input type="text" name="SelectedDate" class=" input_" id="SelectedDate" readonly onClick="GetDate(this) ;" value="" onchange="selectFunction()" />
                </div>
                <div class="form-group" style="float:left;margin-left: 30px;">
                  <label style="color:#047187;">TO</label><br>
                  <input type="text" name="SelectedDate" class=" input_" id="SelectedDate1" readonly onClick="GetDate(this);" value="" onchange="selectFunction()" />
                </div>  
             </div>
         </div> 
         

 <div class="row" style="margin-bottom: 0;">
            <div class="col-lg-6" style="width:77%;">
              <div class="panel panel-primary" style="margin-bottom: 0;height: 430px; ">
                <div class="panel-heading">
                  <h3 class="panel-title"><i class="fa fa-bar-chart-o"></i> <script type="text/javascript">
                    document.write(headTitle);
                        </script>                 
                  </h3>
                </div>
                  <div class="panel-body" style="width: 100%;height: 390px;">
                    <div class="flot-chart">
                      <div class="flot-chart-content" id="powGraph" style="height: 365px;"></div>
                    </div>
                  </div>
            </div>
          </div>
              
              
     <div style="float: right;width: 222px;">
         <div class="" style="float: right;">
            <div id="energyCostPanel" class="panel panel-success" style="width: 220px;border:1px solid #14a164!important;">
                     <div  class="panel-heading panel-heading1" style="background: #3ac98b;border:none;">
                               <img src="/ilids/resources/images/bulb_green1.png" style="margin-left: 50px;">
                               <div id="energyCost" class="text-announce" style="">
                                <p id="energyCostValue" class="announcement-text" style="padding: 5px;"></p>
                                </div>
                     </div>
            </div>
          </div>
            <div class=""style="float: right; width: 222px;">
                 <div id="phase1PowerFactorPanel" class="panel power-factor-panel" style="padding: 9px 0;background: #e75151;margin-left: 0;">
                     <div  class="power-factor" style="border:none;">
                                <div id="phase1PowerFactor">
                                    <p class="announcement-text">Power factor Phase 1: ${phase1PowerFactor}</p>
                                </div>
                     </div>
                 </div>
                      <div id="phase2PowerFactorPanel" class="panel power-factor-panel " style="padding: 9px 0;background: #e75151;">
                        <div  class="power-factor" style="border:none;">
                                  <div id="phase2PowerFactor">
                                    <p class="announcement-text">Power factor Phase 2: ${phase2PowerFactor}</p>
                                  </div>
                        </div>
                      </div>   
                    <div id="phase3PowerFactorPanel"  class="panel power-factor-panel " style="padding: 9px 0;background: #e75151;">
                     <div class="power-factor" style="border:none;">
                                <div id="phase3PowerFactor">
                                     <p class="announcement-text">Power factor Phase 3: ${phase3PowerFactor}</p>
                                </div>
                     </div>
                    </div>              
           </div>
     </div>
  </div>
      
  <tbody>



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

<script type="text/javascript">
	$(document).ready(function() {
		var startUrl = "dashboardupdate/subscribe";
		var pollUrl = "dashboardupdate/polldata";
		var poll = new Poll();
		poll.start(startUrl,pollUrl);
	});
        
        $(function() {
                var fromDateParams=document.getElementById("SelectedDate").value;
                var toDateParams=document.getElementById("SelectedDate1").value;
                var energyRequestUrl = "dashboardupdate/energyCost";
                var energyValueDiv=0;
                var alertSpan=0;
                if (energyRequest) {
			energyRequest.abort(); // abort any pending request
		}
             
		// fire off the request to MatchUpdateController
		var energyRequest = $.ajax({
			url : energyRequestUrl,
			type : "get",
                        data : { "startDate":fromDateParams, "endDate":toDateParams},
                        success: function(pollData) {
                            energyValueDiv='<p id="energyCostValue" class="announcement-text">Energy Cost: Rs.'+ Number(pollData.energyCost)+'</p>';
                               alertSpan='<span id="alertCountId" style="color: red;">'+Number(pollData.alertCount)+'</span>';
                              $(energyValueDiv).replaceAll('#energyCostValue');
                            $(alertSpan).replaceAll('#alertCountId');
                        
                    }
		});
        });
        
</script>
