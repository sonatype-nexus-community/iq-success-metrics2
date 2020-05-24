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
	
	@Value("${csvfile.dir}")
	private String csvDirectory;
	
	@Value("${csvfile.successmetrics}")
	private String csvSuccessMetricsFileName;
	
	@Value("${csvfile.policyviolations}")
	private String csvPolicyViolationsFileName;
	
	
	private String csvPolicyViolationsFilePath;


			
	private static final Logger log = LoggerFactory.getLogger(OSNameService.class);
	
	
	public Path getCSVSuccessMetricsFilePath() {
		
		Path csvSuccessMetricsFilePath = null;

		String csvFilePath = this.getOSPath();
	    
	    if (csvFilePath == null) {
	    	log.error("could not get path for OS");
	    }
	    else {
	    	csvSuccessMetricsFilePath = Paths.get(csvFilePath, "/", csvSuccessMetricsFileName);
	    }
	    
	    return csvSuccessMetricsFilePath;
	}
	
	public String getCSVPolicyViolationsFilePath() {
				
		String csvFilePath = this.getOSPath();

		if (csvFilePath == null) {
			log.error("could not get path for OS");
	    }
	    else {
	    	csvPolicyViolationsFilePath = csvFilePath + "/" + csvPolicyViolationsFileName;
	    }
	    
	    return csvPolicyViolationsFilePath;
	}
	
	private String getOSPath() {
		
		String csvFilePath = null;
		
		String osHome = null;

		String osName = System.getProperty("os.name");
		
	    log.info("Detected operating system: " + osName);
	    
//	    if (osName.toLowerCase(Locale.ENGLISH).contains("windows")){
//	    	csvFilePath = csvFileWinPath; 
//	    }
//	    else {
//	    	csvFilePath = csvFileUxPath; 
//	    }
	    
	    if (osName.toLowerCase(Locale.ENGLISH).contains("windows")){
	    	osHome = System.getProperty("user.home"); 
	    }
	    else {
	    	osHome = System.getProperty("user.home"); 
	    }
	    
		log.info("Home directory: " + osHome);

	    //csvFilePath = osHome + "/" + csvDirectory;
	    csvFilePath = osHome;

		return csvFilePath;
	}
}
