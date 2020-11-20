package org.sonatype.cs.metrics.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sonatype.cs.metrics.model.DbRow;
import org.sonatype.cs.metrics.service.DataService;
import org.sonatype.cs.metrics.util.SqlStatement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LicenseViolationsController {

    private static final Logger log = LoggerFactory.getLogger(LicenseViolationsController.class);

    @Autowired
    private DataService dataService;

    @GetMapping({ "/licenseViolations" })
    public String licenseViolations(Model model) {

        log.info("In LicenseViolationsController");

        List<DbRow> LicenseViolations = dataService.runSql(SqlStatement.LicenseViolations);
        List<DbRow> discoveredLicenseViolations = dataService.runSql(SqlStatement.DiscoveredLicenseViolations);
        //List<DbRow> openLicenseViolations = dataService.runSql(SqlStatement.OpenLicenseViolations);
        List<DbRow> fixedLicenseViolations = dataService.runSql(SqlStatement.FixedLicenseViolations);
        List<DbRow> waivedLicenseViolations = dataService.runSql(SqlStatement.WaivedLicenseViolations);

        model.addAttribute("LicenseViolations", LicenseViolations);
        model.addAttribute("discoveredLicenseViolations", discoveredLicenseViolations);
        //model.addAttribute("openLicenseViolations", openLicenseViolations);
		model.addAttribute("fixedLicenseViolations", fixedLicenseViolations);
		model.addAttribute("waivedLicenseViolations", waivedLicenseViolations);

        return "reportLicenseViolations";
    }
    
}
