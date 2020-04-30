package org.demo.smproto;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(value = 1)
public class CreateCSVFileRunner implements CommandLineRunner {
	
	private static final Logger log = LoggerFactory.getLogger(CreateCSVFileRunner.class);

	@Value("${metrics.csvfile}")
	private String csvFileName;
	
	@Value("${metrics.timePeriod})")
	private String timePeriod;

	@Value("${metrics.firstTimePeriod}")
	private String firstTimePeriod;

	@Value("${nexusiq.url}")
	private String iqurl;

	@Value("${nexusiq.username}")
	private String iquser;

	@Value("${nexusiq.passwd}")
	private String iqpasswd;

	@Override
	public void run(String... args) throws Exception {
		

		log.info("Create csv file...");
		
		String cmd = "curl -X POST -H \"Accept: text/csv\" -H \"Content-Type: application/json\"" +
				" -u " + iquser + ":" + iqpasswd + 
				" -o " + csvFileName + " -d@/tmp/monthly.json " +
				iqurl + "/api/v2/reports/metrics";
		
				
		try {
			
			if (new File(csvFileName).exists()) {
				log.info("file already exists: " + csvFileName);
			}
			else {
				log.info("creating file: " + csvFileName);
				log.info(cmd);

				Process process = Runtime.getRuntime().exec("ls -l");
				process.waitFor();
//				InputStream inputStream = process.getInputStream();
//				
//				for (int i = 0; i < inputStream.available(); i++) {
//		           System.out.println("" + inputStream.read());
//		        }
				
				process.destroy();

			}
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
		finally {
		}
		
	}
	
	

}
