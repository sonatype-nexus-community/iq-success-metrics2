function drawBarChart() {
            // Define the chart to be drawn.
            var data = google.visualization.arrayToDataTable([
               ['Year', 'Asia'],
               ['2012',  900],
               ['2013',  1000],
               ['2014',  1170],
               ['2015',  1250],
               ['2016',  1530]
            ]);

            var options = {title: 'Population (in millions)'}; 

            // Instantiate and draw the chart.
            var chart = new google.visualization.BarChart(document.getElementById('barchart'));
            chart.draw(data, options);
         }
         google.charts.setOnLoadCallback(drawBarChart);