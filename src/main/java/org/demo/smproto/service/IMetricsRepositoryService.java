package org.demo.smproto.service;

import java.util.List;

import org.demo.smproto.model.Metric;
import org.springframework.data.repository.CrudRepository;

public interface IMetricsRepositoryService extends CrudRepository<Metric, Long>{
	
	List<Metric> findAll();
}
