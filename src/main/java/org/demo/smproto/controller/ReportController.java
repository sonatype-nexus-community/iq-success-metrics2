package org.demo.smproto.controller;

import java.util.ArrayList;
import java.util.List;

import org.demo.smproto.SmprotoApplication;
import org.demo.smproto.component.MetricsRepository;
import org.demo.smproto.model.Application;
import org.demo.smproto.model.DataPoint;
import org.demo.smproto.service.IApplicationsService;
import org.demo.smproto.service.IDataService;
import org.demo.smproto.service.IMetricsRepositoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.thymeleaf.expression.Lists;


@Controller
public class ReportController {
	
	@Autowired 
	private IDataService dataService;
	
	private static final Logger log = LoggerFactory.getLogger(SmprotoApplication.class);

	@GetMapping({"/report"})
    public String report(Model model) {
				
		List<DataPoint> dataPoints = new ArrayList<DataPoint>();

		for (DataPoint dp : dataService.countOnBoardedApplications()) {
			log.info(dp.toString());
			dataPoints.add(dp);
		}
		
		if (dataPoints.isEmpty()) {
            model.addAttribute("message", "No data.");
            model.addAttribute("status", false);
        } 
		else {
			model.addAttribute("dataPoints", dataPoints);
            model.addAttribute("status", true);

		}

        return "report";
    }
	
	@ExceptionHandler(EmptyResultDataAccessException.class)
    public ResponseEntity<String> noCityFound(EmptyResultDataAccessException e) {

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No data found");
    }
}


//class multiDimensional 
//{ 
//    public static void main(String args[]) 
//    { 
//        // declaring and initializing 2D array 
//        int arr[][] = { {2,7,9},{3,6,1},{7,4,2} }; 
//  
//        // printing 2D array 
//        for (int i=0; i< 3 ; i++) 
//        { 
//            for (int j=0; j < 3 ; j++) 
//                System.out.print(arr[i][j] + " "); 
//  
//            System.out.println(); 
//        } 
//    } 
//} 
