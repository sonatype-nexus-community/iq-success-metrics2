package org.demo.smproto;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;

import org.demo.smproto.service.OSNameService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(value = 1)
public class CreateCSVFileRunner implements CommandLineRunner {
	
	private static final Logger log = LoggerFactory.getLogger(CreateCSVFileRunner.class);

	@Value("${metrics.timePeriod})")
	private String timePeriod;

	@Value("${metrics.firstTimePeriod}")
	private String firstTimePeriod;

	@Value("${iq.url}")
	private String iqurl;

	@Value("${iq.user}")
	private String iquser;

	@Value("${iq.passwd}")
	private String iqpasswd;
	
	@Autowired
	private OSNameService osName;

	@Override
	public void run(String... args) throws Exception {
		
		Path csvFileName = osName.getCSVFileName();
		
		log.info("Creating csv file: " + csvFileName);
		
		String cmd = "curl -X POST -H \"Accept: text/csv\" -H \"Content-Type: application/json\"" +
				" -u " + iquser + ":" + iqpasswd + 
				" -o " + csvFileName + " -d@/tmp/monthly.json " +
				iqurl + "/api/v2/reports/metrics";
			
		try {
			
			if (new File(csvFileName.toString()).exists()) {
				log.info("File already exists: " + csvFileName);
			}
			else {
				log.info(cmd);

				Process process = Runtime.getRuntime().exec("ls -l");
				process.waitFor();
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
