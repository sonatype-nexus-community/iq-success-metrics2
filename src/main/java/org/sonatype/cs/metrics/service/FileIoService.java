package org.sonatype.cs.metrics.service;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


@Service
public class FileIoService {
	private static final Logger log = LoggerFactory.getLogger(FileIoService.class);

	@Value("${pdf.outputdir}")
	private String outputdir;

	
	public void writeCsvFile(String csvFilename, List<String[]> csvData) throws IOException {
		
		String[] header = { "Measure", "Before", "After", "Delta", "Change (%)", "xTimes"};
		
		try {
			BufferedWriter writer = Files.newBufferedWriter(Paths.get(csvFilename));
	
			writer.write(String.join(",", header));
			writer.newLine();
			
			for (String[] array : csvData) {
				log.info(" writing: " + Arrays.toString(array));
				writer.write(String.join(",", Arrays.asList(array)));
				writer.newLine();
		    }	
			
			writer.close();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	public String makeFilename(String prefix, String extension) throws IOException {
		LocalDateTime instance = LocalDateTime.now();
	    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("ddMMyy_HHmm");
	
	    String filename = prefix + "-" + formatter.format(instance) + "." + extension;
	
	    Path path = Paths.get(outputdir);
	
	    if (!Files.exists(path)){
	      Files.createDirectory(path);
	    }
	
	    String filepath = outputdir + File.separator + filename;
	    
	    return filepath;
	}

}
