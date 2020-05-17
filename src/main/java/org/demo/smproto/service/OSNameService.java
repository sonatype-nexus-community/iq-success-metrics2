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
	private String csvFileName;

	private Path csvfile;
	
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
	    	csvfile = Paths.get(csvFilePath, "/", csvFileName);
	    }
	    
	    return csvfile;
	}
	
}
