package com.taskmanagement.task.models;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "activity_entry")
public class ActivityEntry {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Consultant getConsultant() {
		return consultant;
	}

	public void setConsultant(Consultant consultant) {
		this.consultant = consultant;
	}

	public Project getProject() {
		return project;
	}

	public String getActivityDescription() {
		return ActivityDescription;
	}

	public void setActivityDescription(String activityDescription) {
		ActivityDescription = activityDescription;
	}

	public void setProject(Project project) {
		this.project = project;
	}
	
	public LocalDate getActivityEndDate() {
		return activityEndDate;
	}

	public void setActivityEndDate(LocalDate activityEndDate) {
		this.activityEndDate = activityEndDate;
	}

	private String ActivityDescription;
	
	 private LocalDate activityEndDate; // Etkinlik bitiş tarihi

	@ManyToOne
    @JoinColumn(name = "consultant_id")
    private Consultant consultant;
    
    @ManyToOne
    @JoinColumn(name = "project_id")
    private Project project;
    

}
