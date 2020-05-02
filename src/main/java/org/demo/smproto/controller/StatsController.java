package org.demo.smproto.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class StatsController {
	
	@GetMapping({"/stats"})
	public String stats() {
		return "stats";
	}

}
