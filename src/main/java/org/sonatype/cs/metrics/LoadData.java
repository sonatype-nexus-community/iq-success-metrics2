package org.sonatype.cs.metrics;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sonatype.cs.metrics.service.FileService;
import org.sonatype.cs.metrics.util.SqlStatement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class LoadData {
    private static final Logger log = LoggerFactory.getLogger(LoadData.class);
    
    
    @Value("${data.applicationevaluationsmetrics}")
    private static String applicationevaluationsDatafile;
    
    @Value("${data.policyviolationsmetrics}")
    private static String policyviolationsDatafile;
    
    @Value("${data.componentquarantinemetrics}")
    private static String componentsquarantineDatafile;
    
    @Value("${data.componentwaiversmetrics}")
    private static String componentwaiversDatafile;
    
    public static boolean applicationEvaluationsFileLoaded = false;
    public static boolean policyViolationsDataLoaded = false;
	public static boolean componentsQuarantineLoaded = false;
	public static boolean componentWaiversLoaded = false;
    

    @Autowired
    private static FileService fileService;
	
    public static void LoadDataFiles() throws IOException {
    	applicationEvaluationsFileLoaded = loadApplicationEvaluationsData();
		policyViolationsDataLoaded = loadPolicyViolationsData();
		componentsQuarantineLoaded = loadComponentsQuarantineData();
		componentWaiversLoaded = loadComponentsWaiversData();
    }

    private static boolean loadApplicationEvaluationsData() throws IOException {
    	
		final String fileHeader = "ApplicationName,EvaluationDate,Stage";
		String stmt = SqlStatement.ApplicationEvaluationsTable;	
        return fileService.loadMetricsFile(applicationevaluationsDatafile, fileHeader, stmt);
	}
    
    
    private static boolean loadPolicyViolationsData() throws IOException {
    	
        final String fileHeader = "PolicyName,CVE,ApplicationName,OpenTime,Component,Stage";
		String stmt = SqlStatement.PolicyViolationsTables;	
        return fileService.loadMetricsFile(policyviolationsDatafile, fileHeader, stmt);
	}
    
    
    private static boolean loadComponentsQuarantineData() throws IOException {
    	
		final String fileHeader = "Repository,Format,PackageUrl,QuarantineTime,PolicyName,ThreatLevel";
		String stmt = SqlStatement.ComponentsInQuarantineTable;;	
        return fileService.loadMetricsFile(componentsquarantineDatafile, fileHeader, stmt);
	}
    
    
    private static boolean loadComponentsWaiversData() throws IOException {
    	
		final String fileHeader = "ApplicationName,Stage,PackageUrl,PolicyName,ThreatLevel,Comment";
		String stmt = SqlStatement.ComponentWaiversTable;	
        return fileService.loadMetricsFile(componentwaiversDatafile, fileHeader, stmt);
	}

}
