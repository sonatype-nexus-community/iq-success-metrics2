package org.demo.smproto;

import java.io.BufferedReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import org.demo.smproto.model.Metric;
import org.demo.smproto.service.IMetricsRepositoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.opencsv.bean.ColumnPositionMappingStrategy;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

@Component
@Order(value = 2)
public class ReadCSVFileRunner implements CommandLineRunner {
	
	private static final Logger log = LoggerFactory.getLogger(ReadCSVFileRunner.class);
	
	@Autowired 
	private IMetricsRepositoryService repository;
	
	@Value("${metrics.csvfile}")
	private Path csvFilePath;
	
	@Override
	public void run(String... args) throws Exception {
		
		log.info("Read csv file...");
		log.info("reading file: " + csvFilePath);
		
		List<Metric> metrics = null;
		
		BufferedReader reader = null;
		
		try {
			
			ColumnPositionMappingStrategy ms = new ColumnPositionMappingStrategy();
		    ms.setType(Metric.class);
		     
			reader = Files.newBufferedReader(csvFilePath);
			
            CsvToBean<Metric> csvToBean = new CsvToBeanBuilder(reader)
                    .withType(Metric.class)
                    .build();

            metrics = csvToBean.parse();    
        } 
		finally {
            if (reader != null) reader.close();
		}
		
		for (Metric m : metrics) {
			repository.save(new Metric(m));
		}
	 
		//		log.info("All metrics from database:");
		//		log.info("-------------------------------");
		//	
		//		for (Metric m : repository.findAll()) {
		//			log.info(m.toString());
		//		}
		//	
		//		log.info("-------------------------------");	
		
		log.info("Number of entries: " + metrics.size());

	}
}
