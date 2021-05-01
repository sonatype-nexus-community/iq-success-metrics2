package org.sonatype.cs.metrics.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sonatype.cs.metrics.model.DbRow;
import org.sonatype.cs.metrics.model.Mttr;
import org.sonatype.cs.metrics.service.DataService;
import org.sonatype.cs.metrics.service.MetricsService;
import org.sonatype.cs.metrics.service.PeriodsDataService;
import org.sonatype.cs.metrics.util.SqlStatement;
import org.sonatype.cs.metrics.util.SqlStatementPreviousPeriod;
import org.sonatype.cs.metrics.util.SqlStatements;
import org.sonatype.cs.metrics.util.SummaryDataService;
import org.sonatype.cs.metrics.util.SummaryDataServicePreviousPeriod;
import org.sonatype.cs.metrics.util.UtilService;

@Controller
public class ConparePeriodsController {
  private static final Logger log = LoggerFactory.getLogger(ConparePeriodsController.class);

  @Autowired
  private PeriodsDataService periodsDataService;
  
  @Autowired
  private MetricsService metricsService;

  @GetMapping({ "/compare" })
  public String applications(Model model) throws ParseException {
    log.info("In ConparePeriodsController");
   
	Map<String, Object> periodsData = periodsDataService.getPeriodData(SqlStatements.METRICTABLENAME);
    Map<String, Object> p1metrics = metricsService.getMetrics(SqlStatements.METRICP1TABLENAME, periodsData);
    Map<String, Object> p2metrics = metricsService.getMetrics(SqlStatements.METRICP2TABLENAME, periodsData);

    model.mergeAttributes(periodsData);
    model.addAttribute("p1", p1metrics);
    model.addAttribute("p2", p2metrics);

    return "comparePeriods";
  }
}
