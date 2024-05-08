package com.taskmanagement.task.services;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.taskmanagement.task.models.ConsultantProjectDetails;

public interface ConsultantProjectDetailsRepository extends JpaRepository<ConsultantProjectDetails, Long>{
	
	 List<ConsultantProjectDetails> findByConsultantId(Long consultantId);
	 

}
