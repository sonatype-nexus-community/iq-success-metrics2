package org.sonatype.cs.metrics;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.ParseException;

import com.lowagie.text.DocumentException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sonatype.cs.metrics.service.PdfService;
import org.sonatype.cs.metrics.service.FileService;
import org.sonatype.cs.metrics.util.DataLoaderParams;
import org.sonatype.cs.metrics.util.SqlStatement;
import org.sonatype.cs.metrics.util.UtilService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.Banner;

@SpringBootApplication
public class SuccessMetricsApplication implements CommandLineRunner {

	private static final Logger log = LoggerFactory.getLogger(SuccessMetricsApplication.class);
	
	public static boolean applicationEvaluationsFileLoaded = false;
	public static boolean policyViolationsDataLoaded = false;
	public static boolean componentsQuarantineLoaded = false;
	public static boolean componentWaiversLoaded = false;

	@Value("${spring.main.web-application-type}")
	private String runMode;

	@Value("${pdf.htmltemplate}")
	private String htmlTemplate;

	@Value("${data.successmetrics}")
	private String successmetricsDatafile;

	@Value("${server.port}")
	private String port;

	@Autowired
	private FileService fileService;

	@Autowired
	private PdfService pdfService;
	

	public static void main(String[] args) {
		// SpringApplication.run(SuccessMetricsApplication.class, args);

		SpringApplication app = new SpringApplication(SuccessMetricsApplication.class);
		app.setBannerMode(Banner.Mode.OFF);
		app.run(args);
	}

	@Override
	public void run(String... args) throws Exception {
		log.info("Run mode: " + runMode);
		
		loadSuccessMetricsData();

		if (runMode.contains("SERVLET")) {
			reports2();
			log.info("Ready for viewing at http://localhost:" + port);
		} 
		else {
			String html = pdfService.parseThymeleafTemplate(htmlTemplate);
			pdfService.generatePdfFromHtml(html);

			System.exit(0);
		}
	}

	private void loadSuccessMetricsData() throws IOException, ParseException {
		String fileHeader = "applicationId,applicationName,applicationPublicId,";

		String stmt = SqlStatement.MetricsTable;
		boolean fileLoaded = fileService.loadMetricsFile(DataLoaderParams.smDatafile, fileHeader, stmt);

		if (!fileLoaded) {
			log.info(DataLoaderParams.smDatafile + " file not found");
			System.exit(-1);
		}
		else {
			fileService.loadPreviousPeriodData();
		}
	}
	
	public void reports2() throws IOException {
		applicationEvaluationsFileLoaded = fileService.loadMetricsFile(DataLoaderParams.aeDatafile, DataLoaderParams.aeFileHeader, SqlStatement.ApplicationEvaluationsTable);
		policyViolationsDataLoaded = fileService.loadMetricsFile(DataLoaderParams.pvDatafile, DataLoaderParams.pvFileHeader,  SqlStatement.PolicyViolationsTables);
		componentsQuarantineLoaded = fileService.loadMetricsFile(DataLoaderParams.cqDatafile, DataLoaderParams.cqFileHeader, SqlStatement.ComponentsInQuarantineTable);
		componentWaiversLoaded = fileService.loadMetricsFile(DataLoaderParams.cwDatafile, DataLoaderParams.cwFileHeader, SqlStatement.ComponentWaiversTable);
	}
}
