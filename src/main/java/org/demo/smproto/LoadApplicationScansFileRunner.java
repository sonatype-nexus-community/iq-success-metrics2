package org.demo.smproto;

import java.io.File;

import org.demo.smproto.service.FileNameService;
import org.demo.smproto.service.RepositoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Order(value=2)
@Component
public class LoadApplicationScansFileRunner implements CommandLineRunner{
	
	private static final Logger log = LoggerFactory.getLogger(LoadPolicyViolationsFileRunner.class);

	@Autowired
	private RepositoryService repositoryService;
	
	@Autowired
	private FileNameService fsName;
	
	@Override
	public void run(String... args) throws Exception {
		log.info("In: LoadApplicationScansFileRunner");

		String csvFileName = fsName.getCSVFile("applicationevaluations");
		
		log.info("Reading csv file: " + csvFileName);

		File f = new File(csvFileName);
		
		if(f.exists() && !f.isDirectory()) { 

			repositoryService.LoadApplicationEvaluationsCsvFile(csvFileName);
			
			log.info("Application Evaluations loaded.");
		}
		else {
			log.info("No Application Evaluations data");
		}
	}

}
