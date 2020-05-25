package org.demo.smproto;

import java.io.BufferedReader;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import org.demo.smproto.model.Metric;
import org.demo.smproto.service.IDataService;
import org.demo.smproto.service.IDatabaseService;
import org.demo.smproto.service.IMetricsRepositoryService;
import org.demo.smproto.service.OSNameService;
import org.demo.smproto.service.RepositoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;


@Order(value=2)
@Component
public class LoadSuccessMetricsFileRunner implements CommandLineRunner {
	
	private static final Logger log = LoggerFactory.getLogger(LoadSuccessMetricsFileRunner.class);
	
	@Autowired 
	private IMetricsRepositoryService repository;
	
	@Autowired
	private OSNameService osName;
	
	@Autowired 
	private IDataService dataService;
	
	@Autowired
	private RepositoryService repositoryService;
	
//	@Autowired
//	private IDatabaseService dbService;
	
	@Override
	public void run(String... args) throws Exception {
		log.info("In: LoadSuccessMetricsFileRunner");

		Path csvFileName = osName.getCSVSuccessMetricsFilePath();
		
		log.info("Reading csv file: " + csvFileName);
				
		File f = new File(csvFileName.toString());
		
		if(f.exists() && !f.isDirectory()) { 
			
			repositoryService.LoadSuccessMetricsCsvFile(csvFileName.toString());

		
//			List<Metric> metrics = null;
//			
//			BufferedReader reader = null;
//			
//			try {
//				reader = Files.newBufferedReader(csvFileName);
//				
//	            CsvToBean<Metric> csvToBean = new CsvToBeanBuilder(reader)
//	                    .withType(Metric.class)
//	                    .build();
//	
//	            metrics = csvToBean.parse();    
//	        } 
//			finally {
//	            if (reader != null) reader.close();
//			}
			
			log.info("Loading database...");
			
			//repository.saveAll(metrics);
	
			//for (Metric m : metrics) {
				//log.info("metric: " + m);
				//repository.save(new Metric(m));
			//}
			
			//System.out.print("");
		
			//log.info("Done. Number of entries: " + metrics.size());
			
			//repositoryService.LoadSuccessMetricsCsvFile(csvFileName.toString());
			
			log.info("Ready for browsing");
		}
		else {
			log.info("No Success Metrics data");
		}

	}
}
