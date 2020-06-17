package org.demo.smproto;

import java.io.File;
import java.nio.file.Path;

import org.demo.smproto.service.FileNameService;
import org.demo.smproto.service.RepositoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;


@Order(value=3)
@Component
public class LoadSuccessMetricsFileRunner implements CommandLineRunner {
	
	private static final Logger log = LoggerFactory.getLogger(LoadSuccessMetricsFileRunner.class);
	
	@Autowired
	private FileNameService fsName;
	
	@Autowired
	private RepositoryService repositoryService;
	
	@Override
	public void run(String... args) throws Exception {
		log.info("In: LoadSuccessMetricsFileRunner");

		String csvFileName = fsName.getCSVFile("successmetrics");
		
		log.info("Reading csv file: " + csvFileName);
				
		File f = new File(csvFileName.toString());
		
		if(f.exists() && !f.isDirectory()) { 
			
			repositoryService.LoadSuccessMetricsCsvFile(csvFileName.toString());
			
			log.info("Success Metrics loaded.");

			log.info("Ready for browsing");
		}
		else {
			log.info("No Success Metrics data");
		}

	}
}
