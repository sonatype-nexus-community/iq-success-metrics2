package org.sonatype.cs.metrics.util;

import java.util.List;

import org.sonatype.cs.metrics.model.DbRow;
import org.springframework.stereotype.Service;

@Service
public class HelperService {
	
	public int[] getPointsSumAndAverage(List<DbRow> dataList) {

		int countLabels = 0;
		int sumData = 0;

		for (DbRow dp : dataList) {
			int count = (int) dp.getPointA();

			if (count > 0) {
				sumData += count;
				countLabels++;
			}
		}

		int avg = sumData / countLabels;

		int[] values = new int[] { sumData, avg };
		return values;
	}
	
	
	public Object getPointsAverage(List<Float> points) {
		int countPoints = 0;

		float sumData = 0;

		for (float dp : points) {

			if (dp > 0) {
				sumData += dp;
				countPoints++;
			}
		}

		return sumData / countPoints;
	}

}
