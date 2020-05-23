package org.demo.smproto.service;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class OSNameService {
	
	@Value("${csvfile.path.ux}")
	private String csvFileUxPath;
	
	@Value("${csvfile.path.win}")
	private String csvFileWinPath;
	
	@Value("${csvfile.name}")
	private String csvSuccessMetricsFileName;
	
	@Value("${csv.policyviolations.filename}")
	private String csvPolicyViolationsFileName;
	
	@Value("${csv.directory}")
	private String csvDirectory;

	private Path csvSuccessMetricsFilePath;
	private String csvPolicyViolationsFilePath;

	
	private static final Logger log = LoggerFactory.getLogger(OSNameService.class);

	
	public Path getCSVFileName() {
		
		String csvFilePath = null;

		String osName = System.getProperty("os.name");
	    log.info("Detected operating system: " + osName);
	    
	    if (osName.toLowerCase(Locale.ENGLISH).contains("windows")){
	    	csvFilePath = csvFileWinPath; 
	    }
	    else {
	    	csvFilePath = csvFileUxPath; 
	    }
	    
	    if (csvFilePath == null) {
	    	log.error("could not locate data file for OS: " + osName);
	    }
	    else {
	    	csvSuccessMetricsFilePath = Paths.get(csvFilePath, "/", csvSuccessMetricsFileName);
	    }
	    
	    return csvSuccessMetricsFilePath;
	}
	
	public String getCSVPolicyViolations() {
		
		String osHome = null;
		
		String osName = System.getProperty("os.name");

		log.info("Detected operating system: " + osName);
	    
	    if (osName.toLowerCase(Locale.ENGLISH).contains("windows")){
	    	osHome = System.getProperty("user.home"); 
	    }
	    else {
	    	osHome = System.getProperty("user.home"); 
	    }
	    
		log.info("Home directory: " + osHome);
		
		if (osHome == null) {
	    	log.error("could not locate get home directory: " + osHome);
	    }
	    else {
	    	csvPolicyViolationsFilePath = osHome + "/" + csvDirectory + "/" + csvPolicyViolationsFileName;
	    }
	    
	    return csvPolicyViolationsFilePath;
	}
}
