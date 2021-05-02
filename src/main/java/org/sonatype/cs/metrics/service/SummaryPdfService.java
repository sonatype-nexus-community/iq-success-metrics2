package org.sonatype.cs.metrics.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sonatype.cs.metrics.util.SqlStatements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import org.xhtmlrenderer.pdf.ITextRenderer;

import com.lowagie.text.DocumentException;


@Service
public class SummaryPdfService {

	private static final Logger log = LoggerFactory.getLogger(SummaryPdfService.class);

	@Value("${pdf.outputdir}")
	private String outputdir;
	  
	@Value("${pdf.htmltemplate}")
	private String htmlTemplate;
	  
	@Autowired
	private PeriodsDataService periodsDataService;
	  
	@Autowired
	private MetricsService metricsService;
	  
	@Autowired
	private AnalysisService analysisService;
	
	
	public String parsePdfTemplate(String htmlTemplate) throws ParseException {
	    ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
	    templateResolver.setSuffix(".html");
	    templateResolver.setTemplateMode(TemplateMode.HTML);

	    TemplateEngine templateEngine = new TemplateEngine();
	    templateEngine.setTemplateResolver(templateResolver);
	    
	    Map<String, Object> periodsData = periodsDataService.getPeriodData(SqlStatements.METRICTABLENAME);
        Map<String, Object> metrics = metricsService.getMetrics(SqlStatements.METRICTABLENAME, periodsData);
	    Map<String, Object> p1metrics = metricsService.getMetrics(SqlStatements.METRICP1TABLENAME, periodsData);
	    Map<String, Object> p2metrics = metricsService.getMetrics(SqlStatements.METRICP2TABLENAME, periodsData);
	    Map<String, Object> analysisData = analysisService.getAnalysisData(periodsData);

	    Context context = new Context();
	    context.setVariables(periodsData);
	    context.setVariables(metrics);
	    context.setVariables(analysisData);

	    context.setVariable("p1", p1metrics);
	    context.setVariable("p2", p2metrics);

	    return templateEngine.process(htmlTemplate, context);
	}
	

	public void generatePdfFromHtml(String html) throws DocumentException, IOException {

	    LocalDateTime instance = LocalDateTime.now();
	    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("ddMMyy_HHmm");
	
	    String pdfFilename = "successmetrics-" + formatter.format(instance) + ".pdf";
	
	    Path path = Paths.get(outputdir);
	
	    if (!Files.exists(path)){
	      Files.createDirectory(path);
	    }
	
	    String outputFolder = outputdir + File.separator + pdfFilename;
	    OutputStream outputStream = new FileOutputStream(outputFolder);
	
	    ITextRenderer renderer = new ITextRenderer();
	    renderer.setDocumentFromString(html);
	    renderer.layout();
	    renderer.createPDF(outputStream);
	
	    outputStream.close();
	
	    log.info("Created pdf report: " + outputFolder);
	}
	
}
