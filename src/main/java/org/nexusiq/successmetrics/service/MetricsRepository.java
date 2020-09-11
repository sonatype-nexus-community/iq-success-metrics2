package org.nexusiq.successmetrics.service;

import org.nexusiq.successmetrics.model.Metric;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MetricsRepository extends CrudRepository<Metric, Long> {

}
