package org.demo.smproto;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages={"org.demo.smproto"})
public class SmprotoApplication {
		
	public static void main(String[] args) {
		SpringApplication.run(SmprotoApplication.class, args);
	}
}
