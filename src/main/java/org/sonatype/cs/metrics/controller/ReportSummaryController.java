package org.sonatype.cs.metrics.controller;

import java.text.ParseException;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sonatype.cs.metrics.service.ApplicationsDataService;
import org.sonatype.cs.metrics.service.LicenseDataService;
import org.sonatype.cs.metrics.service.MetricsService;
import org.sonatype.cs.metrics.service.PeriodsDataService;
import org.sonatype.cs.metrics.service.SecurityDataService;
import org.sonatype.cs.metrics.service.SummaryTotalsDataService;
import org.sonatype.cs.metrics.util.SqlStatements;
import org.sonatype.cs.metrics.util.SummaryDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ReportSummaryController {
	private static final Logger log = LoggerFactory.getLogger(ReportSummaryController.class);
    
	
	@Autowired
	private PeriodsDataService periodsDataService;
	
    @Autowired
    private MetricsService metricsService;

    @GetMapping({ "/summary" })
    public String applications(Model model) throws ParseException {

        log.info("In ReportSummaryController");
        
		Map<String, Object> periodsData = periodsDataService.getPeriodData(SqlStatements.METRICTABLENAME);
        Map<String, Object> metrics = metricsService.getMetrics(SqlStatements.METRICTABLENAME, periodsData);
        
        model.mergeAttributes(periodsData);
        model.mergeAttributes(metrics);

		return "reportSummary";
    }
}
