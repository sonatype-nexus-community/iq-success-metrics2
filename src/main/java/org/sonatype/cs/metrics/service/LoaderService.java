package org.sonatype.cs.metrics.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sonatype.cs.metrics.util.SqlStatements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoaderService {

	private static final Logger log = LoggerFactory.getLogger(LoaderService.class);
	
	@Autowired
	private DbService dbService;
	
	@Autowired
	private PeriodsDataService periodsDataService;
	
	public boolean loadMetricsFile(String fileName, String header, String stmt) throws IOException {
		boolean status = false;

		if (isHeaderValid(fileName, header)){
			status = loadFile(fileName, stmt);
		}

		return status;
	}

	private boolean loadFile(String fileName, String stmt) throws IOException {
		String sqlStmt = stmt + " ('" + fileName + "')";	
		
		dbService.runSqlLoad(sqlStmt);
		
		log.info("Loaded file: " + fileName);
		
		return true;
	}
	
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

	public void filterOutLatestPeriod(String endPeriod) throws ParseException {
		String sqlStmt = "delete from metric where time_period_start = " + "'" + endPeriod + "'";
		dbService.runSqlLoad(sqlStmt);
		return;
	}

	public void loadInsightsData() throws ParseException {
		Map<String, Object> periods = periodsDataService.getPeriodData(SqlStatements.METRICTABLENAME);

		String midPeriod = periods.get("midPeriod").toString();
		
		log.info("mid period: " + midPeriod);
		
		String sqlStmtP1 = "DROP TABLE IF EXISTS METRIC_P1; CREATE TABLE METRIC_P1 AS SELECT * FROM METRIC WHERE TIME_PERIOD_START <= '" + midPeriod + "'";
		dbService.runSqlLoad(sqlStmtP1);
		
		String sqlStmtP2 = "DROP TABLE IF EXISTS METRIC_P2; CREATE TABLE METRIC_P2 AS SELECT * FROM METRIC WHERE TIME_PERIOD_START > '" + midPeriod + "'";
		dbService.runSqlLoad(sqlStmtP2);
			 
		return;		
	}
	
}
