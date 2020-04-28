package org.demo.smproto.service;

import java.util.List;

import org.demo.smproto.model.Applications;
import org.springframework.data.repository.CrudRepository;

public interface IApplicationsService {
	
	List<Applications> CountApplicationsByTimeStartPeriod();	
}
