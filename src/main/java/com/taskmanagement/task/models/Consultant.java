package com.taskmanagement.task.models;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.JoinColumn;


@Entity
@Table(name = "consultants")
public class Consultant {
	

	    @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;

	    private String firstName;
	    public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
		}

		private String lastName;
		
	    public boolean isActive() {
			return active;
		}

		public void setActive(boolean active) {
			this.active = active;
		}

		private String email;
	    private String phone;
	    private String address;
	    private boolean active; // Danışman Aktif/pasif durum kontrolü için
	    
	    public Set<ActivityEntry> getActivityEntrys() {
			return activityEntrys;
		}

    	public void setActivityEntrys(Set<ActivityEntry> activityEntrys) {
			this.activityEntrys = activityEntrys;
		}

		@OneToMany(mappedBy = "consultant")
	    private Set<ActivityEntry> activityEntrys;
		
		 @ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE })
		    @JoinTable(
		        name = "consultant_details",
		        joinColumns = @JoinColumn(name = "consultant_id"),
		        inverseJoinColumns = @JoinColumn(name = "details_id")
		    )
		    private Set<ConsultantProjectDetails> details = new HashSet<>();
				
		

	    public Set<ConsultantProjectDetails> getDetails() {
			return details;
		}

		public void setDetails(Set<ConsultantProjectDetails> details) {
			this.details = details;
		}

		public String getEmail() {
			return email;
		}

		public void setEmail(String email) {
			this.email = email;
		}

	

		public String getPhone() {
			return phone;
		}

		public void setPhone(String phone) {
			this.phone = phone;
		}


		public String getAddress() {
			return address;
		}

		public void setAddress(String address) {
			this.address = address;
		}

			
		public String getFirstName() {
			return firstName;
		}

		public void setFirstName(String firstName) {
			this.firstName = firstName;
		}

		public Set<Project> getProjects() {
			return projects;
		}

		public void setProjects(Set<Project> projects) {
			this.projects = projects;
		}

		public String getLastName() {
			return lastName;
		}

		public void setLastName(String lastName) {
			this.lastName = lastName;
		}
		
		 @ManyToMany(mappedBy = "consultants")
		  private Set<Project> projects = new HashSet<>();
		 
		 public void clearDetails() {
		        this.details.clear();
		    }

		    public void addDetail(ConsultantProjectDetails detail) {
		        this.details.add(detail);
		    }

		    public void removeDetail(ConsultantProjectDetails detail) {
		        this.details.remove(detail);
		    }

		 
		 @Override
		    public String toString() {
		        return "Consultant{" +
		                "id=" + id +
		                ", firstName='" + firstName + '\'' +
		                ", lastName='" + lastName + '\'' +
		                ", email='" + email + '\'' +
		                ", phone='" + phone + '\'' +
		                ", address='" + address + '\'' +
		                ", active=" + active +
		                '}';
		    }

		   
	   
	}


