package org.demo.smproto.controller;

import java.util.Map;

import org.demo.smproto.service.ModelService;
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


@Controller
public class ApplicationsController {
	
	@Autowired 
	private ModelService modelService;
	
	private static final Logger log = LoggerFactory.getLogger(ApplicationsController.class);

	@GetMapping({"/applications"})
    public String applications(Model model) {
								
		log.info("In ApplicationsController");

		Map <String, Object> map = modelService.setApplicationsReportModel();
		
		model.mergeAttributes(map);
		
        return "applications";
    }
	
	@ExceptionHandler(EmptyResultDataAccessException.class)
    public ResponseEntity<String> noCityFound(EmptyResultDataAccessException e) {

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No data found");
    }
	
}


