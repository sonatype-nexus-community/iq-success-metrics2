package org.demo.smproto.service;

import java.nio.file.Path;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class OSNameService {
	
	@Value("${metrics.ux.csvfile}")
	private Path csvFileUxPath;
	
	@Value("${metrics.win.csvfile}")
	private Path csvFileWinPath;
	
	private static final Logger log = LoggerFactory.getLogger(OSNameService.class);

	
	public Path getCSVFileName() {
		
		Path csvFilePath = null;

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
	    	//System.exit(1);
	    }
	    
	    return csvFilePath;
	}
	
}
