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
import org.sonatype.cs.metrics.util.SqlStatements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class PeriodsDataService {
	private static final Logger log = LoggerFactory.getLogger(PeriodsDataService.class);
	
	@Autowired
	private DbService dbService;

    private static int oneDayMs = 86400000;
    private static long oneWeekMs = 604800000;
    private static long oneMonthMs = 2629800000L;
    
    
	public Map<String, Object> getPeriodData2(String tableName) throws ParseException {
		Map<String, Object> model = new HashMap<>();
		
	    List<DbRow> timePeriods = dbService.runSql(tableName, SqlStatements.TimePeriods);
		
	    int tmSize = timePeriods.size();
	    String startPeriod = timePeriods.get(0).getLabel();
		String endPeriod = timePeriods.get(timePeriods.size()-1).getLabel();
		
	    String firstTimePeriod = timePeriods.get(0).getLabel().toString();
		String secondTimePeriod = timePeriods.get(1).getLabel().toString();
		String timePeriodFrequency = this.getTimePeriodFrequency(tmSize, firstTimePeriod, secondTimePeriod);
		
		model.put("timePeriodFrequency", timePeriodFrequency);
		model.put("startPeriod", startPeriod);
		model.put("endPeriod", endPeriod);
		model.put("periodDateRangeStr", "(" + startPeriod + " - " + endPeriod + ")");

		int midPeriodIndex = timePeriods.size()/2-1;
		
		String midPeriod1 = timePeriods.get(midPeriodIndex).getLabel();
		String midPeriod2 = timePeriods.get(midPeriodIndex+1).getLabel();

		String[] midPeriod = new String[] { midPeriod1, midPeriod2 };		

		String midPeriodEnd = midPeriod[0];
		String midPeriodStart = midPeriod[1];

		int midPeriod1Range = this.getPeriodsDiff(timePeriodFrequency, startPeriod, midPeriodEnd);
		int midPeriod2Range = this.getPeriodsDiff(timePeriodFrequency, midPeriodStart, endPeriod);

		String endStr = "";

//		if (periodsRange > 1) {
//			endStr = "s";
//		}
//
//		String midPeriodStr = "/" + periodsRange + " " + timePeriodFrequency + endStr;
//
//
//		model.put("midPeriodDateRangeStr", "(" + startPeriod + " - " + midPeriod + midPeriodStr + ")");

		model.put("midPeriodEnd", midPeriodEnd);
		model.put("midPeriodStart", midPeriodStart);
		model.put("midPeriodDate1RangeStr", "(" + startPeriod + " - " + midPeriodEnd + ")");
		model.put("midPeriodDate2RangeStr", "(" + midPeriodStart + " - " + endPeriod + ")");

		return model;
	}
	
	public Map<String, Object> getPeriodData(String tableName) throws ParseException {
		Map<String, Object> model = new HashMap<>();
		
	    List<DbRow> timePeriods = dbService.runSql(tableName, SqlStatements.TimePeriods);
		
	    int tmSize = timePeriods.size();
	    String startPeriod = timePeriods.get(0).getLabel();
		String endPeriod = timePeriods.get(timePeriods.size()-1).getLabel();
		
	    String firstTimePeriod = timePeriods.get(0).getLabel().toString();
		String secondTimePeriod = timePeriods.get(1).getLabel().toString();
		String timePeriodFrequency = this.getTimePeriodFrequency(tmSize, firstTimePeriod, secondTimePeriod);
		
		model.put("timePeriodFrequency", timePeriodFrequency);
		model.put("startPeriod", startPeriod);
		model.put("endPeriod", endPeriod);
		model.put("periodDateRangeStr", "(" + startPeriod + " - " + endPeriod + ")");

		int midPeriodIndex = timePeriods.size()/2-1;
		
		String midPeriod = timePeriods.get(midPeriodIndex).getLabel();
		log.info(midPeriod);

		int midPeriod1Range = this.getPeriodsDiff(timePeriodFrequency, startPeriod, midPeriod);
		int midPeriod2Range = this.getPeriodsDiff(timePeriodFrequency, midPeriod, endPeriod);

		String endStr = "";

//		if (periodsRange > 1) {
//			endStr = "s";
//		}
//
//		String midPeriodStr = "/" + periodsRange + " " + timePeriodFrequency + endStr;
//
//
//		model.put("midPeriodDateRangeStr", "(" + startPeriod + " - " + midPeriod + midPeriodStr + ")");

		model.put("midPeriod", midPeriod);
		model.put("midPeriodDate1RangeStr", "(" + startPeriod + " - " + midPeriod + ")");
		model.put("midPeriodDate2RangeStr", "(" + midPeriod + " - " + endPeriod + ")");

		return model;
	}
	
	
//	private String[] getMidPeriods(int midPeriodIndex) {
//		String midPeriod1 = timePeriods.get(midPeriodIndex).getLabel();
//		String midPeriod2 = timePeriods.get(midPeriodIndex+1).getLabel();
//
//		String[] midPeriod = new String[] { midPeriod1, midPeriod2 };
//		
//		return midPeriod;
//	}
	
//	private int getMidToEndPeriodsRange() throws ParseException {
//    	String endPeriod = this.getEndPeriod();
//    	String midPeriod = this.getMidPeriods()[0];
//    	int periodRange = this.getPeriodsDiff(endPeriod, midPeriod);
//    	return periodRange;
//    }
	
//	private int getPeriodsRange(String period1, String period2) throws ParseException {
//    	int periodRange = this.getPeriodsDiff(period1, period2);
//    	return periodRange;
//    }

	private String getTimePeriodFrequency(int tmSize, String firstTimePeriod, String secondTimePeriod) throws ParseException {
		
		long oneWeek = 604800000;
		
		String timePeriodLabel = "Week";
		
		if (tmSize > 1) {
		
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
	
	private int getPeriodsDiff(String timePeriod, String endPeriod, String midPeriod) throws ParseException {
    	int diff = 0;
    	    	
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
