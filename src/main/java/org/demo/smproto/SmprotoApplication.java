package org.demo.smproto;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import org.demo.smproto.model.Metric;
import org.demo.smproto.service.IMetricsRepositoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.opencsv.bean.ColumnPositionMappingStrategy;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

@SpringBootApplication
public class SmprotoApplication {
	
	private static final Logger log = LoggerFactory.getLogger(SmprotoApplication.class);
	
	@Value("${metrics.csvfile}")
	private Path csvfile;

	public static void main(String[] args) {
		SpringApplication.run(SmprotoApplication.class, args);
	}
	
	@Bean
	public CommandLineRunner LoadData(IMetricsRepositoryService repository) {
		
		return (args) -> {	
			log.info("csv file is " + csvfile);
			
			List<Metric> metrics = readCSVFile();
			
			log.info("All metrics from CSV file:");
			log.info("-------------------------------");
			log.info("Number of entries: " + metrics.size());
			
			for (Metric m : metrics) {
				log.info("saving record: " + m.toString());
				repository.save(new Metric(m));
			}
			
			log.info("All metrics from database:");
			log.info("-------------------------------");
			
			for (Metric m : repository.findAll()) {
				log.info(m.toString());
			}
		};
	}
	
	private  List<Metric> readCSVFile() throws IOException{
		
		List<Metric> metrics = null;
		
		BufferedReader reader = null;
		try {
			
			ColumnPositionMappingStrategy ms = new ColumnPositionMappingStrategy();
		    ms.setType(Metric.class);
		     
			reader = Files.newBufferedReader(csvfile);
			
            CsvToBean<Metric> csvToBean = new CsvToBeanBuilder(reader)
                    .withType(Metric.class)
                    .build();

            metrics = csvToBean.parse();    
        } 
		finally {
            if (reader != null) reader.close();
		}
		
		return metrics;
	}	

}
