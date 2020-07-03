package org.nexusiq.successmetrics;

import org.nexusiq.successmetrics.service.FileNameService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Order(value=4)
@Component
public class StartupRunner implements CommandLineRunner {
	private static final Logger log = LoggerFactory.getLogger(StartupRunner.class);
	
	@Value("${server.port}")
	private String port;
	
	@Override
	public void run(String... args) throws Exception {
				
		if (!FileNameService.SuccessMetricsReportExists) {
			log.error("Exiting... no data files found");
			System.exit(-1);
		}
		else {
			log.info("Ready for browsing at http://localhost:" + port);
		}
	}
}
