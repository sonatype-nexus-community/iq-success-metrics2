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

@Order(value = 2)
@Component
public class LoadApplicationEvaluationsRunner implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(LoadApplicationEvaluationsRunner.class);
    private static final String fileHeader = "ApplicationName,EvaluationDate,Stage";

    @Value("${data.applicationevaluationsmetrics}")
    private String fileName;

    @Autowired
    private FileService fileService;

    public static boolean fileLoaded = false;

    @Override
	public void run(String... args) throws Exception {
        
        String stmt = SqlStatement.ApplicationEvaluationsTable;	
        fileLoaded = fileService.loadMetricsFile(fileName, fileHeader, stmt);
    }
}
