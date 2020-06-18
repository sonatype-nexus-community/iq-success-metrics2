package org.demo.smproto.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
	
	private static final Logger log = LoggerFactory.getLogger(HomeController.class);

	@GetMapping({"/", "/home"})
	public String home() {
		log.info("In HomeController");

		return "home";
	}
}
