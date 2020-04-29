function drawBarChart() {
    	  var data = google.visualization.arrayToDataTable([
    	  ['Task', 'Hours per Day'],
    	  ['Work', 8],
    	  ['Friends', 2],
    	  ['Eat', 2],
    	  ['TV', 2],
    	  ['Gym', 2],
    	  ['Sleep', 8]
    	]);

    	  var options = {'title':'My Average Day', 'width':550, 'height':400};
    	  var chart = new google.visualization.BarChart(document.getElementById('barchart'));
    	  chart.draw(data, options);
    	}
       google.charts.setOnLoadCallback(drawBarChart);