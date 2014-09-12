'use strict';

d3.json(servlet, function (data) {
   var dateFormat = d3.time.format("%m/%d/%Y %I:%M:%S");
   var numberFormat = d3.format(".2f");
   data.forEach(function (d) {
        d.dateFn = dateFormat.parse(d.date);
        d.currents = numberFormat(d.current);
   });
   
   var mdvValue = mdvValue1;
   var mwidth=800;
    var main_margin = {top: 30, right: 60, bottom: 95, left: 100},
        mini_margin = {top: 345, right: 60, bottom: 20, left: 100},
        main_width = mwidth - main_margin.left - main_margin.right,
        main_height = 400 - main_margin.top - main_margin.bottom,
        mini_height = 400 - mini_margin.top - mini_margin.bottom;

    var formatDate = d3.time.format("%H:%M:%S"),

        parseDate = formatDate.parse,
        bisectDate = d3.bisector(function(d) { return d.datee; }).left,
        formatOutput0 = function(d) { return formatDate(d.datee) + " - "  + d.currents; };
       
    var main_x = d3.time.scale()
      .range([0, main_width]),
       mini_x = d3.time.scale()
      .range([0, main_width]);

    var main_y0 = d3.scale.sqrt()
      .range([270, 0]),
       mini_y0 = d3.scale.sqrt()
      .range([20, 0]);

   var main_xAxis = d3.svg.axis()
    .scale(main_x)
    .tickFormat(d3.time.format("%H:%M"))
    .orient("bottom"),
    mini_xAxis = d3.svg.axis()
    .scale(mini_x)
    .tickFormat(d3.time.format("%H:%M"))
    .orient("bottom");

var main_yAxisLeft = d3.svg.axis()
    .scale(main_y0)
    .orient("left")
    .ticks(4);
    
var main_line0 = d3.svg.line()
    .interpolate("linear")
    .x(function(d) { return main_x(d.datee); })
    .y(function(d) { return main_y0(d.currents); });
    
    var mainarea = d3.svg.area()
    .interpolate("linear")
    .x0(main_width)
    .x(function(d) { return main_x(d.datee); })
    .y0(280)
    .y1(function(d) { return main_y0(d.currents); }); 

var mini_line0 = d3.svg.line()
    .interpolate("linear")
    .x(function(d) { return mini_x(d.datee); })
    .y(function(d) { return mini_y0(d.currents); });
    
   var miniarea = d3.svg.area()
    .interpolate("linear")
    .x(function(d) { return mini_x(d.datee); })
    .y0(mini_height)
    .y1(function(d) { return mini_y0(d.currents); }); 

var svg = d3.select("#powGraph").append("svg")
    
    .attr("viewBox", "0 0 800 400");
    

var brush = d3.svg.brush()
    .x(mini_x)
    .on("brush", brush3);
    
svg.append("defs").append("clipPath")
    .attr("id", "clip")
    .append("rect")
    .attr("width", main_width)
    .attr("height", main_height);
    
    //vertical lines
svg.selectAll(".vline").data(d3.range(26)).enter()
    .append("line")
    .attr("x1", function (d) {
    return d * 40;
})
    .attr("x2", function (d) {
    return d * 40;
})
    .attr("y1", function (d) {
    return 0;
})
    .attr("y2", function (d) {
    return 1000;
})
    .style("stroke", "#eef0f1");

// horizontal lines
svg.selectAll(".vline").data(d3.range(26)).enter()
    .append("line")
    .attr("y1", function (d) {
    return d * 25;
})
    .attr("y2", function (d) {
    return d * 25;
})
    .attr("x1", function (d) {
    return 0;
})
    .attr("x2", function (d) {
    return 1000;
})
    .style("stroke", "#eef0f1");
    
var main = svg.append("g")
    .attr("transform", "translate(" + main_margin.left + "," + main_margin.top + ")");

var mini = svg.append("g")
    .attr("transform", "translate(" + mini_margin.left + "," + mini_margin.top + ")");
    
var valueline2 = d3.svg.line()
    .x(function(d) { return main_x(d.datee); })
    .y(function(d) { return main_y0(mdvValue); });
    
var main1 = svg.append("g")
    .attr("transform", "translate(" + main_margin.left + "," + main_margin.top + ")");   
var main2 = svg.append("g")
    .attr("transform", "translate(" + main_margin.left + "," + main_margin.top + ")"); 
    
var mini2 = svg.append("g")
    .attr("transform", "translate(" + mini_margin.left + "," + mini_margin.top + ")");    
   
    
data.forEach(function(d) {
   d.datee = d.dateFn;
   d.currents =+d.currents;
   d.mdvValue=+mdvValue;
});

  main_x.domain([data[0].datee, data[data.length - 1].datee]);
  main_y0.domain(d3.extent(data, function(d) { return d.currents; }));
  mini_x.domain(main_x.domain());
  mini_y0.domain(main_y0.domain());
  
  main.append("path")
      .datum(data)
      .attr("clip-path", "url(#clip)")
      .attr("class", "line line0")
      .attr("d", main_line0);


//   main1.append("path")
//      .datum(data)
//      .attr("clip-path", "url(#clip)")
//      .attr("class", "line line5")
//      .style("stroke-dasharray", ("3, 3"))
//      .attr("d", valueline2);
      
   main2.append("path")
      .datum(data)
      .attr("clip-path", "url(#clip)")
      .attr("class", "area")
      .attr("d", mainarea);   

  main.append("g")
      .attr("class", "x axis")
      .attr("transform", "translate(0," + main_height + ")")
      .call(main_xAxis);

  main.append("g")
      .attr("class", "y axis axisLeft")
      .call(main_yAxisLeft)
      .append("text")
      .attr("transform", "rotate(-90)")
      .attr("y", -65)
      .attr("dy", ".41em")
      .attr("x", -65)
      .style("text-anchor", "end")
      .attr("class", "heading_top_")
      .text(yaxisTitle);//Specified in the home.jsp
      
  main1.append("text")      // text label for the x axis
      .attr("x", 890)
      .attr("y",  65 )
      .style("text-anchor", "middle")
        .text();
  mini.append("g")
      .attr("class", "x axis")
      .attr("transform", "translate(0," + mini_height + ")")
      .call(main_xAxis);

  mini.append("path")
      .datum(data)
      .attr("class", "line")
      .attr("d", mini_line0);
  
   mini2.append("path")
      .datum(data)
      .attr("clip-path", "url(#clip)")
      .attr("class", "area")
      .attr("d", miniarea);      
      
   mini2.append("g")
      .attr("class", "x brush")
      .call(brush)
      .selectAll("rect")
      .attr("y", -6)
      .attr("height", mini_height + 7);
      
  var focus = main2.append("g")
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
      .attr("x1", main_width - 1) 
      .attr("x2", main_width + 1); 

  // Anzeige auf der rechten Leiste
  focus.append("line")
      .attr("class", "y1")
      .attr("x1", main_width - 5)
      .attr("x2", main_width + 5);

  focus.append("circle")
      .attr("class", "y0")
      .attr("r", 5);

  focus.append("text")
      .attr("class", "y0")
      .attr("dy", "-1em")
      .attr("dx", "-4em")
      .style("stroke", "green"); 

  main2.append("rect")
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
    focus.select(".x").attr("transform", "translate(" + main_x(d.datee) + ",0)");
    focus.select(".y0").attr("transform", "translate(" + main_width * -1 + ", " + main_y0(d.currents) + ")").attr("x2", main_width + main_x(d.datee));
  }

function brush3() {
  main_x.domain(brush.empty() ? mini_x.domain() : brush.extent()); 
  main.select(".line0").attr("d", main_line0);
  main2.select(".area").attr("d", mainarea);
  main.select(".x.axis").call(main_xAxis);
}
    
       
});
