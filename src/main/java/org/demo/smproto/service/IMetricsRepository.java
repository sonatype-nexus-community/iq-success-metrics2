package org.demo.smproto.service;

import java.util.List;

import org.demo.smproto.model.Metric;
import org.springframework.data.repository.CrudRepository;

public interface IMetricsRepository extends CrudRepository<Metric, Long>{
	
	List<Metric> findByApplicationId(String applicationId);
	
	List<Metric> findByApplicationPublicId(String applicationPublicId);
	
	List<Metric> findByApplicationName(String applicationName);
	
	List<Metric> findByOrganizationId(String organisationId);
	
	List<Metric> findByOrganizationName(String organisationName);
	
	List<Metric> findByTimePeriodStart(String timePeriodStart);
	
	List<Metric> findAll();
	
	Metric findById(long id);
	
	
}
