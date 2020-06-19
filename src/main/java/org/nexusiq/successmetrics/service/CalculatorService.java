package org.nexusiq.successmetrics.service;

import java.util.List;

import org.nexusiq.successmetrics.model.DataPoint;
import org.nexusiq.successmetrics.model.SummaryAverage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class CalculatorService {
	private static final Logger log = LoggerFactory.getLogger(CalculatorService.class);

	
	public int sumAllPoints(List<DataPoint> dataList) {
		
		int sumData = 0;

		for (DataPoint dp : dataList) {
			float rowTotal = dp.getPointA() + dp.getPointB() + dp.getPointC();
			sumData += rowTotal;
		}
		
		return sumData;
	}
	
	public int sumPoint(List<DataPoint> dataList, String point) {
		
		int sumData = 0;

		for (DataPoint dp : dataList) {
					
			float rowTotal = 0;
			
			switch (point) {
				case "A": rowTotal = dp.getPointA(); break;
				case "B": rowTotal = dp.getPointB(); break;
				case "C": rowTotal = dp.getPointC(); break;
				case "D": rowTotal = dp.getPointD(); break;
				default: rowTotal = 0;
			}
			
			sumData += rowTotal;
		}
		
		return sumData;
	}
	
	public SummaryAverage applicationsOnboardedAverage(List<DataPoint> dataList) {
		
		int countLabels = dataList.size();
		
		int numberOfApplications = (int) dataList.get(dataList.size() - 1).getPointA();
		
		int dataAverage = numberOfApplications/countLabels;

		return new SummaryAverage(numberOfApplications, dataAverage);
		
	}
	
	public SummaryAverage sumAndAveragePointA(List<DataPoint> dataList) {
		
		int countLabels = 0;

		int sumData = 0;
		
		for (DataPoint dp : dataList) {
			int count = (int) dp.getPointA();
			
			if (count > 0) {
				sumData += count;
				countLabels++;
			}
		}
		
		return new SummaryAverage(sumData, sumData/countLabels);
	}
	
	public Object averagePoint(List<Float> points) {
		int countPoints = 0;
		
		float sumData = 0;
		
		for (float dp : points) {
			
			if (dp > 0) {
				sumData += dp;
				countPoints++;
			}
		}
		
		return sumData/countPoints;
	}

}
