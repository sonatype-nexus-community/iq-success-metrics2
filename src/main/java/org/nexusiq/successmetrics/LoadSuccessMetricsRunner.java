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

@Order(value=3)
@Component
public class LoadSuccessMetricsRunner implements CommandLineRunner {
	
	private static final Logger log = LoggerFactory.getLogger(LoadSuccessMetricsRunner.class);

	@Autowired
	private FileNameService fileNameService;
	
	@Autowired 
	private SQLService sqlService;
	
	@Override
	public void run(String... args) throws Exception {
		log.info("In: LoadSuccessMetricsFileRunner");
		
		String csvFileName = fileNameService.getFilename("successmetrics");
		
		log.info("Reading csv file: " + csvFileName);
				
		File f = new File(csvFileName.toString());
		
		if(f.exists() && !f.isDirectory()) { 
			
			sqlService.LoadSuccessMetrics(csvFileName.toString());
			
			fileNameService.SuccessMetricsReport = true;

			log.info("Success Metrics loaded.");
		}
		else {
			log.info("No Success Metrics data");
		}
	}
}
