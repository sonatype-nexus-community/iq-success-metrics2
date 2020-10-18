package org.sonatype.cs.nxmetrics.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sonatype.cs.nxmetrics.model.DbRow;
import org.sonatype.cs.nxmetrics.model.Mttr;
import org.sonatype.cs.nxmetrics.service.DataService;
import org.sonatype.cs.nxmetrics.util.SqlStatement;
import org.sonatype.cs.nxmetrics.util.TimePeriodService;

@Controller
public class ApplicationsController {

    private static final Logger log = LoggerFactory.getLogger(ApplicationsController.class);

    @Autowired
    private DataService dataService;

    @Autowired
    private TimePeriodService timePeriodService;

    @GetMapping({ "/applications" })
    public String applications(Model model) {

        log.info("In ApplicationsController");

        String latestTimePeriod = timePeriodService.latestPeriod();

        List<DbRow> applicationsOnboarded = dataService.runSql(SqlStatement.ApplicationsOnboarded);
        List<DbRow> numberOfScans = dataService.runSql(SqlStatement.NumberOfScans);
        List<DbRow> numberOfApplicationsScanned = dataService.runSql(SqlStatement.NumberOfApplicationsScanned);
        List<Mttr> mttr = dataService.runSqlMttr(SqlStatement.MTTR);

		model.addAttribute("applicationsOnboarded", applicationsOnboarded);
		model.addAttribute("numberOfScans", numberOfScans);
		model.addAttribute("numberOfApplicationsScanned", numberOfApplicationsScanned);
        model.addAttribute("mttr", mttr);

        String applicationOpenViolations = SqlStatement.ApplicationsOpenViolations + " where time_period_start = '" + latestTimePeriod + "' group by application_name" + " order by 2 desc, 3 desc";
        List<DbRow> aov = dataService.runSql(applicationOpenViolations);

        String organisationOpenViolations = SqlStatement.OrganisationsOpenViolations + " where time_period_start = '" + latestTimePeriod + "' group by organization_name" + " order by 2 desc, 3 desc";
        List<DbRow> oov = dataService.runSql(organisationOpenViolations);

        model.addAttribute("mostCriticalApplicationName", aov.get(0).getLabel());
        model.addAttribute("mostCriticalApplicationCount", aov.get(0).getPointA());
        model.addAttribute("leastCriticalApplicationName", aov.get(aov.size()-1).getLabel());
        model.addAttribute("leastCriticalApplicationCount", aov.get(aov.size()-1).getPointA());

        model.addAttribute("applicationsSecurityRemediation", dataService.runSql(SqlStatement.ApplicationsSecurityRemediation));
        model.addAttribute("applicationsLicenseRemediation", dataService.runSql(SqlStatement.ApplicationsLicenseRemediation));	
				
		model.addAttribute("mostCriticalOrganisationsData", oov);
		model.addAttribute("mostCriticalApplicationsData", aov);
		model.addAttribute("mostScannedApplicationsData", dataService.runSql(SqlStatement.MostScannedApplications));

        return "reportApplications";
    }
}
