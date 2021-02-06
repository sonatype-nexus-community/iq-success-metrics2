package org.sonatype.cs.metrics.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.lowagie.text.DocumentException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import org.xhtmlrenderer.pdf.ITextRenderer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class CliService {

  private static final Logger log = LoggerFactory.getLogger(CliService.class);

  @Autowired
  private SummaryDataService summaryDataService;

  public String parseThymeleafTemplate(String htmlTemplate) throws ParseException {
		ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
		templateResolver.setSuffix(".html");
		templateResolver.setTemplateMode(TemplateMode.HTML);

		TemplateEngine templateEngine = new TemplateEngine();
		templateEngine.setTemplateResolver(templateResolver);

		// Context context = new Context();
		// context.setVariable("to", "Baeldung");

    Context context = summaryDataService.setSummaryData();

		return templateEngine.process(htmlTemplate, context);
	}

	public void generatePdfFromHtml(String html) throws DocumentException, IOException {

    LocalDateTime instance = LocalDateTime.now();
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("ddMMyy_HHmm");

    String pdfFilename = "successmetrics-" + formatter.format(instance) + ".pdf";

    String outputFolder = System.getProperty("user.home") + File.separator + pdfFilename;
    OutputStream outputStream = new FileOutputStream(outputFolder);

    ITextRenderer renderer = new ITextRenderer();
    renderer.setDocumentFromString(html);
    renderer.layout();
    renderer.createPDF(outputStream);

    outputStream.close();
	}
}
