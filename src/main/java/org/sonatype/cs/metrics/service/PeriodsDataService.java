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

		model.put("midPeriod", midPeriod);
		model.put("midPeriodDate1RangeStr", "(" + startPeriod + " - " + midPeriod + ")");
		model.put("midPeriodDate2RangeStr", "(" + midPeriod + " - " + endPeriod + ")");

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
