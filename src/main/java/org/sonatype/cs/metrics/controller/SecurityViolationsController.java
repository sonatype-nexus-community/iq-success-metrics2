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
public class SecurityViolationsController {

    private static final Logger log = LoggerFactory.getLogger(SecurityViolationsController.class);

    @Autowired
    private DataService dataService;

    @GetMapping({ "/securityViolations" })
    public String securityViolations(Model model) {

        log.info("In SecurityViolationsController");

        List<DbRow> securityViolations = dataService.runSql(SqlStatement.SecurityViolations);
        List<DbRow> discoveredSecurityViolations = dataService.runSql(SqlStatement.DiscoveredSecurityViolations);
        //List<DbRow> openSecurityViolations = dataService.runSql(SqlStatement.OpenSecurityViolations);
        List<DbRow> fixedSecurityViolations = dataService.runSql(SqlStatement.FixedSecurityViolations);
        List<DbRow> waivedSecurityViolations = dataService.runSql(SqlStatement.WaivedSecurityViolations);

        model.addAttribute("securityViolations", securityViolations);
        model.addAttribute("discoveredSecurityViolations", discoveredSecurityViolations);
        //model.addAttribute("openSecurityViolations", openSecurityViolations);
		model.addAttribute("fixedSecurityViolations", fixedSecurityViolations);
		model.addAttribute("waivedSecurityViolations", waivedSecurityViolations);

        return "reportSecurityViolations";
    }
    
}
