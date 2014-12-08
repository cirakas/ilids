jq.getJSON(servlet1, function (data) {
   // alert(JSON.stringify(data));
    var fdata = data.data1;
    var dataset1 = [];
    for(var i=0; i<fdata.length; i++) {
        dataset1.push([Date.UTC(fdata[i].year,+" "+ fdata[i].month,+" "+ fdata[i].day,+" "+ fdata[i].hour,+" "+ fdata[i].minute,+" "+ fdata[i].second),fdata[i].value1]);
    }
    
    
    var sdata = data.data2;
    var dataset2 = [];
    for(var j=0; j<sdata.length; j++) {
        dataset2.push([Date.UTC(sdata[j].year,+" "+ sdata[j].month,+" "+ sdata[j].day,+" "+ sdata[j].hour,+" "+ sdata[j].minute,+" "+ sdata[j].second),sdata[j].value2]);
    }
    
    var tdata = data.data3;
    var dataset3 = [];
    for(var k=0; k<tdata.length; k++) {
        dataset3.push([Date.UTC(tdata[k].year,+" "+ tdata[k].month,+" "+ tdata[k].day,+" "+ tdata[k].hour,+" "+ tdata[k].minute,+" "+ tdata[k].second),tdata[k].value3]);
    }
    
    jq('#powGraph').highcharts({
        chart: {
            type: 'spline',
            zoomType: 'x'
           // enableDoubleClickZoomTo: true
            //eventType: "mouseWheel"
        },
        
        
        plotOptions: {
                candlestick: {
                    dataGrouping: {
                        enabled: false   
                    }
                }
            },
              rangeSelector : {
                buttons: [{
                    type: 'hour',
                    count: 1,
                    text: '1h'
                }, {
                    type: 'day',
                    count: 1,
                    text: '1d'
                }, {
                    type: 'month',
                    count: 1,
                    text: '1m'
                }, {
                    type: 'year',
                    count: 1,
                    text: '1y'
                }, {
                    type: 'all',
                    text: 'All'
                }],
                inputEnabled: false, // it supports only days
                selected : 4 // all
            },
            
           
        
        title: {
            text: 'Current vs Time'
        },
//        subtitle: {
//            text: 'Current vs Time in Highcharts JS'
//        },
        xAxis: {
            type: 'datetime',
            //minRange: 3600 * 1000,
            dateTimeLabelFormats: { // don't display the dummy year
                month: '%e. %b',
                year: '%b'
            },
            title: {
                text: 'Date'
            }
        },
        yAxis: {
            title: {
                text: 'Current (A)'
            },
            min: 0
        },
        tooltip: {
            
//            crosshairs: true,
//                shared: true,
            headerFormat: '<b>{series.name}</b><br>',
            pointFormat: '{point.x:%e. %b %Hhrs}: {point.y:.2f} A'
        },
        
        
        series: [{
            
            name: 'Phase1 Current',
            
            data: dataset1
        }, {
            name: 'Phase2 Current',
            data: dataset2
        }, {
            name: 'Phase3 Current',
            data: dataset3
        }
        
    
        ]
        
    });
});

