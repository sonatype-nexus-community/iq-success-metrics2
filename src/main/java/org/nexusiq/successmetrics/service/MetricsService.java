package org.nexusiq.successmetrics.service;

import java.util.List;

import org.nexusiq.successmetrics.model.Metric;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MetricsService {
	
	private static final Logger log = LoggerFactory.getLogger(MetricsService.class);
	
	@Autowired
	private IMetricsService metricsService;
	
	public List<Metric> getAllMetrics() {
		return metricsService.findByOrderByOrganizationNameAsc();
	}
	
}