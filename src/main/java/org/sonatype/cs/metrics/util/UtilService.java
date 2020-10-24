package org.sonatype.cs.metrics.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.sonatype.cs.metrics.model.DbRow;
import org.sonatype.cs.metrics.model.DbRowStr;
import org.sonatype.cs.metrics.service.DataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UtilService {

	@Autowired
	private DataService dataService;
	
    public String latestPeriod() {
	    String latestPeriod = dataService.runSql(SqlStatement.LatestTimePeriodStart).get(0).getLabel();
		return latestPeriod;
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
