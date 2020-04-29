package org.demo.smproto.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Entity
public class Application {
	@Id	
	@GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID",strategy = "org.hibernate.id.UUIDGenerator")
	
	@Column(name = "time_period_start")
	private String timePeriod;
	
	@Column(name = "count")
	private int count;

	
	public Application() {}

	public Application(String timePeriod, int count) {
		this.timePeriod = timePeriod;
		this.count = count;
	}
	
	public String getTimePeriod() {
		return timePeriod;
	}

	public void setTimePeriod(String timePeriod) {
		this.timePeriod = timePeriod;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	@Override
	public String toString() {
		return "Applications [timePeriod=" + timePeriod + ", count=" + count + "]";
	}
	
	
}
