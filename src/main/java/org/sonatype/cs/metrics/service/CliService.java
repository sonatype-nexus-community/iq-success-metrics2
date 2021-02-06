package org.sonatype.cs.metrics.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.lowagie.text.DocumentException;

import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import org.xhtmlrenderer.pdf.ITextRenderer;


@Service
public class CliService {

  public String parseThymeleafTemplate(String htmlTemplate) {
		ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
		templateResolver.setSuffix(".html");
		templateResolver.setTemplateMode(TemplateMode.HTML);

		TemplateEngine templateEngine = new TemplateEngine();
		templateEngine.setTemplateResolver(templateResolver);

		Context context = new Context();
		context.setVariable("to", "Baeldung");

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
