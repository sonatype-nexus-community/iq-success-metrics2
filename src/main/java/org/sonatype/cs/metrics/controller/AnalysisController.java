package org.sonatype.cs.metrics.controller;

import java.text.ParseException;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sonatype.cs.metrics.service.AnalysisService;
import org.sonatype.cs.metrics.service.PeriodsDataService;
import org.sonatype.cs.metrics.util.SqlStatements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AnalysisController {
  private static final Logger log = LoggerFactory.getLogger(AnalysisController.class);

  @Autowired
  private AnalysisService analysisService;
  
  @Autowired
  private PeriodsDataService periodsDataService;
	     

  @GetMapping({ "/analysis" })
  public String insights(Model model) throws ParseException {
	  log.info("In AnalysisController");

  	  Map<String, Object> periodsData = periodsDataService.getPeriodData(SqlStatements.METRICTABLENAME);
      Map<String, Object> analysisData = analysisService.getAnalysisData(periodsData);
      model.mergeAttributes(analysisData);

      return "analysis";
  }
}
