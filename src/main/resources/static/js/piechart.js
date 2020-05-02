(function() {
	
	google.charts.load('current', {packages: ['corechart']});
	google.setOnLoadCallback(drawPieChart);
	//google.charts.setOnLoadCallback(drawPieChart);
 
	function drawPieChart() {
	
	    console.log('hello from piechart');

		var data = new google.visualization.DataTable();
		
	    data.addColumn('string', 'Browser');
	    data.addColumn('number', 'Percentage');
	    
	    data.addRows([
	       ['Firefox', 45.0],
	       ['IE', 26.8],
	       ['Chrome', 12.8],
	       ['Safari', 8.5],
	       ['Opera', 6.2],
	       ['Others', 0.7]
	    ]);
  
	    var options = {'title':'Browser market shares at a specific website, 2014', 'width':550, 'height':400};
	
	    var chart = new google.visualization.PieChart(document.getElementById ('piechart'));
	    //console.log("chart: ", chart);
	    
	    chart.draw(data, options);	
	}
})();