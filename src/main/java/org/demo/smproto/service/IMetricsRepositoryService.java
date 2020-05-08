package org.demo.smproto.service;

import java.util.List;

import org.demo.smproto.model.Metric;
import org.springframework.data.repository.PagingAndSortingRepository;


public interface IMetricsRepositoryService extends PagingAndSortingRepository<Metric, Long>{

	List<Metric> findAll();
	
	List<Metric> findByOrderByTimePeriodStartAsc();
}
