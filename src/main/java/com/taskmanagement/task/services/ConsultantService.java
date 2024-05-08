package com.taskmanagement.task.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.taskmanagement.task.models.Consultant;

import jakarta.transaction.Transactional;

@Service
public class ConsultantService {

	 @Autowired
	    private ConsultantRepository consultantRepository;

	    @Autowired
	    private ActivityEntryRepository activityEntryRepository;

	    @Transactional
	    public void deleteInactiveConsultants() {
	        List<Consultant> activeConsultants = consultantRepository.findByActiveTrue();
	        for (Consultant consultant : activeConsultants) {
	            consultantRepository.delete(consultant);
	        }
	    }
}
