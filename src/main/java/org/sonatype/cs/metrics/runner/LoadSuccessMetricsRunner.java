package org.sonatype.cs.metrics.runner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sonatype.cs.metrics.service.FileService;
import org.sonatype.cs.metrics.util.SqlStatement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Order(value=1)
@Component
public class LoadSuccessMetricsRunner implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(LoadSuccessMetricsRunner.class);
    private static final String fileHeader = "applicationId,applicationName,applicationPublicId,";

    @Value("${data.successmetrics}")
    private String fileName;

    @Value("${data.dir}")
    private String dataDir;

    @Autowired
    private FileService fileService;

    @Override
	public void run(String... args) throws Exception {

        log.info("In: LoadSuccessMetricsRunner");
        
        String stmt = SqlStatement.MetricsTable;	
        String metricsFile = dataDir + "/" + fileName;

        if (fileService.isDataValid(metricsFile, fileHeader)) {
            fileService.loadFile(metricsFile, stmt);
        }
        else {
            log.info(metricsFile + " file not found");
            System.exit(-1);
        }
	}
}
