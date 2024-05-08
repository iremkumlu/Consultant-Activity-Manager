package com.taskmanagement.task.models;

import java.time.LocalDate;

import jakarta.validation.constraints.NotEmpty;

public class ProjectDto {
    
    private Long id;
    
    private Long[] consultantIds;
    
    @NotEmpty(message = "The project name is required")
    private String projectName;

    @NotEmpty(message = "The project description is required")
    private String projectDescription;

    @NotEmpty(message = "The work plan is required")
    private String workPlan;

    
    public Long[] getConsultantIds() {
		return consultantIds;
	}

	public void setConsultantIds(Long[] consultantIds) {
		this.consultantIds = consultantIds;
	}

	public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    private LocalDate deliveryDate; // LocalDate olarak değiştirildi

    public LocalDate getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(LocalDate deliveryDate) {
        this.deliveryDate = deliveryDate;
    }


    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getProjectDescription() {
        return projectDescription;
    }

    public void setProjectDescription(String projectDescription) {
        this.projectDescription = projectDescription;
    }

    public String getWorkPlan() {
        return workPlan;
    }

    public void setWorkPlan(String workPlan) {
        this.workPlan = workPlan;
    }
}
