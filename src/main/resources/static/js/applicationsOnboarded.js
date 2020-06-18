function applicationsOnboarded(points) {
//          	var points = /*[[${applicationsOnboardedData}]]*/'noValue';
          	alert("Hello from applicationsOnboarded");
    		var data = new google.visualization.DataTable();
    	    data.addColumn('string', 'Period');
    	    data.addColumn('number', 'Applications Onboarded - Cumulative');

    	    points.forEach(entry => {
                var label = entry.label;
                var point = entry.pointA;
                data.addRow([ `${label}`, parseInt(`${point}`) ]);
            }); 
    	       
    	    var options = {'title':'Applications Onboarded (Scanned)', 'width':750, 'height':400};
    	    var chart = new google.visualization.ColumnChart(document.getElementById ('applicationsOnboardedChart'));   
    	    
    	    var btnSave = document.getElementById('save-pdf');

    	    google.visualization.events.addListener(chart, 'ready', function () {
    	    	btnSave.disabled = false;
    	    });
    	    
    	    btnSave.addEventListener('click', function () {
    	        var doc = new jsPDF();
    	        doc.addImage(chart.getImageURI(), 0, 0);
    	        doc.save('chart.pdf');
    	    }, false);
    	    
    	    chart.draw(data, options);
    	 }

    	google.charts.setOnLoadCallback(applicationsOnboarded);