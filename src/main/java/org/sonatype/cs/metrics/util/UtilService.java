package org.sonatype.cs.metrics.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sonatype.cs.metrics.controller.UnsignedController;
import org.sonatype.cs.metrics.model.DbRow;
import org.sonatype.cs.metrics.model.DbRowStr;
import org.sonatype.cs.metrics.service.DataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class UtilService {
	
    private static final Logger log = LoggerFactory.getLogger(UtilService.class);

    private static long oneWeekMs = 604800000;
    private static long oneMonthMs = 2629800000L;
    
    @Value("${data.previous.weeks}")
	private int dataPreviousWeeks;
    
    @Value("${data.previous.months}")
	private int dataPreviousMonths;
    
	@Autowired
	private DataService dataService;
	
    public String latestPeriod() {
	    String latestPeriod = dataService.runSql(SqlStatement.LatestTimePeriodStart).get(0).getLabel();
		return latestPeriod;
	}
    
    public String getPreviousPeriod() throws ParseException {
	    String currentPeriod = this.latestPeriod();
	    log.info("Current Period: " + currentPeriod);
	    	    
	    long currentPeriodMs = this.convertDateStr(currentPeriod);
	    //log.info("Current Period (ms): " + Long.toString(currentPeriodMs));
	    
	    String timePeriod = this.getTimePeriod();
	    
	    long previousPeriodCalc;
	    long previousPeriodMs;
	    
	    if (timePeriod == "week") {
	    	previousPeriodCalc = oneWeekMs;
 	    }
	    else {
	    	previousPeriodCalc = oneMonthMs;
 	    }
	    
	    int dataPreviousPeriod = previousPeriodFrequency();
	    previousPeriodMs = currentPeriodMs - (previousPeriodCalc * dataPreviousPeriod);
	    //log.info("Previous Period (ms): " + previousPeriodMs);
	  
	    DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
	    String previousPeriod = df.format(previousPeriodMs);
	    log.info("Previous Period: " + df.format(previousPeriodMs) + " (" + dataPreviousPeriod + " " + timePeriod + "s)");

	    log.info("Period Frequency: " + timePeriod);

		return previousPeriod;
	}
    
    public int previousPeriodFrequency() throws ParseException {
    	
    	int dataPreviousPeriod = 0;
    	
	    String timePeriod = this.getTimePeriod();
    	
		if (timePeriod == "week") {
	    	dataPreviousPeriod  = dataPreviousWeeks;
	    }
	    else {
	    	dataPreviousPeriod = dataPreviousMonths;
	    }
		
		return dataPreviousPeriod;
    }
	
	public String getTimePeriod() throws ParseException {
		List<DbRow> timePeriods = dataService.runSql(SqlStatement.TimePeriods);
		
		long oneWeek = 604800000;
		
		String timePeriodLabel = "Week";
		String firstTimePeriod;
		String secondTimePeriod;
		
		if (timePeriods.size() > 1) {
			firstTimePeriod = timePeriods.get(0).getLabel().toString();
			secondTimePeriod = timePeriods.get(1).getLabel().toString();

			long fp = this.convertDateStr(firstTimePeriod);
			long sp = this.convertDateStr(secondTimePeriod);
			
			long diff = sp - fp;

			if (diff <= oneWeek) {
				timePeriodLabel = "week";
			}
			else {
				timePeriodLabel = "month";

			}
		}
		else {
			timePeriodLabel = "week";
		}
		
		return timePeriodLabel;
	}
	
	private Long convertDateStr(String str) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date = sdf.parse(str);
		long millis = date.getTime();
		return millis;
	}

	public Map<String, Object> dataMap(String key, List<DbRowStr> data) {

        Map<String, Object> map = new HashMap<>();

        if (data.size() > 0){
            map.put(key + "Data", data);
            map.put(key + "Number", data.size());
            map.put(key, true);
        }
        else {
            map.put(key + "Number", 0);
            map.put(key, false);
        }

        return map;
    }
    
}
