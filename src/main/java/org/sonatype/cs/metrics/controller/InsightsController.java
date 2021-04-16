package org.sonatype.cs.metrics.controller;

import java.text.ParseException;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sonatype.cs.metrics.service.InsightsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class InsightsController {
  private static final Logger log = LoggerFactory.getLogger(InsightsController.class);

  @Autowired
  private InsightsService insightsService;

  @GetMapping({ "/insights" })
  public String insights(Model model) throws ParseException {

      log.info("In InsightsController");

      Map<String, Object> insightsData = insightsService.insightsData();
      model.mergeAttributes(insightsData);

      return "reportInsights";
  }
}
