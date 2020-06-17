package org.demo.smproto.model;

public class ApplicationEvaluation {
	
	private String evaluationDate;
	private String applicationName;
	private String stage;
	
	public ApplicationEvaluation() {}
	
	public ApplicationEvaluation(String evaluationDate, String applicationName, String stage) {
		this.evaluationDate = evaluationDate;
		this.applicationName = applicationName;
		this.stage = stage;
	}

	public String getEvaluationDate() {
		return evaluationDate;
	}

	public void setEvaluationDate(String evaluationDate) {
		this.evaluationDate = evaluationDate;
	}

	public String getApplicationName() {
		return applicationName;
	}

	public void setApplicationName(String applicationName) {
		this.applicationName = applicationName;
	}

	public String getStage() {
		return stage;
	}

	public void setStage(String stage) {
		this.stage = stage;
	}

	@Override
	public String toString() {
		return "ApplicationEvaluation [evaluationDate=" + evaluationDate + ", applicationName=" + applicationName
				+ ", stage=" + stage + "]";
	}

}
