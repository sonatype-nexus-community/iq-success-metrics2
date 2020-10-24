package org.sonatype.cs.metrics.controller;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sonatype.cs.metrics.model.DbRowStr;
import org.sonatype.cs.metrics.service.DataService;
import org.sonatype.cs.metrics.util.SqlStatement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ComponentsInQuarantineController {
    private static final Logger log = LoggerFactory.getLogger(ComponentsInQuarantineController.class);

    @Autowired
    private DataService dataService;

    @GetMapping({"/componentsq"})
	public String componentsq(Model model) {
        log.info("In ComponentsInQuarantineController");

        List<DbRowStr> componentsq =  dataService.runSqlStr(SqlStatement.ComponentsInQuarantine);
        model.addAttribute("componentsq", componentsq);

        return "reportComponentsq";
    }
}
