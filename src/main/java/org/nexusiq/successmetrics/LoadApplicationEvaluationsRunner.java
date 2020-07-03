package org.nexusiq.successmetrics;

import java.io.File;

import org.nexusiq.successmetrics.service.FileNameService;
import org.nexusiq.successmetrics.service.SQLService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Order(value=2)
@Component
public class LoadApplicationEvaluationsRunner implements CommandLineRunner {
	
	private static final Logger log = LoggerFactory.getLogger(LoadApplicationEvaluationsRunner.class);

	@Autowired
	private FileNameService fileNameService;
	
	@Autowired 
	private SQLService sqlService;
	
	@Override
	public void run(String... args) throws Exception {
		log.info("In: LoadApplicationEvaluationsRunner");
		
		String csvFileName = fileNameService.getFilename("applicationevaluations");
		
		log.info("Reading csv file: " + csvFileName);
				
		File f = new File(csvFileName.toString());
		
		if(f.exists() && !f.isDirectory() && f.length() > 0) { 
			
			sqlService.LoadApplicationEvaluations(csvFileName.toString());
			
			fileNameService.ApplicationEvaluationsReportExists = true;

			log.info("Application Evaluations loaded.");			
		}
		else {
			log.info("No Application Evaluations data");
		}
	}
}
