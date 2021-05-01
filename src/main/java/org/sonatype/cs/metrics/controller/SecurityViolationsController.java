package org.sonatype.cs.metrics.controller;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sonatype.cs.metrics.service.SecurityDataService;
import org.sonatype.cs.metrics.util.SqlStatements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SecurityViolationsController {
    private static final Logger log = LoggerFactory.getLogger(SecurityViolationsController.class);

    @Autowired
    private SecurityDataService securityDataService;

    @GetMapping({ "/securityViolations" })
    public String securityViolations(Model model) {

        log.info("In SecurityViolationsController");
        
        Map<String, Object> securityViolationsData = securityDataService.getSecurityViolations(SqlStatements.METRICTABLENAME);
        model.mergeAttributes(securityViolationsData);
		
        return "securityViolations";
    }
    
}
