package org.demo.smproto.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class ReportController {
	@GetMapping({"/report"})
    public String report(Model model, @RequestParam(value="period", required=false, defaultValue="Month") String period) {
		model.addAttribute("period", period);
        return "report";
    }
}

