package org.demo.smproto;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;


@Order(value=1)
@Component
public class StartUpRunner implements CommandLineRunner {
	
	private static final Logger log = LoggerFactory.getLogger(StartUpRunner.class);
	
	@Override
	public void run(String... args) throws Exception {
		log.info("Ready for browsing");
	}
}
