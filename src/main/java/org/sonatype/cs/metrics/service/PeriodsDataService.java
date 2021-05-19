package org.sonatype.cs.metrics.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.sonatype.cs.metrics.model.DbRow;
import org.sonatype.cs.metrics.util.SqlStatements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class PeriodsDataService {
	
	@Autowired
	private DbService dbService;

	
	public Map<String, Object> getPeriodData(String tableName) throws ParseException {
		Map<String, Object> model = new HashMap<>();
		
	    List<DbRow> timePeriods = dbService.runSql(tableName, SqlStatements.TimePeriods);
		
	    int numberOfPeriods = timePeriods.size();
	    String startPeriod = timePeriods.get(0).getLabel();
		String endPeriod = timePeriods.get(timePeriods.size()-1).getLabel();
		
	    String firstTimePeriod = timePeriods.get(0).getLabel().toString();
		String secondTimePeriod = timePeriods.get(1).getLabel().toString();
		String timePeriodFrequency = this.getTimePeriodFrequency(numberOfPeriods, firstTimePeriod, secondTimePeriod);
		
		model.put("timePeriodFrequency", timePeriodFrequency);
		model.put("startPeriod", startPeriod);
		model.put("endPeriod", endPeriod);
		model.put("periodDateRangeStr", "(" + startPeriod + " - " + endPeriod + ")");

		int midPeriodIndex = numberOfPeriods/2;
		if (midPeriodIndex % 2 == 0) {
			midPeriodIndex--;
		}
		
		String midPeriod = timePeriods.get(midPeriodIndex).getLabel();
		
		String midPeriodEnd = midPeriod;
		String midPeriodStart = timePeriods.get(midPeriodIndex+1).getLabel();

		model.put("midPeriod", midPeriod);
		model.put("midPeriodDate1RangeStr", "(" + startPeriod + " - " + midPeriodEnd + ")");
		model.put("midPeriodDate2RangeStr", "(" + midPeriodStart + " - " + endPeriod + ")");

		return model;
	}
	
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
	
}
