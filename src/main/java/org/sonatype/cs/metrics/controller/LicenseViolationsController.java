package org.sonatype.cs.metrics.controller;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sonatype.cs.metrics.service.LicenseDataService;
import org.sonatype.cs.metrics.util.SqlStatements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LicenseViolationsController {
    private static final Logger log = LoggerFactory.getLogger(LicenseViolationsController.class);

    @Autowired
    private LicenseDataService licenseDataService;

    @GetMapping({ "/licenseViolations" })
    public String licenseViolations(Model model) {

        log.info("In LicenseViolationsController");

        Map<String, Object> licenseViolationsData = licenseDataService.getLicenseViolations(SqlStatements.METRICTABLENAME);
        model.mergeAttributes(licenseViolationsData);

        return "reportLicenseViolations";
    }
    
}
