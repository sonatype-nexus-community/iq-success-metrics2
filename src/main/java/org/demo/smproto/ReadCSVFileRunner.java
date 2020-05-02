package org.demo.smproto;

import java.io.BufferedReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Locale;

import org.demo.smproto.model.Metric;
import org.demo.smproto.service.IMetricsRepositoryService;
import org.demo.smproto.service.OSNameService;
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
public class ReadCSVFileRunner implements CommandLineRunner {
	
	private static final Logger log = LoggerFactory.getLogger(ReadCSVFileRunner.class);
	
	@Autowired 
	private IMetricsRepositoryService repository;
	
	@Autowired
	private OSNameService osName;
	
	@Override
	public void run(String... args) throws Exception {
		
		Path csvFileName = osName.getCSVFileName();
	    
		log.info("Reading csv file: " + csvFileName);
		
		List<Metric> metrics = null;
		
		BufferedReader reader = null;
		
		try {
			
			ColumnPositionMappingStrategy ms = new ColumnPositionMappingStrategy();
		    ms.setType(Metric.class);
		     
			reader = Files.newBufferedReader(csvFileName);
			
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
		
		log.info("Number of entries: " + metrics.size());

	}
}
