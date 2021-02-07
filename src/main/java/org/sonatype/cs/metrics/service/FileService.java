package org.sonatype.cs.metrics.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FileService {

	private static final Logger log = LoggerFactory.getLogger(FileService.class);
	
	@Autowired
	private DataService dataService;

	public boolean loadMetricsFile(String fileName, String header, String stmt) throws IOException {
		boolean status = false;

		if (isHeaderValid(fileName, header)){
			status = loadFile(fileName, stmt);
		}

		return status;
	}

	private boolean loadFile(String fileName, String stmt) throws IOException {
		log.info("Reading file: " + fileName);
		String sqlStmt = stmt + " ('" + fileName + "')";	
		dataService.runSqlLoad(sqlStmt);
		log.info("Data loaded.");
		return true;
	}
	
	// public boolean isAvailable(String metricsFile){
	// 	File f = new File(metricsFile);
	// 	if (f.exists() && f.length() > 0 && !f.isDirectory()){
	// 		return true;
	// 	}
	// 	else {
	// 		return false;
	// 	}
	// }

	private boolean isHeaderValid(String filename, String header) throws IOException {

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
							else {
								if (this.countLines(metricsFile) < 2){
									//log.warn("No metrics data in file");
									isValid = false;
								}
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

	private String getFirstLine(String fileName) throws IOException {
	    BufferedReader br = new BufferedReader(new FileReader(fileName)); 

	    String line = br.readLine(); 
	    br.close();
	    return line;
	}

	private int countLines(String fileName) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(fileName)); 

		String line = br.readLine(); 
		int lineCount = 0;

		while (line != null){
			lineCount++;
			line = br.readLine();
		}

		br.close();
		return lineCount;
}
	
}
