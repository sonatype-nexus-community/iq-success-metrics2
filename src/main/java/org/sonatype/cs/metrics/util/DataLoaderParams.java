package org.sonatype.cs.metrics.util;

import org.springframework.stereotype.Component;

@Component
public class DataLoaderParams {

	public static final String smDatafile = "successmetrics.csv";
	public static final String aeDatafile = "reports2/applicationevaluations.csv";
	public static final String cqDatafile = "reports2/componentsinquarantine.csv";
	public static final String cwDatafile = "reports2/componentwaivers.csv";
	public static final String pvDatafile = "reports2/policyviolations.csv";
  
	public static final String smHeader = "applicationId,applicationName,applicationPublicId,";
	public static final String aeFileHeader = "ApplicationName,EvaluationDate,Stage";
	public static final String cqFileHeader = "Repository,Format,PackageUrl,QuarantineTime,PolicyName,ThreatLevel";
	public static final String pvFileHeader = "PolicyName,Reason,ApplicationName,OpenTime,Component,Stage";
	public static final String cwFileHeader = "ApplicationName,Stage,PackageUrl,PolicyName,ThreatLevel,Comment";

	public static final String htmlTemplate = "templates/thymeleaf_template";

}

