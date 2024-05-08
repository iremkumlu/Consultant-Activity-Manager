package com.taskmanagement.task.controllers;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.taskmanagement.task.models.ActivityEntry;
import com.taskmanagement.task.models.ActivityEntryDto;
import com.taskmanagement.task.models.Consultant;
import com.taskmanagement.task.models.Project;
import com.taskmanagement.task.models.ProjectDto;
import com.taskmanagement.task.services.ConsultantRepository;
import com.taskmanagement.task.services.ProjectRepository;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/projects")
public class ProjectControllers {

    @Autowired
    private ProjectRepository repo;
    
    @Autowired
    private ConsultantRepository consultantRepository;

    @GetMapping({"", "/"})
    public String showProjectList(Model model) {
        List<Project> projects = repo.findAll(Sort.by(Sort.Direction.DESC, "id"));
        model.addAttribute("projects", projects);
        return "projects/index"; // Düzeltildi: consultants/project
    }

    @GetMapping("/create")
    public String showCreate(Model model) {
        ProjectDto projectDto = new ProjectDto();    
        model.addAttribute("projectDto", projectDto);
        
        // Veritabanından tüm danışmanları getir
        List<Consultant> consultants = consultantRepository.findAll();
        model.addAttribute("consultants", consultants);
        
        return "projects/CreateProject";
    }
    
    @PostMapping("/create")
    public String createProject(@Valid @ModelAttribute("projectDto") ProjectDto projectDto, BindingResult result, Model model) {
        if (result.hasErrors()) {
            // Hata varsa, tekrar danışmanları getir ve sayfayı göster
            List<Consultant> consultants = consultantRepository.findAll();
            model.addAttribute("consultants", consultants);
            return "projects/CreateProject";
        }

        // Danışman ID'lerini al ve ilgili danışmanları bul
        Set<Consultant> consultants = new HashSet<>();
        for (Long consultantId : projectDto.getConsultantIds()) {
        	Consultant consultant = consultantRepository.findById(consultantId.intValue()).orElse(null);
            if (consultant != null) {
                consultants.add(consultant);
            }
        }

        // Proje oluştur
        Project project = new Project();
        project.setProjectName(projectDto.getProjectName());
        project.setProjectDescription(projectDto.getProjectDescription());
        project.setWorkPlan(projectDto.getWorkPlan());
        project.setDeliveryDate(projectDto.getDeliveryDate());
        project.setConsultants(consultants);

        repo.save(project);

        return "redirect:/projects";
    }

    @GetMapping("/edit")
    public String showEditPage(Model model, @RequestParam int id) {
        Project project = repo.findById(id).orElse(null);
        if (project == null) {
            return "redirect:/projects";
        }

        ProjectDto projectDto = new ProjectDto();
        projectDto.setProjectName(project.getProjectName());
        projectDto.setProjectDescription(project.getProjectDescription());
        projectDto.setWorkPlan(project.getWorkPlan());
        projectDto.setDeliveryDate(project.getDeliveryDate());

        // Edit sayfasında mevcut atamaları göstermek için
        model.addAttribute("project", project);
        model.addAttribute("projectDto", projectDto);
        
        // Veritabanından tüm danışmanları getir
        List<Consultant> consultants = consultantRepository.findAll(); 
        model.addAttribute("consultants", consultants);

        return "projects/EditProject";
    }


    @PostMapping("/edit")
    public String updateProject(Model model, @RequestParam int id, @Valid @ModelAttribute ProjectDto projectDto, BindingResult result) {
        if (result.hasErrors()) {
            return "projects/EditProject";
        }

        Project project = repo.findById(id).orElse(null);
        if (project != null) {
            project.setProjectName(projectDto.getProjectName());
            project.setProjectDescription(projectDto.getProjectDescription());
            project.setWorkPlan(projectDto.getWorkPlan());
            project.setDeliveryDate(projectDto.getDeliveryDate());

            // Yeni danışman ID'lerini al ve ilgili danışmanları bul
            Set<Consultant> newConsultants = new HashSet<>();
            for (Long consultantId : projectDto.getConsultantIds()) {
                Consultant consultant = consultantRepository.findById(consultantId.intValue()).orElse(null);
                if (consultant != null) {
                    newConsultants.add(consultant);
                }
            }

            // Danışmanları güncelle
            project.setConsultants(newConsultants);

            repo.save(project); 
        }

        return "redirect:/projects";
    } 

    @GetMapping("/delete")
    public String deleteProject(@RequestParam int id) {
    	Project project = repo.findById(id).orElse(null);
        if (project != null) {
        	repo.delete(project);
        }

        return "redirect:/projects"; 
    }
}
