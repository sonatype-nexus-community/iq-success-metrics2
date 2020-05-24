package org.demo.smproto.service;

import java.util.List;

import org.demo.smproto.model.Metric;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Component;

@Component
public interface IMetricsRepositoryService extends PagingAndSortingRepository<Metric, Long>{

	List<Metric> findAll();
	
	List<Metric> findByOrderByTimePeriodStartAsc();
}
