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

@Order(value = 2)
@Component
public class LoadApplicationEvaluationsRunner implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(LoadApplicationEvaluationsRunner.class);
    private static final String fileHeader = "ApplicationName,EvaluationDate,Stage";

    @Value("${data.applicationevaluationsmetrics}")
    private String metricsFile;

    @Autowired
    private FileService fileService;

    @Override
	public void run(String... args) throws Exception {

        log.info("In ApplicationEvaluationsMetricsRunner");
        
        String stmt = SqlStatement.ApplicationEvaluationsTable;	

        if (fileService.isDataValid(metricsFile, fileHeader)) {
            fileService.loadFile(metricsFile, stmt);
        }
    }
}
