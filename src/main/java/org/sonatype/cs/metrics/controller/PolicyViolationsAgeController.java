package org.sonatype.cs.metrics.controller;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sonatype.cs.metrics.model.DbRowStr;
import org.sonatype.cs.metrics.service.DataService;
import org.sonatype.cs.metrics.util.SqlStatement;
import org.sonatype.cs.metrics.util.UtilService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PolicyViolationsAgeController {
	private static final Logger log = LoggerFactory.getLogger(PolicyViolationsAgeController.class);

    @Autowired
    private DataService dataService;

    @Autowired
    private UtilService utilService;

    @GetMapping({ "/policyViolationsAge" })
    public String policyViolationsAge(Model model) {

        List<DbRowStr> age7Data =  dataService.runSqlStr(SqlStatement.PolicyViolationsAge7);
		List<DbRowStr> age30Data = dataService.runSqlStr(SqlStatement.PolicyViolationsAge30);
		List<DbRowStr> age60Data =  dataService.runSqlStr(SqlStatement.PolicyViolationsAge60);
        List<DbRowStr> age90Data =  dataService.runSqlStr(SqlStatement.PolicyViolationsAge90);

        Map<String, Object> age7Map = utilService.dataMap("age7", age7Data);
        Map<String, Object> age30Map = utilService.dataMap("age30", age30Data);
        Map<String, Object> age60Map = utilService.dataMap("age60", age60Data);
        Map<String, Object> age90Map = utilService.dataMap("age90", age90Data);

        model.mergeAttributes(age7Map);
        model.mergeAttributes(age30Map);
        model.mergeAttributes(age60Map);
        model.mergeAttributes(age90Map);

        model.addAttribute("status", true);

        return "reportPolicyViolationsAge";
    }
}
