package org.nexusiq.successmetrics.service;

import java.util.List;

import org.nexusiq.successmetrics.model.Metric;
import org.springframework.data.repository.PagingAndSortingRepository;


public interface IMetricsService extends PagingAndSortingRepository<Metric, Long>{

	List<Metric> findAll();
	List<Metric> findByOrderByTimePeriodStartAsc();	
	List<Metric> findByOrderByOrganizationNameAsc();	

}
