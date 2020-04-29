<script th:inline="javascript">
      function drawMyChart() {
    	  
          	var points = /*[[${dataPoints}]]*/'noValue';
          	var mp = [];
          	
          	
          	
    		var data = new google.visualization.DataTable();
    	    data.addColumn('string', 'Browser');
    	    data.addColumn('number', 'Percentage');
    	    
    	    points.forEach(entry => {
                var period = entry.period;
                var count = entry.count;
                alert(`${period},${count}`);
                //mp.add(`${period},${count}`);
                mp.push([`${period},${count}`]);
                //alert(mp);
                data.addRow([ `${period}`, 30 ]);
            }); 
    	    
    	    /* data.addRows([
    	       ['Firefox', 10.0],
    	       ['IE', 9.8],
    	       ['Chrome', 20.8],
    	       ['Safari', 30.5],
    	       ['Opera', 9.2],
    	       ['Others', 4.7]
    	    ]); */
    	       
    	    var options = {'title':'Browser market shares at a specific website, 2014', 'width':550, 'height':400};

    	    var chart = new google.visualization.PieChart(document.getElementById ('mychart'));
    	    
    	    chart.draw(data, options);
    	 }

    	google.charts.setOnLoadCallback(drawMyChart);
      </script>
      <div id="mychart" style = "width: 550px; height: 400px; margin: 0 auto"></div>
      