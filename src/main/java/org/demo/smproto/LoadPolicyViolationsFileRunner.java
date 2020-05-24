package org.demo.smproto;

import java.io.File;
import java.nio.file.Path;

import org.demo.smproto.service.IDatabaseService;
import org.demo.smproto.service.OSNameService;
import org.demo.smproto.service.RepositoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Order(value=1)
@Component
public class LoadPolicyViolationsFileRunner implements CommandLineRunner {
	
	private static final Logger log = LoggerFactory.getLogger(LoadPolicyViolationsFileRunner.class);

	@Autowired
	private RepositoryService dbService;
	
	@Autowired
	private OSNameService osName;
	
	@Override
	public void run(String... args) throws Exception {
		log.info("In: LoadPolicyViolationsFileRunner");

		String csvFileName = osName.getCSVPolicyViolationsFilePath();
		
		log.info("Reading csv file: " + csvFileName);

		File f = new File(csvFileName);
		
		if(f.exists() && !f.isDirectory()) { 

			dbService.LoadPolicyViolationsCsvFile(csvFileName);
			
			log.info("Policy violations loaded.");
		}
		else {
			log.info("No policy violations data");
		}
	}
}
