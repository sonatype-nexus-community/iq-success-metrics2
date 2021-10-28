package org.sonatype.cs.metrics;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sonatype.cs.metrics.service.InsightsAnalysisService;
import org.sonatype.cs.metrics.service.LoaderService;
import org.sonatype.cs.metrics.service.SummaryPdfService;
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

	@Value("${spring.profiles.active}")
	private String activeProfile;

	@Value("${pdf.htmltemplate}")
	private String pdfTemplate;

	@Value("${data.successmetrics}")
	private String successmetricsDatafile;
	
	@Value("${iq.sm.csvfile}")
	private boolean iqSmCsvfile;
	
	@Value("${iq.sm.period}")
	private String iqSmPeriod;

	@Value("${server.port}")
	private String port;
	
	@Value("${server.servlet.context-path:}")
	private String contextPath;
	
	@Autowired
	private LoaderService loaderService;

	@Autowired
	private SummaryPdfService pdfService;

	@Autowired
  	private InsightsAnalysisService analysisService;

	private boolean doAnalysis = true;
	

	public static void main(String[] args) {

		SpringApplication app = new SpringApplication(SuccessMetricsApplication.class);
		app.setBannerMode(Banner.Mode.OFF);
		app.run(args);
	}

	@Override
	public void run(String... args) throws Exception {
		log.info("Run mode: " + runMode);
		log.info("Working directory: " + System.getProperty("user.dir"));
		log.info("Active profile: " + activeProfile);

		if (iqSmCsvfile) {
			loaderService.createSmDatafile(iqSmPeriod);
		}

		successMetricsFileLoaded = loaderService.loadSuccessMetricsData();

		if (runMode.contains("SERVLET")) {
			// web app
			this.reports2();
			this.startUp();
		} 
		else {
			// non-interactive mode
			if (successMetricsFileLoaded) {

				switch (activeProfile){
					case "pdf":
						String html = pdfService.parsePdfTemplate(pdfTemplate, doAnalysis);
						pdfService.generatePdfFromHtml(html);
						break;
					case "insights":
						analysisService.writeInsightsAnalysisData();
						break;
					default:
						log.error("unknown profile");
						break;
				}
			}
			else {
				log.error("No data file found");
			}
			
			System.exit(0);
		}
	}

	private void reports2() throws IOException {
		
		applicationEvaluationsFileLoaded = loaderService.loadMetricsFile(DataLoaderParams.aeDatafile, DataLoaderParams.aeFileHeader, SqlStatements.ApplicationEvaluationsTable);
		policyViolationsDataLoaded = loaderService.loadMetricsFile(DataLoaderParams.pvDatafile, DataLoaderParams.pvFileHeader,  SqlStatements.PolicyViolationsTables);
		componentsQuarantineLoaded = loaderService.loadMetricsFile(DataLoaderParams.cqDatafile, DataLoaderParams.cqFileHeader, SqlStatements.ComponentsInQuarantineTable);
		componentWaiversLoaded = loaderService.loadMetricsFile(DataLoaderParams.cwDatafile, DataLoaderParams.cwFileHeader, SqlStatements.ComponentWaiversTable);
	}
	
	private void startUp() {
		if (successMetricsFileLoaded || applicationEvaluationsFileLoaded || policyViolationsDataLoaded || componentsQuarantineLoaded || componentWaiversLoaded){
			log.info("Ready for viewing at http://localhost:{}{}", port, contextPath != null ? contextPath : "");
		}
		else {
			log.error("No data files found");
			System.exit(-1);
		}
	}	
}
