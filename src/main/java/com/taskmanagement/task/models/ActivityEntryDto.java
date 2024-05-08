package com.taskmanagement.task.models;

import java.time.LocalDate;
import java.util.Set;

public class ActivityEntryDto {
	
	private Long id;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	private Integer consultantId;

	 private Integer projectId;
	 
	 private String activityDescription;
	 
	 private LocalDate activityEndDate; // Etkinlik bitiş tarihi

	public LocalDate getActivityEndDate() {
		return activityEndDate;
	}

	public void setActivityEndDate(LocalDate activityEndDate) {
		this.activityEndDate = activityEndDate;
	}

	public String getActivityDescription() {
		return activityDescription;
	}

	public void setActivityDescription(String activityDescription) {
		this.activityDescription = activityDescription;
	}


	public Integer getConsultantId() {
		return consultantId;
	}

	public void setConsultantId(Integer consultantId) {
		this.consultantId = consultantId;
	}

	public Integer getProjectId() {
		return projectId;
	}

	public void setProjectId(Integer projectId) {
		this.projectId = projectId;
	}

	
	 
}
