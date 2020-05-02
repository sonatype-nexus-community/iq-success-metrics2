(function() {
	
	google.charts.load('current', {packages: ['corechart']});
	google.setOnLoadCallback(applicationsOnboarded);
	//google.charts.setOnLoadCallback(drawPieChart);
 
	function applicationsOnboarded(points) {
		
		console.log('hello from applicationsOnboarded');
		console.log(points);

		var data = new google.visualization.DataTable();
	    data.addColumn('string', 'Period');
	    data.addColumn('number', 'Number of Applications');
	    
//	    dataPoints.forEach(entry => {
//            var period = entry.period;
//            var count = entry.count;
//            data.addRow([ `${period}`, parseInt(`${count}`) ]);
//        }); 
//	    
//	    console.log(dataPoints);
   
	    data.addRows([
		       ['Firefox', 45.0],
		       ['IE', 26.8],
		       ['Chrome', 12.8],
		       ['Safari', 8.5],
		       ['Opera', 6.2],
		       ['Others', 0.7]
		    ]);
	    
	    var options = {'title':'Applications Onboarded', 'width':750, 'height':400};

	    var chart = new google.visualization.ColumnChart(document.getElementById ('applicationsOnboardedChart'));
	    
	    console.log("applicationsOnboardedChart: ", chart);

	    chart.draw(data, options);	
	}
})();

//<div class="container">
//<script th:inline="javascript">var points = /*[[${countOnboardedApplications}]]*/'noValue';</script>
//<script type="text/javascript" src="js/piechart.js"></script>
//<div id="piechart" style = "width: 550px; height: 400px; margin: 0 auto"></div>
//
//<div class="container">
//	<script th:inline="javascript">var applicationsOnboarded = /*[[${countOnboardedApplications}]]*/'noValue';</script>
//<script type="javascript" src="js/applicationsOnboarded.js"></script>
//<div id="applicationsOnboardedChart" style = "width: 550px; height: 400px; margin: 0 auto"></div>
//</div>

//var dataPoints = document.getElementsByName(points);
