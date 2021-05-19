package org.sonatype.cs.metrics;

import java.io.IOException;
import java.text.ParseException;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sonatype.cs.metrics.service.FileLoaderService;
import org.sonatype.cs.metrics.service.SummaryPdfService;
import org.sonatype.cs.metrics.service.PeriodsDataService;
import org.sonatype.cs.metrics.util.DataLoaderParams;
import org.sonatype.cs.metrics.util.SqlStatements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.Banner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SuccessMetricsApplication implements CommandLineRunner {

	private static final Logger log = LoggerFactory.getLogger(SuccessMetricsApplication.class);
	
	public static boolean successMetricsFileLoaded = false;
	public static boolean applicationEvaluationsFileLoaded = false;
	public static boolean policyViolationsDataLoaded = false;
	public static boolean componentsQuarantineLoaded = false;
	public static boolean componentWaiversLoaded = false;
	

	@Value("${spring.main.web-application-type}")
	private String runMode;

	@Value("${pdf.htmltemplate}")
	private String pdfTemplate;

	@Value("${data.successmetrics}")
	private String successmetricsDatafile;

	@Value("${server.port}")
	private String port;
	
	@Value("${data.includelatestperiod}")
	private boolean includelatestperiod;
	
	@Value("${data.loadInsightsMetrics}")
	private boolean loadInsightsMetrics;
	

	@Autowired
	private FileLoaderService loaderService;

	@Autowired
	private SummaryPdfService pdfService;
	
	@Autowired
	private PeriodsDataService periodsDataService;
	

	public static void main(String[] args) {

		SpringApplication app = new SpringApplication(SuccessMetricsApplication.class);
		app.setBannerMode(Banner.Mode.OFF);
		app.run(args);
	}

	@Override
	public void run(String... args) throws Exception {
		log.info("Run mode: " + runMode);
		
		successMetricsFileLoaded = loadSuccessMetricsData();

		if (runMode.contains("SERVLET")) {
			
			this.reports2();
			this.startUp();
		} 
		else {
			String html = pdfService.parsePdfTemplate(pdfTemplate);
			pdfService.generatePdfFromHtml(html);

			System.exit(0);
		}
	}
	
	private boolean loadSuccessMetricsData() throws IOException, ParseException {

		String stmt = SqlStatements.MetricsTable;
		boolean fileLoaded = loaderService.loadMetricsFile(DataLoaderParams.smDatafile, DataLoaderParams.smHeader, stmt);
		
		if (fileLoaded) {
			Map<String, Object> periods = periodsDataService.getPeriodData(SqlStatements.METRICTABLENAME);
			String endPeriod = periods.get("endPeriod").toString();
			
			if (!includelatestperiod) {
				loaderService.filterOutLatestPeriod(endPeriod); // it is likely incomplete
			}
			
			if (loadInsightsMetrics) {
				loaderService.loadInsightsData(periods);
			}
		}
		
		return fileLoaded;
	}

	private void reports2() throws IOException {
		
		applicationEvaluationsFileLoaded = loaderService.loadMetricsFile(DataLoaderParams.aeDatafile, DataLoaderParams.aeFileHeader, SqlStatements.ApplicationEvaluationsTable);
		policyViolationsDataLoaded = loaderService.loadMetricsFile(DataLoaderParams.pvDatafile, DataLoaderParams.pvFileHeader,  SqlStatements.PolicyViolationsTables);
		componentsQuarantineLoaded = loaderService.loadMetricsFile(DataLoaderParams.cqDatafile, DataLoaderParams.cqFileHeader, SqlStatements.ComponentsInQuarantineTable);
		componentWaiversLoaded = loaderService.loadMetricsFile(DataLoaderParams.cwDatafile, DataLoaderParams.cwFileHeader, SqlStatements.ComponentWaiversTable);
	}
	
	private void startUp() {
		if (successMetricsFileLoaded || applicationEvaluationsFileLoaded){
			log.info("Ready for viewing at http://localhost:" + port);
		}
		else {
			log.error("No data files found");
			System.exit(-1);
		}
	}
	
}
