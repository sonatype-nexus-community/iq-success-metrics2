package org.sonatype.cs.metrics.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class FileService {

	private static final Logger log = LoggerFactory.getLogger(FileService.class);
	
	@Autowired
	private DataService dataService;
	
	public boolean loadFile(String filename, String stmt) throws IOException {

		String metricsFile = filename;

		String sqlStmt = stmt + " ('" + metricsFile + "')";	

		log.info("Reading data file: " + metricsFile);

		dataService.runSqlLoad(sqlStmt);

		log.info("Data loaded.");
		
		return true;
	}
	
	public String getFirstLine(String fileName) throws IOException {
	    BufferedReader br = new BufferedReader(new FileReader(fileName)); 

	    String line = br.readLine(); 
	    br.close();
	    return line;
	}

	public boolean isDataValid(String filename, String header) throws IOException {

		boolean isValid = false;

		String metricsFile = filename;

		File f = new File(metricsFile);

        if (f.exists()){
			if (!f.isDirectory() && f.length() > 0) {
				isValid = true;

				if (header.length() > 0){
					String firstLine = this.getFirstLine(metricsFile);

					if (!firstLine.startsWith(header)) {
						log.error("Invalid header");
						log.error("-> " + firstLine);
						isValid = false;
					} 
				}
			}
			else {
				log.info("No data");
				isValid = false;
			}
		}
	
		return isValid;
	}
}
