package com.taskmanagement.task.models;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "consultant_project_notes")
public class ConsultantProjectDetails {
	
	    @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;
	    
	    private Long consultantId;
	        
	    public Long getConsultantId() {
			return consultantId;
		}

		public void setConsultantId(Long consultantId) {
			this.consultantId = consultantId;
		}

		  @ManyToMany(mappedBy = "details", cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE })
		  private Set<Consultant> consultants;
	  	    
	   
		public Set<Consultant> getConsultants() {
			return consultants;
		}

		public void setConsultants(Set<Consultant> consultants) {
			this.consultants = consultants;
		}

		private String details;
      	    

		public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
		}


		public String getDetails() {
			return details;
		}

		public void setDetails(String details) {
			this.details = details;
		}

		@Override
	    public String toString() {
	        return details;
	    }
		

	    
	}


