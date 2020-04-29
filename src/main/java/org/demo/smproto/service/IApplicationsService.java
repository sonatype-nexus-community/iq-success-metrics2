package org.demo.smproto.service;

import java.util.List;

import org.demo.smproto.model.Application;
import org.springframework.data.repository.CrudRepository;

public interface IApplicationsService {
	
	List<Application> CountApplicationsByTimeStartPeriod();	
}
