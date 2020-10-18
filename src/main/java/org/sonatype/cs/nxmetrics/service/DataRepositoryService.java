package org.sonatype.cs.nxmetrics.service;

import org.sonatype.cs.nxmetrics.model.Metric;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DataRepositoryService extends CrudRepository<Metric, Long> {

}
