package org.sonatype.cs.nxmetrics.runner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sonatype.cs.nxmetrics.service.FileService;
import org.sonatype.cs.nxmetrics.util.SqlStatement;
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
    private String metricsFile;

    @Autowired
    private FileService fileService;

    @Override
	public void run(String... args) throws Exception {

        log.info("In: LoadSuccessMetricsRunner");
        
        String stmt = SqlStatement.MetricsTable;	

        if (fileService.isDataValid(metricsFile, fileHeader)) {
            fileService.loadFile(metricsFile, stmt);
        }
        else {
            System.exit(-1);
        }
	}
}
