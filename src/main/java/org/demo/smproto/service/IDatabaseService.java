package org.demo.smproto.service;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.demo.smproto.model.Metric;
import org.demo.smproto.service.SQLStatement;


@Repository
public interface IDatabaseService extends JpaRepository<Metric, Long>{
	
	@Modifying
	@Transactional
	@Query(value = "drop table if exists metric; CREATE TABLE METRIC AS SELECT * FROM CSVREAD('/var/tmp/successmetrics.csv')", nativeQuery = true)
	void LoadDb();
	
	@Modifying
	@Transactional
	@Query(value = "drop table if exists POLICYVIOLATION; CREATE TABLE POLICYVIOLATION AS SELECT * FROM CSVREAD('/Users/sotudeko/nexusiq-metrics/policyviolations.csv')", nativeQuery = true)
	void LoadPolicyViolationsDb();
	
}
