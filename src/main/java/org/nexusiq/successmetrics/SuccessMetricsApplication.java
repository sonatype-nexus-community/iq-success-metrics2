package org.nexusiq.successmetrics;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages={"org.nexusiq.successmetrics"})
public class SuccessMetricsApplication {
		
	public static void main(String[] args) {
		SpringApplication.run(SuccessMetricsApplication.class, args);
	}
}
