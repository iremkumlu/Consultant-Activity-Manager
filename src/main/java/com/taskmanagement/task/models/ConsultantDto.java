package com.taskmanagement.task.models;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import jakarta.validation.constraints.NotEmpty;

public class ConsultantDto {

	@NotEmpty(message = "The consultant name is required")
	private String firstName;

	@NotEmpty(message = "The consultant lastname is required")
	private String lastName;

	@NotEmpty(message = "The consultant email is required")
	private String email;

	@NotEmpty(message = "The consultant phone is required")
	private String phone;
	
	@NotEmpty(message = "The consultant address is required")
	private String address;
	
	private Long id; 
	
	 private Long detailsId;
	
	public Long getDetailsId() {
		return detailsId;
	}

	public void setDetailsId(Long detailsId) {
		this.detailsId = detailsId;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	private boolean active;


	private Set<ConsultantProjectDetails> details = new HashSet<>();
	
	
	public Long getId() {
		return id;
	}

	public Set<ConsultantProjectDetails> getDetails() {
		return details;
	}

	public void setDetails(Set<ConsultantProjectDetails> details) {
		this.details = details;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
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
	
	

}







