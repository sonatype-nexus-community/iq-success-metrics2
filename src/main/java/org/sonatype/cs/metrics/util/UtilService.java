package org.sonatype.cs.metrics.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
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

    private static int oneDayMs = 86400000;
    private static long oneWeekMs = 604800000;
    private static long oneMonthMs = 2629800000L;
    
	@Autowired
	private DataService dataService;
	
    public String getLatestPeriod() {
	    String latestPeriod = dataService.runSql(SqlStatement.LatestTimePeriodStart).get(0).getLabel();
		return latestPeriod;
	}
    
    public int getPreviousPeriodRange() throws ParseException {
    	String currentPeriod = this.getLatestPeriod();
    	String previousPeriod = this.getPreviousPeriod();
    	int previousPeriodRange = this.getDateDiff(currentPeriod, previousPeriod);
    	return previousPeriodRange;
    }
    
    private int getDateDiff(String currentPeriod, String previousPeriod) throws ParseException {
    	int diff = 0;
    	
    	String timePeriod = this.getTimePeriod();
    	
    	long currentPeriodMs = convertDateStr(currentPeriod);
    	long previousPeriodMs = convertDateStr(previousPeriod);
    	long diffMs = currentPeriodMs - previousPeriodMs;
    	long diffDays = diffMs/oneDayMs;
    	
		if (timePeriod == "week") {
	    	diff  = (int) (diffDays/7);
	    }
	    else {
	    	diff = (int) (diffDays/28);
	    }
		
		return diff;
	}

	public String getPreviousPeriod() {
		List<DbRow> timePeriods = dataService.runSql(SqlStatement.TimePeriods);
		int numberOfPeriods = timePeriods.size();
		
		int periodNumber = 0;
		int minPeriods = 2;
		
		String midPeriod = "none";
		
		if (numberOfPeriods == minPeriods) {
			midPeriod = timePeriods.get(0).getLabel();
		}
		
		if (numberOfPeriods > minPeriods) {
			periodNumber = numberOfPeriods/2; 
			midPeriod = timePeriods.get(periodNumber).getLabel();
		}
		
		return midPeriod;
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
