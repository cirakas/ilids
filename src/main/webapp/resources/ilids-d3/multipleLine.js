'use strict';

d3.json(servlet, function(data) {
    var dateFormat = d3.time.format("%m/%d/%Y %H:%M:%S").parse;
    var format = d3.time.format("%b-%d %H:%M")

    //var numberFormat = d3.format(".2f");
    //var mdvValue = mdvValue1;
    var mwidth = 1000;
    var main_margin = {top: 30, right: 75, bottom: 95, left: 75},
    mini_margin = {top: 345, right: 75, bottom: 20, left: 75},
    main_width = mwidth - main_margin.left - main_margin.right,
    main_height = 400 - main_margin.top - main_margin.bottom,
    mini_height = 400 - mini_margin.top - mini_margin.bottom;


    var width = $("#powGraph").width(),
            aspect = 400 / 1000;

    var formatDate = d3.time.format("%H:%M:%S"),
            bisectDate = d3.bisector(function(d) {
                return dateFormat(d.date);
            }).left,
            formatOutput0 = function(d) {
                return formatDate(dateFormat(d.date)) + " - " + +d.current;
            },
            
            formatOutput1 = function(d) { 
                return formatDate(dateFormat(d.date)) + " - " + +d.mdv; 
            };


    var main_x = d3.time.scale()
            .range([0, main_width]),
        mini_x = d3.time.scale()
            .range([0, main_width]);

    var main_y0 = d3.scale.linear()
            .range([270, 0]),
        main_y1 = d3.scale.linear()
            .range([260, 0]),
        mini_y0 = d3.scale.linear()
            .range([20, 0]),
        mini_y1 = d3.scale.linear()
            .range([20, 0]);

    var main_xAxis = d3.svg.axis()
            .scale(main_x)
            // .tickFormat(d3.time.format("%H:%M"))
            .orient("bottom"),
        mini_xAxis = d3.svg.axis()
            .scale(mini_x)
            //   .tickFormat(d3.time.format("%H:%M"))
            .orient("bottom");

    var main_yAxisLeft = d3.svg.axis()
            .scale(main_y0)
            .orient("left")
            .ticks(4),
        main_yAxisRight = d3.svg.axis()
            .scale(main_y1)
            .orient("right")
            .ticks(4)
    
    var brush = d3.svg.brush()
            .x(mini_x)
            .on("brush", brush3);
        
    var zoom = d3.behavior.zoom()
            .scaleExtent([1, 50])
            .on("zoom", draw);     

   
            
    var svg = d3.select("#powGraph").append("svg")
            .attr("preserveAspectRatio", "xMidYMid")
            .attr("viewBox", "0 0 1000 400")
            .attr("width", width)
            .attr("height", width * aspect);
    
    
     var main_line0 = d3.svg.line()
            .interpolate("linear")
            .x(function(d) {
                return main_x(dateFormat(d.date));
            })
            .y(function(d) {
                return main_y0(+d.current);
            });
            
    var main_line1 = d3.svg.line()
            .interpolate("linear")
            .x(function(d) {
                return main_x(dateFormat(d.date));
            })
            .y(function(d) {
                return main_y0(+d.current2);
            });
            
            
    var main_line2 = d3.svg.line()
            .interpolate("linear")
            .x(function(d) {
                return main_x(dateFormat(d.date));
            })
            .y(function(d) {
                return main_y0(+d.current3);
            });        

    var mini_line0 = d3.svg.line()
            .interpolate("linear")
            .x(function(d) {
                return mini_x(dateFormat(d.date));
            })
            .y(function(d) {
                return mini_y0(+d.current);
            });
            
    var mini_line1 = d3.svg.line()
            .interpolate("linear")
            .x(function(d) {
                return mini_x(dateFormat(d.date));
            })
            .y(function(d) {
                return mini_y0(+d.current2);
            });
    
    var mini_line2 = d3.svg.line()
            .interpolate("linear")
            .x(function(d) {
                return mini_x(dateFormat(d.date));
            })
            .y(function(d) {
                return mini_y0(+d.current3);
            });
    
    svg.append("defs").append("clipPath")
            .attr("id", "clip")
            .append("rect")
            .attr("width", main_width)
            .attr("height", main_height);

    $(window).resize(function() {
        var width = $("#powGraph").width();
        svg.attr("width", width);
        svg.attr("height", width * aspect);
    });


//grid vertical lines
    svg.selectAll(".vline").data(d3.range(26)).enter()
            .append("line")
            .attr("x1", function(d) {
                return d * 40;
            })
            .attr("x2", function(d) {
                return d * 40;
            })
            .attr("y1", function(d) {
                return 0;
            })
            .attr("y2", function(d) {
                return 1000;
            })
            .style("stroke", "#eef0f1");

//grid horizontal lines
    svg.selectAll(".vline").data(d3.range(26)).enter()
            .append("line")
            .attr("y1", function(d) {
                return d * 25;
            })
            .attr("y2", function(d) {
                return d * 25;
            })
            .attr("x1", function(d) {
                return 0;
            })
            .attr("x2", function(d) {
                return 1000;
            })
            .style("stroke", "#eef0f1");

    var main = svg.append("g")
            .attr("transform", "translate(" + main_margin.left + "," + main_margin.top + ")");

    var mini = svg.append("g")
            .attr("transform", "translate(" + mini_margin.left + "," + mini_margin.top + ")");
    
    

//var valueline2 = d3.svg.line()
//    .x(function(d) { return main_x(dateFormat.parse(d.date)); })
//    .y(function(d) { return main_y0(mdvValue); });

//  var context = svg.append("g")
//    .attr("class", "context")
//    .attr("transform", "translate(" + mini_margin.left + "," + mini_margin.top + ")");

    main_x.domain([dateFormat(data[0].date), dateFormat(data[data.length - 1].date)]);
    main_y0.domain(d3.extent(data, function(d) {
        return +d.current;
    }));
    mini_x.domain(main_x.domain());
    mini_y0.domain(main_y0.domain());
    zoom.x(main_x);
    draw();

    main.append("path")
            .datum(data)
            .attr("clip-path", "url(#clip)")
            .attr("class", "line line0")
            .attr("d", main_line0);
    
    main.append("path")
            .datum(data)
            .attr("clip-path", "url(#clip)")
            .attr("class", "line line1")
            .attr("d", main_line1);
    
    main.append("path")
            .datum(data)
            .attr("clip-path", "url(#clip)")
            .attr("class", "line line2")
            .attr("d", main_line2);
    


//   main1.append("path")
//      .datum(data)
//      .attr("clip-path", "url(#clip)")
//      .attr("class", "line line5")
//      .style("stroke-dasharray", ("3, 3"))
//      .attr("d", valueline2);

   

// main2.append("g")
//      .attr("class", "x brush")
//      .call(brush1)
//      .selectAll("rect")
//      //.style("stroke", ("indianred"))
//      .style("fill-opacity", (".0"))
//      .attr("y", -6)
//      .attr("height", main_height + 7);
//      
    main.append("g")
            .attr("class", "x axis")
            .attr("transform", "translate(0," + main_height + ")")
            .call(main_xAxis);

    main.append("g")
            .attr("class", "y axis axisLeft")
            .call(main_yAxisLeft)
            .append("text")
            .attr("transform", "rotate(-90)")
            .attr("y", -60)
            .attr("dy", ".41em")
            .attr("x", -65)
            .style("text-anchor", "end")
            .attr("class", "heading_top_")
            .text("Current");//Specified in the home.jsp
    
    

    main.append("text")      // text label for the x axis
            .attr("x", 890)
            .attr("y", 65)
            .style("text-anchor", "middle")
            .text();
    
    mini.append("g")
            .attr("class", "x axis")
            .attr("transform", "translate(0," + mini_height + ")")
            .call(main_xAxis);

    mini.append("path")
            .datum(data)
            .attr("clip-path", "url(#clip)")
            .attr("class", "line line0")
            .attr("d", mini_line0);
    
    mini.append("path")
            .datum(data)
            .attr("clip-path", "url(#clip)")
            .attr("class", "line line1")
            .attr("d", mini_line1);
    
    mini.append("path")
            .datum(data)
            .attr("clip-path", "url(#clip)")
            .attr("class", "line line2")
            .attr("d", mini_line2);
  

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
            .attr("x1", main_width - 1)
            .attr("x2", main_width + 1);


    focus.append("circle")
            .attr("class", "y0")
            .attr("r", 5);

    focus.append("text")
            .attr("class", "y0")
            .attr("dy", "-1em")
            .attr("dx", "-4em")
            .style("stroke", "black");
    

//main graph functionality-zoom
    main.append("rect")
            .attr("class", "pane")
            .attr("width", main_width)
            .attr("height", main_height)
            .on("mouseover", function() {
                focus.style("display", null);
            })
            .on("mouseout", function() {
                focus.style("display", "none");
            })
            .on("mousemove", mousemove1)
            .call(zoom)
    
            .on("dblclick.zoom", null)
            .on("touchstart.zoom", null)
           // .on("mousedown.zoom", null)
            .on("mouseup.zoom", null)
            //.on("mousemove.zoom", null)
            //.on("mousewheel.zoom", null)
            .on("touchmove.zoom", null)
            .on("touchend.zoom", null)
            .on("end.zoom", null)
            .on("MozMousePixelScroll.zoom", null)
           // .on("wheel.zoom", null);



//      context.append("path")
//      .datum(data)
//      .attr("class", "area")
//      .attr("d", miniarea);
//
//  context.append("g")
//      .attr("class", "x axis")
//      .attr("transform", "translate(0," + mini_height + ")")
//      .call(main_xAxis);
//
//  context.append("g")
//      .attr("class", "x brush")
//      .call(brush)
//    .selectAll("rect")
//      .attr("y", -6)
//      .attr("height", mini_height + 7);



    function mousemove1() {
        var x0 = main_x.invert(d3.mouse(this)[0]),
                i = bisectDate(data, x0, 1),
                d0 = data[i - 1],
                d1 = data[i],
                d = x0 - dateFormat(d0.date) > dateFormat(d1.date) - x0 ? d1 : d0;
        var pdatee = dateFormat(d.date);
       

        focus.select("circle.y0").attr("transform", "translate(" + main_x(pdatee) + "," + main_y0(+d.current) + ")");
        focus.select("text.y0").attr("transform", "translate(" + main_x(pdatee) + "," + main_y0(+d.current) + ")").text(formatOutput0(d));
        focus.select(".x").attr("transform", "translate(" + main_x(pdatee) + ",0)");
        focus.select(".y0").attr("transform", "translate(" + main_width * -1 + ", " + main_y0(+d.current) + ")").attr("x2", main_width + main_x(pdatee));
                                                

    }

    
    function brush3() {
        main_x.domain(brush.empty() ? mini_x.domain() : brush.extent());
        main.select(".line0").attr("d", main_line0);
        main.select(".line1").attr("d", main_line1);
        main.select(".line2").attr("d", main_line2);
        main.select(".x.axis").call(main_xAxis);
    }


    function draw() {
        svg.select("g.x.axis").call(main_xAxis);
        svg.select("g.y.axis").call(main_yAxisLeft);
        svg.select("path.line0").attr("d", main_line0);
        svg.select("path.line1").attr("d", main_line1);
        svg.select("path.line2").attr("d", main_line2);
    }
    
});