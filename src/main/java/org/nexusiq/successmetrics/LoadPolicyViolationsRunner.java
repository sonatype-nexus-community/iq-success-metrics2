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


@Order(value=1)
@Component
public class LoadPolicyViolationsRunner implements CommandLineRunner {
	
	private static final Logger log = LoggerFactory.getLogger(LoadPolicyViolationsRunner.class);
	
	@Autowired
	private FileNameService fileNameService;
	
	@Autowired 
	private SQLService sqlService;
	
	@Override
	public void run(String... args) throws Exception {
		log.info("In: LoadPolicyViolationsRunner");
		
		String csvFileName = fileNameService.getFilename("policyviolations");
		
		log.info("Reading csv file: " + csvFileName);
				
		File f = new File(csvFileName.toString());
		
		if(f.exists() && !f.isDirectory()) { 
			
			sqlService.LoadPolicyViolations(csvFileName.toString());
			
			fileNameService.PolicyViolationsReport = true;
			
			log.info("Policy Vioaltions loaded.");
		}
		else {
			log.info("No Policy Vioaltions data");
		}
	}
}
