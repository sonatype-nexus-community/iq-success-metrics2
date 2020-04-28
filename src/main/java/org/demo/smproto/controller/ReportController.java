package org.demo.smproto.controller;

import org.demo.smproto.SmprotoApplication;
import org.demo.smproto.model.Applications;
import org.demo.smproto.service.IApplicationsService;
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


@Controller
public class ReportController {
	
	@Autowired 
	private IApplicationsService repository;
	
	private static final Logger log = LoggerFactory.getLogger(SmprotoApplication.class);

	@GetMapping({"/report"})
    public String report(Model model) {
		//model.addAttribute("period", period);
				
		for (Applications m : repository.CountApplicationsByTimeStartPeriod()) {
			log.info(m.toString());
		}
		
        return "report";
    }
	
	@ExceptionHandler(EmptyResultDataAccessException.class)
    public ResponseEntity<String> noCityFound(EmptyResultDataAccessException e) {

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No data found");
    }
}

