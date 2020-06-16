package org.demo.smproto.controller;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

@Controller
public class PeriodSnapshotController {
	
	@GetMapping("/period")
    public String period() {
        return "period";
	}
	
	@PostMapping("/snapshot")
    public String snapshot(@RequestParam("file") MultipartFile file, Model model) {
		
		 if (file.isEmpty()) {
	            model.addAttribute("message", "Please select a CSV file to upload.");
	            model.addAttribute("status", false);
	     } 
		 else {

	     }
	            
		return "snapshot";
		
	}


}
