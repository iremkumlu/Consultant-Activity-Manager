package com.taskmanagement.task.models;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;


import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.JoinColumn;

import jakarta.persistence.Table;


@Entity
@Table(name = "projects")
public class Project {
	
	    @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;

	    @Column(name = "project_name")
	    private String projectName;
	    
	    @Column(name = "delivery_date")
	    @DateTimeFormat(pattern = "yyyy-MM-dd")
	    private LocalDate deliveryDate;
	    
	    @OneToMany(mappedBy = "project")
	    private Set<ActivityEntry> activityEntrys;
 
		public Set<ActivityEntry> getActivityEntrys() {
			return activityEntrys;
		}

		public void setActivityEntrys(Set<ActivityEntry> activityEntrys) {
			this.activityEntrys = activityEntrys;
		}

		public LocalDate getDeliveryDate() {
	        return deliveryDate;
	    }

	    public void setDeliveryDate(LocalDate deliveryDate) {
	        this.deliveryDate = deliveryDate;
	    }


	    @Column(name = "project_description", columnDefinition = "TEXT")
	    private String projectDescription;

	    @Column(name = "work_plan", columnDefinition = "TEXT")
	    private String workPlan;
	     

	    public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
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

		public Set<Consultant> getConsultants() {
			return consultants;
		}

		public void setConsultants(Set<Consultant> consultants) {
			this.consultants = consultants;
		}


		@ManyToMany
	    @JoinTable(
	        name = "project_consultants",
	        joinColumns = @JoinColumn(name = "project_id"),
	        inverseJoinColumns = @JoinColumn(name = "consultant_id")
	    )
	    private Set<Consultant> consultants = new HashSet<>();
		
				
	    
	}


