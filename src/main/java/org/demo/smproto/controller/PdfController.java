package org.demo.smproto.controller;

import java.util.HashMap;
import java.util.Map;

import org.demo.smproto.pdfTemplate.PdfGeneratorUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.thymeleaf.spring5.SpringTemplateEngine;

@Controller
public class PdfController {
	
	private static final Logger log = LoggerFactory.getLogger(PdfController.class);

	@Autowired
	PdfGeneratorUtil pdfGeneratorUtil;
	
	@GetMapping({"/pdf"})
	public String pdf() {
		
//		Map<String,Object> data = new HashMap<String,Object>();
//	    data.put("name","James");
//	    try {
//	    	log.info("creating pdf");
//			pdfGeneratorUtil.createPdf("pdf",data);
//		} 
//	    catch (Exception e) {
//			e.printStackTrace();
//		} 
		
		return "pdf";
	}
}
