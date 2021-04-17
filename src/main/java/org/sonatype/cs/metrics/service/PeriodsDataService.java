package org.sonatype.cs.metrics.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sonatype.cs.metrics.model.DbRow;
import org.sonatype.cs.metrics.util.SqlStatement;
import org.sonatype.cs.metrics.util.SqlStatementPreviousPeriod;
import org.sonatype.cs.metrics.util.UtilService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.sonatype.cs.metrics.service.DbService;


@Service
public class PeriodsDataService {
	private static final Logger log = LoggerFactory.getLogger(PeriodsDataService.class);
	
	@Autowired
	private DbService dbService;

    private static int oneDayMs = 86400000;
    private static long oneWeekMs = 604800000;
    private static long oneMonthMs = 2629800000L;
    
	public Map<String, Object> getPeriodData() throws ParseException {
		Map<String, Object> model = new HashMap<>();
		
		String timePeriodFrequency = this.getTimePeriodFrequency();
		String startPeriod = this.getStartPeriod();  
		String midPeriod = this.getMidPeriod();
		String endPeriod = this.getEndPeriod();
	
		int periodsRange = this.getMidToEndPeriodsRange();

		String endStr = "";

		if (periodsRange > 1) {
			endStr = "s";
		}

		String midPeriodStr = "/" + periodsRange + " " + timePeriodFrequency + endStr;


		model.put("endPeriodDateRangeStr", "(" + startPeriod + " - " + endPeriod + ")");
		model.put("midPeriodDateRangeStr", "(" + startPeriod + " - " + midPeriod + midPeriodStr + ")");

		model.put("timePeriodFrequency", timePeriodFrequency);
		model.put("endPeriod", endPeriod);
		model.put("midPeriod", midPeriod);

		return model;
	}
	
	
	public String getStartPeriod() {
	    List<DbRow> timePeriods = dbService.runSql(SqlStatement.TimePeriods);

		String startPeriod = timePeriods.get(0).getLabel();
		return startPeriod;
	}
	
	public String getMidPeriod() {
	    List<DbRow> timePeriods = dbService.runSql(SqlStatement.TimePeriods);

		String midPeriod = timePeriods.get(timePeriods.size()/2).getLabel();
		return midPeriod;
	}
	
	public String getEndPeriod() {
	    List<DbRow> timePeriods = dbService.runSql(SqlStatement.TimePeriods);

		String endPeriod = timePeriods.get(timePeriods.size()-1).getLabel();
		return endPeriod;
	}
	
	public int getMidToEndPeriodsRange() throws ParseException {
    	String endPeriod = this.getEndPeriod();
    	String midPeriod = this.getMidPeriod();
    	int periodRange = this.getPeriodsDiff(endPeriod, midPeriod);
    	return periodRange;
    }

	private String getTimePeriodFrequency() throws ParseException {
	    List<DbRow> timePeriods = dbService.runSql(SqlStatement.TimePeriods);
		
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
	
	private int getPeriodsDiff(String endPeriod, String midPeriod) throws ParseException {
    	int diff = 0;
    	
    	String timePeriod = this.getTimePeriodFrequency();
    	
    	long endPeriodMs = convertDateStr(endPeriod);
    	long midPeriodMs = convertDateStr(midPeriod);
    	long diffMs = endPeriodMs - midPeriodMs;
    	long diffDays = diffMs/oneDayMs;
    	
		if (timePeriod == "week") {
	    	diff  = (int) (diffDays/7);
	    }
	    else {
	    	diff = (int) (diffDays/28);
	    }
		
		return diff;
	}
    
}
