package com.taskmanagement.task.services;

import org.springframework.data.jpa.repository.JpaRepository;


import com.taskmanagement.task.models.ActivityEntry;





public interface ActivityEntryRepository extends JpaRepository<ActivityEntry, Integer>{
	

}
