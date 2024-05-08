package com.taskmanagement.task.services;

import org.springframework.data.jpa.repository.JpaRepository;

import com.taskmanagement.task.models.Project;


public interface ProjectRepository extends JpaRepository<Project, Integer> {
   
}

