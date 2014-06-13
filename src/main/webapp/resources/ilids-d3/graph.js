
'use strict';

d3.csv("resources/ilids-d3/writedat122.csv", function (data) {
      console.log('---------');
    /* since its a csv file we need to format the data a bit */
    var dateFormat = d3.time.format("%b/%d/%Y %H:%M:%S");
   //var timeFormat = d3.time.format("hh:mm:ss")
    //console.dir(dateFormat);
    var numberFormat = d3.format(".2f");
    data.forEach(function (d) {
         console.log("------gg-----date"+d.date);
        d.dd = dateFormat.parse(d.date);
        d.lineDate =dateFormat.parse(d.date);
        d.month = d3.time.month(d.dd); // pre-calculate month for better performance
        d.current = +d.current; // coerce to number
        d.voltage = +d.voltage;
        d.power = +d.power;
        d.threshold = +d.threshold;
        d.cost = +d.power * 8;
      });
    var ndx = crossfilter(data);
    var all = ndx.groupAll();
    

      
    var main_margin = {top: 20, right: 60, bottom: 100, left: 110},
        mini_margin = {top: 430, right: 60, bottom: 20, left: 110},
        main_width = 660 - main_margin.left - main_margin.right,
        main_height = 500 - main_margin.top - main_margin.bottom,
        mini_height = 500 - mini_margin.top - mini_margin.bottom;
//        
//        var margin = {top: 100, right: 100, bottom: 100, left: 100},
//    width = 960 - margin.left - margin.right,
//    height = 500 - margin.top - margin.bottom;
//
//var x = d3.scale.identity()
//    .domain([0, width]);
//
//var xAxis = d3.svg.axis()
//    .scale(x)
//    .ticks(0)
//    .orient("bottom");

    var formatDate = d3.time.format("%H:%M:%S"),

        parseDate = formatDate.parse,
        bisectDate = d3.bisector(function(d) { return d.datee; }).left,
        formatOutput0 = function(d) { return formatDate(d.datee) + " - "  + d.currents+" A "; };
       
     //  formatOutput1 = function(d) { return   formatDate(d.datee) +"\n"+ " - " +"\n"+ d.power; };
     

var main_x = d3.time.scale()
   .range([0, main_width]),
    mini_x = d3.time.scale()
   .range([0, main_width]);

var main_y0 = d3.scale.sqrt()
   .range([main_height, 0]),
//    main_y1 = d3.scale.sqrt()
//   .range([main_height, 0]),
    mini_y0 = d3.scale.sqrt()
   .range([mini_height, 0]);
//    mini_y1 = d3.scale.sqrt()
//   .range([mini_height, 0]);

var main_xAxis = d3.svg.axis()
    .scale(main_x)
    .tickFormat(d3.time.format("%H:%M:%S"))
    .orient("bottom"),
    mini_xAxis = d3.svg.axis()
    .scale(mini_x)
    .tickFormat(d3.time.format("%H:%M:%S"))
    .orient("bottom");

var main_yAxisLeft = d3.svg.axis()
    .scale(main_y0)
    .orient("left");
//    main_yAxisRight = d3.svg.axis()
//    .scale(main_y1)
//    .orient("right");


//var y = d3.scale.linear()
//    .domain([0, 1e6])
//    .range([main_height, 0]);
//
//var x = d3.time.scale()
//    .domain([new Date(2010, 7, 1), new Date(2012, 7, 1)])
//    .range([0, main_width]);
//    
    
var brush = d3.svg.brush()
    .x(mini_x)
    .on("brush", brush3);

var main_line0 = d3.svg.line()
    .interpolate("cardinal")
    .x(function(d) { return main_x(d.datee); })
    .y(function(d) { return main_y0(d.currents); });

//var main_line1 = d3.svg.line()
//    .interpolate("cardinal")
//    .x(function(d) { return main_x(d.datee); })
//    .y(function(d) { return main_y1(d.power); });

var mini_line0 = d3.svg.line()
    .x(function(d) { return mini_x(d.datee); })
    .y(function(d) { return mini_y0(d.currents); });

//var mini_line1 = d3.svg.line()
//    .x(function(d) { return mini_x(d.datee); })
//    .y(function(d) { return mini_y1(d.power); });

//var x = d3.scale.identity()
//    .domain([0, width]);
//
//var xAxis = d3.svg.axis()
//    .scale(x)
//    .ticks(0)
//    .orient("bottom");


var svg = d3.select("#linGraph").append("svg")
    .attr("width", main_width + main_margin.left + main_margin.right)
    .attr("height", main_height + main_margin.top + main_margin.bottom);

svg.append("defs").append("clipPath")
    .attr("id", "clip")
  .append("rect")
    .attr("width", main_width)
    .attr("height", main_height);
    
svg.append( "line3" )
  .attr("x1", main_x( main_x.domain()[0] ) )
  .attr("y1", main_y0( 40 ) )   // whatever the y-val should be
 
  


var main = svg.append("g")
    .attr("transform", "translate(" + main_margin.left + "," + main_margin.top + ")");

var mini = svg.append("g")
    .attr("transform", "translate(" + mini_margin.left + "," + mini_margin.top + ")");

data.forEach(function(d) {
    d.datee = d.dd;
     //console.log("month"+ d.date);
/*var months = d.dd.getMonth();
        var name=["Jan","Feb","Mar","Apr","May","Jun","Jul","Aug","Sep","Oct","Nov","Dec"];
        var months1= name[months];*/
    d.currents =+d.current;
   // console.log("month"+ d.cost);
   
  
});
  data.sort(function(a, b) {

   //var dat= a.datee - b.datee;
  //  console.log("date"+dat);
    return a.datee - b.datee;
    
  });

  main_x.domain([data[0].datee, data[data.length - 1].datee]);
  main_y0.domain(d3.extent(data, function(d) { return d.currents; }));
  //main_y0.domain([0.1, d3.max(data, function(d) { return d.Durchschn; })]);
 // main_y1.domain(d3.extent(data, function(d) { return d.power; }));
  mini_x.domain(main_x.domain());
  mini_y0.domain(main_y0.domain());
 // mini_y1.domain(main_y1.domain());

  main.append("path")
      .datum(data)
      .attr("clip-path", "url(#clip)")
      .attr("class", "line line1")
      .attr("d", main_line0);

//  main.append("path")
//      .datum(data)
//      .attr("clip-path", "url(#clip)")
//      .attr("class", "line line1")
//      .attr("d", main_line1);

  main.append("g")
      .attr("class", "x axis")
      .attr("transform", "translate(0," + main_height + ")")
      .call(main_xAxis);

  main.append("g")
      .attr("class", "y axis axisLeft")
      .call(main_yAxisLeft)
    .append("text")
      .attr("transform", "rotate(-90)")
      .attr("y", 6)
      .attr("dy", ".71em")
      .style("text-anchor", "end")
      .text("Current");

//  main.append("g")
//      .attr("class", "y axis axisRight")
//      .attr("transform", "translate(" + main_width + ", 0)")
//      .call(main_yAxisRight)
//    .append("text")
//      .attr("transform", "rotate(-90)")
//      .attr("y", -12)
//      .attr("dy", ".71em")
//      .style("text-anchor", "end")
//      .text("Power");

  mini.append("g")
      .attr("class", "x axis")
      .attr("transform", "translate(0," + mini_height + ")")
      .call(main_xAxis);

  mini.append("path")
      .datum(data)
      .attr("class", "line")
      .attr("d", mini_line0);

//  mini.append("path")
//      .datum(data)
//      .attr("class", "line")
//      .attr("d", mini_line1);

  mini.append("g")
      .attr("class", "x brush")
      .call(brush)
    .selectAll("rect")
      .attr("y", -6)
      .attr("height", mini_height + 7);

  var focus = main.append("g")
      .attr("class", "focus")
      .style("display", "none");

  // Anzeige auf der Zeitleiste
  focus.append("line")
      .attr("class", "x")
      .attr("y1", main_y0(0) - 6)
      .attr("y2", main_y0(0) + 6);

  // Anzeige auf der linken Leiste
  focus.append("line")
      .attr("class", "y0")
      .attr("x1", main_width - 6) // nach links
      .attr("x2", main_width + 6); // nach rechts

  // Anzeige auf der rechten Leiste
  focus.append("line")
      .attr("class", "y1")
      .attr("x1", main_width - 6)
      .attr("x2", main_width + 6);

  focus.append("circle")
      .attr("class", "y0")
      .attr("r", 4);

  focus.append("text")
      .attr("class", "y0")
      .attr("dy", "-1em");

//  focus.append("circle")
//      .attr("class", "y1")
//      .attr("r", 4);
//
//  focus.append("text")
//      .attr("class", "y1")
//      .attr("dy", "-1em");

  main.append("rect")
      .attr("class", "overlay")
      .attr("width", main_width)
      .attr("height", main_height)
      .on("mouseover", function() { focus.style("display", null); })
      .on("mouseout", function() { focus.style("display", "none"); })
      .on("mousemove", mousemove1);



  function mousemove1() {
    var x0 = main_x.invert(d3.mouse(this)[0]),
        i = bisectDate(data, x0, 1),
        d0 = data[i - 1],
        d1 = data[i],
        d = x0 - d0.datee > d1.datee - x0 ? d1 : d0;
    focus.select("circle.y0").attr("transform", "translate(" + main_x(d.datee) + "," + main_y0(d.currents) + ")");
    focus.select("text.y0").attr("transform", "translate(" + main_x(d.datee) + "," + main_y0(d.currents) + ")").text(formatOutput0(d));
   // focus.select("circle.y1").attr("transform", "translate(" + main_x(d.datee) + "," + main_y1(d.power) + ")");
    //focus.select("text.y1").attr("transform", "translate(" + main_x(d.datee) + "," + main_y1(d.power) + ")").text(formatOutput1(d));
    focus.select(".x").attr("transform", "translate(" + main_x(d.datee) + ",0)");
    focus.select(".y0").attr("transform", "translate(" + main_width * -1 + ", " + main_y0(d.currents) + ")").attr("x2", main_width + main_x(d.datee));
    //focus.select(".y1").attr("transform", "translate(0, " + main_y1(d.power) + ")").attr("x1", main_x(d.datee));
  }


function brush3() {
  main_x.domain(brush.empty() ? mini_x.domain() : brush.extent()); 
  main.select(".line1").attr("d", main_line0);
//  main.select(".line1").attr("d", main_line1);
  main.select(".x.axis").call(main_xAxis);
}
    

//////////////////////////////////////////////////////////////////////////////



 var dateDimension = ndx.dimension(function (d) {
        return d.dd;
     //   console.log('-----'+dateDimension);
    });

     dc.dataTable(".dc-data-table")
        .dimension(dateDimension)
        // data table does not use crossfilter group but rather a closure
        // as a grouping function
        .group(function (d) {
            var format = d3.format("02d");
            return d.dd.getFullYear() + "/" + format((d.dd.getMonth() + 1));
        })
        .size(10) // (optional) max number of records to be shown, :default = 25
        // dynamic columns creation using an array of closures
        .columns([
            function (d) {
                return d.date;
            },
            function (d) {
                return numberFormat(d.voltage);
            },
            function (d) {
                return numberFormat(d.current);
            },
            function (d) {
                return numberFormat(d.power);
            }
           
            
         //   function (d) {
           //     return d.deviceID;
          //  }
        ])
        // (optional) sort using the given field, :default = function(d){return d;}
        .sortBy(function (d) {
            return d.dd;
        })
        // (optional) sort order, :default ascending
        .order(d3.ascending)
        // (optional) custom renderlet to post-process chart using D3
        .renderlet(function (table) {
            table.selectAll(".dc-table-group").classed("info", true);
        });

    
    dc.renderAll();
    
       
});
   d3.selectAll("#version").text(dc.version); 