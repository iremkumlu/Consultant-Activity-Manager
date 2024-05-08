package com.taskmanagement.task.services;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.taskmanagement.task.models.Consultant;

public interface ConsultantRepository extends JpaRepository<Consultant, Integer>{
	
	List<Consultant> findByActiveTrue();
}

