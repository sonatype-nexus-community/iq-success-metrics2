package org.sonatype.cs.metrics;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sonatype.cs.metrics.service.FileService;
import org.sonatype.cs.metrics.util.SqlStatement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.Banner;


@SpringBootApplication
public class SuccessMetricsApplication implements CommandLineRunner {

	private static final Logger log = LoggerFactory.getLogger(SuccessMetricsApplication.class);
	
	@Value("${data.successmetrics}")
    private String successmetricsDatafile;
	
	@Value("${data.applicationevaluationsmetrics}")
    private String applicationevaluationsDatafile;
    
    @Value("${data.policyviolationsmetrics}")
    private String policyviolationsDatafile;
    
    @Value("${data.componentquarantinemetrics}")
    private String componentsquarantineDatafile;
    
    @Value("${data.componentwaiversmetrics}")
    private String componentwaiversDatafile;
    
    public static boolean applicationEvaluationsFileLoaded = false;
    public static boolean policyViolationsDataLoaded = false;
	public static boolean componentsQuarantineLoaded = false;
	public static boolean componentWaiversLoaded = false;
	
	
	@Value("${spring.main.web-application-type}")
	private String runMode;

    @Autowired
    private FileService fileService;

	
	public static void main(String[] args) {
		//SpringApplication.run(SuccessMetricsApplication.class, args);
		
        SpringApplication app = new SpringApplication(SuccessMetricsApplication.class);
		app.setBannerMode(Banner.Mode.OFF);
        app.run(args);
	}

	@Override
	public void run(String... args) throws Exception {
		log.info("Run mode: " + runMode);
		
		if (runMode.contains("SERVLET")) {
			loadSuccessMetricsData();
			loadDatafiles();
		}
		else {
			log.info("cli mode - export file");
            System.exit(0);
		}
	}

	private void loadSuccessMetricsData() throws IOException {
	    String fileHeader = "applicationId,applicationName,applicationPublicId,";

		log.info("loading success metrics");
		
		String stmt = SqlStatement.MetricsTable;	
        boolean fileLoaded = fileService.loadMetricsFile(successmetricsDatafile, fileHeader, stmt);

        if (!fileLoaded){
            log.info(successmetricsDatafile + " file not found");
            System.exit(-1);
        }
	}
	
	public void loadDatafiles() throws IOException {
    	applicationEvaluationsFileLoaded = loadApplicationEvaluationsData();
		policyViolationsDataLoaded = loadPolicyViolationsData();
		componentsQuarantineLoaded = loadComponentsQuarantineData();
		componentWaiversLoaded = loadComponentsWaiversData();
    }

    private boolean loadApplicationEvaluationsData() throws IOException {
    	
		final String fileHeader = "ApplicationName,EvaluationDate,Stage";
		String stmt = SqlStatement.ApplicationEvaluationsTable;	
        return fileService.loadMetricsFile(applicationevaluationsDatafile, fileHeader, stmt);
	}
    
    
    private boolean loadPolicyViolationsData() throws IOException {
    	
        final String fileHeader = "PolicyName,CVE,ApplicationName,OpenTime,Component,Stage";
		String stmt = SqlStatement.PolicyViolationsTables;	
        return fileService.loadMetricsFile(policyviolationsDatafile, fileHeader, stmt);
	}
    
    
    private boolean loadComponentsQuarantineData() throws IOException {
    	
		final String fileHeader = "Repository,Format,PackageUrl,QuarantineTime,PolicyName,ThreatLevel";
		String stmt = SqlStatement.ComponentsInQuarantineTable;;	
        return fileService.loadMetricsFile(componentsquarantineDatafile, fileHeader, stmt);
	}
    
    
    private boolean loadComponentsWaiversData() throws IOException {
    	
		final String fileHeader = "ApplicationName,Stage,PackageUrl,PolicyName,ThreatLevel,Comment";
		String stmt = SqlStatement.ComponentWaiversTable;	
        return fileService.loadMetricsFile(componentwaiversDatafile, fileHeader, stmt);
	}
}
