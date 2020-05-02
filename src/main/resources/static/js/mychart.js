//(function(){
//    console.log("Hello World!");
//})();      	

function drawMyChart() {
    	  
          	var points = /*[[${countOnboardedApplications}]]*/'noValue';
          	var mp = [];
          	
    		var data = new google.visualization.DataTable();
    	    data.addColumn('string', 'Period');
    	    data.addColumn('number', 'Number of Applications');
    	    
    	    points.forEach(entry => {
                var period = entry.period;
                var count = entry.count;
                data.addRow([ `${period}`, parseInt(`${count}`) ]);
            }); 
    	       
    	    var options = {'title':'Applications Onboarded', 'width':750, 'height':400};

    	    var chart = new google.visualization.ColumnChart(document.getElementById ('mychart'));
    	    
    	    chart.draw(data, options);
    	 }

    	